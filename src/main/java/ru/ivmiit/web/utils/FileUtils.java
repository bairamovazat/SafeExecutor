package ru.ivmiit.web.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {

    public static void writeToFile(String filePath, String text, boolean create) throws IOException {

        File commandFile = new File(filePath);
        if(create){
            commandFile.setWritable(true, true);
            commandFile.setReadable(true, true);
            commandFile.setExecutable(true, true);
            boolean resultJavaFile = commandFile.createNewFile();
        }

        PrintWriter out = new PrintWriter(commandFile.getAbsolutePath(), "UTF-8");
        out.print(text);
        out.close();
    }
}
