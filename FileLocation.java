package main.java;

import java.io.File;

public class FileLocation{
    public static String fileLocation(String givencommand){
        String path = System.getenv("PATH");
        String[] Directories = path.split(";");
        for(String directory : Directories){
            File file = new File(directory +File.separator+ givencommand);
            if(file.exists()&& file.canExecute()){
                return file.getAbsolutePath();
            }
        }
        return null;
    }
}

