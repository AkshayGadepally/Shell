import java.util.Scanner;
import java.util.Arrays;
import main.java.FileLocation;

public class Main {
    public static void main(String[] args) throws Exception {
      

        Scanner scanner = new Scanner(System.in);
        

        // while loop to start the shell with $ sign
        while(true){        
        System.out.print("$ ");
        String input = scanner.nextLine();

        // Checking the "type" command
        String[] command = {"echo","exit","type"};
        
        if(input.startsWith("type")){
            String[] split = input.split(" ");
            boolean check = false;
            String givencommand = split[1];

            if(split.length != 2){
                System.out.println("Syntax: type <command>");
                continue;
            }
            
           for(String word : command){ 
               if(givencommand.equals(word)){
                System.out.println(split[1] + " is a shell builtin");
                check = true;
                break;
               }
               }
                if(!check){
                    String location = FileLocation.fileLocation(givencommand);
                    if(location != null){
                        System.out.println(givencommand + " is " + location);
                    } else{
                    System.out.println(split[1] + ": not found");
                    }
                }continue;
           }
        
        
        // this statement is to exit when the user types "exit 0"
        if (input.equals("exit 0")) {
            break;
        }

        // this statement is for echo command 
        if(input.startsWith("echo")){
            String[] Array = input.split(" ");
            for(int i = 1; i < Array.length ; i++){
                if(i == Array.length - 1){
                    System.out.print(Array[i]);
                    System.out.println();
                    
                }
                else{
                 System.out.print(Array[i] + " ");
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
