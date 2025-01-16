import java.util.Scanner;
import java.util.Arrays;
import main.java.FileLocation;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
      

        Scanner scanner = new Scanner(System.in);
        

        // while loop to start the shell with $ sign

        while(true){        
        System.out.print("$ ");
        String input = scanner.nextLine();
        String[] split = input.split(" ");
        String place1 = split[0];
        String initialDir = System.getProperty("user.dir");

        // Checking the "type" command

        String[] command = {"echo","exit","type","pwd","cd"};
        if(input.startsWith("type")){
            boolean check = false;
            if(split.length != 2){
                System.out.println("Syntax: type <command>");
                continue;
            }
           String place2 = split[1];
           for(String word : command){ 
               if(place2.equals(word)){
                    System.out.println(split[1] + " is a shell builtin");
                    check = true;
                    break;
                    }    
                }
                if(!check){
                    String location = FileLocation.fileLocation(place2);
                    if(location != null){
                        System.out.println(place2 + " is " + location);
                    } else{
                        System.out.println(split[1] + ": not found");
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
            if(split.length < 2) {
                continue;
            }
            String targetPath = split[1];
            try {
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
                    // Handle relative path (keeping existing logic for now)
                    File currentDir = new File(System.getProperty("user.dir"));
                    File newDir = new File(currentDir, targetPath).getCanonicalFile();
                    if(newDir.exists() && newDir.isDirectory()) {
                        System.setProperty("user.dir", newDir.getCanonicalPath());
                    } else {
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
            for(int i = 1; i < split.length ; i++){
                if(i == split.length - 1){
                    System.out.print(split[i]);
                    System.out.println(); 
                }
                else{
                    System.out.print(split[i] + " ");
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
