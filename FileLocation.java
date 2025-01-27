package main.java;

import java.io.File;

public class FileLocation {
    public static String fileLocation(String givenCommand) {
        String path = System.getenv("PATH");
        String[] directories = path.split(":");
        for (String directory : directories) {
            File file = new File(directory + File.separator + givenCommand);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }
}

