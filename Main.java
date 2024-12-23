import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
      

        Scanner scanner = new Scanner(System.in);

        // while loop to start the shell with $ sign
        while(true){        
        System.out.print("$ ");
        String input = scanner.nextLine();

        String[] command = {"type", "echo", "exit 0"};
        
        if(input.startsWith("type")){
            String[] split = input.split(" ");
            
           for(String word : command){ 
               if(input.contains(word) && split.length == 2){
                System.out.print(split[1] + " is a shell builtin");
                break;
               }
               else{
                System.out.print(split[1]+": not found");
                break;
               }
           }break;
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
