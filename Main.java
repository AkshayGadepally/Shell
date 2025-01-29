import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import main.java.FileLocation;
import main.java.Quotations;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
    
        // while loop to start the shell with $ sign

        while(true){        
        System.out.print("$ ");
        String input = scanner.nextLine();

        // checking for quotes

        List<String> split = Quotations.quote(input);
        String place1 = split.get(0);
        
        // Checking the "type" command

        String[] command = {"echo","exit","type","pwd","cd"};
        if(input.startsWith("type")){
            boolean check = false;
            if(split.size() != 2){
                System.out.println("Syntax: type <command>");
                continue;
            }
           String place2 = split.get(1);
           for(String word : command){ 
               if(place2.equals(word)){
                    System.out.println(split.get(1) + " is a shell builtin");
                    check = true;
                    break;
                    }    
                }
                if(!check){
                    String location = FileLocation.fileLocation(place2);
                    if(location != null){
                        System.out.println(place2 + " is " + location);
                    } else{
                        System.out.println(split.get(1) + ": not found");
                    }
                }continue;
        }  

        // Statement for opening a file with given arguments from the PATH
    
          String place = FileLocation.fileLocation(place1);   
          if(place != null){
            
            try{
                ProcessBuilder processbuilder = new ProcessBuilder(split);
                processbuilder.directory(new File(System.getProperty("user.dir")));
                Process process = processbuilder.start();
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                    String line;
                    while((line = reader.readLine()) != null){
                        System.out.println(line);
                    }
                }
                process.waitFor();
                continue;
            }
            catch(IOException e){
                e.printStackTrace();
            }
          }

        // Change Directory statement

        if(place1.equals("cd")) {
            if(split.size() < 2) {
                continue;
            }
            String targetPath = split.get(1);
            try {
                if(targetPath.equals("~")){
                    String home = System.getenv("HOME");
                    if(home != null){
                        System.setProperty("user.dir",home);
                    }
                    else{
                        System.out.println("cd: " + targetPath + ": No such file or directory");
                    }
                    continue;
                }
                File directory = new File(targetPath);
                if(directory.isAbsolute()) {
                    // Handle absolute path
                    if(directory.exists() && directory.isDirectory()) {
                        String canonicalPath = directory.getCanonicalPath();
                        System.setProperty("user.dir", canonicalPath);
                    } else {
                        System.out.println("cd: " + targetPath + ": No such file or directory");
                    }
                } else {
                    // Handle relative path 
                    File currentDir = new File(System.getProperty("user.dir"));
                    File newDir = new File(currentDir, targetPath).getCanonicalFile();
                    if(newDir.exists() && newDir.isDirectory()) {
                        System.setProperty("user.dir", newDir.getCanonicalPath());
                    } 
                        else{
                        System.out.println("cd: " + targetPath + ": No such file or directory");
                    }
                }
                
            } catch(IOException e) {
                System.out.println("cd: " + targetPath + ": No such file or directory");
            }
            continue;
        }

        // statement about the pwd command

        if(input.equals ("pwd")){
            System.out.println(System.getProperty("user.dir"));
            continue;
        }

        // this statement is to exit when the user types "exit 0"

        if (input.equals("exit 0")) {
            break;
        }

        // this statement is for echo command

        if(input.startsWith("echo")){ 
            for(int i = 1; i < split.size() ; i++){
                if(split.get(i)=="\\") {
                    continue;
                }
                else
                if(i == split.size() - 1){
                    System.out.print(split.get(i));
                    System.out.println(); 
                }
                else{
                    System.out.print(split.get(i) + " ");
                }
            }
            continue;
         }

        // this statement is to print for invalid commands
        
                System.out.println(input + ": command not found");
        if(input.equals(null)){
            System.out.println("$ ");
            break;
         }
        
      }    
        
         scanner.close();
        
    } 
}
