package ru.ivmiit.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class ExecutorCmd implements Executor {

    public static String cmdCharset = "UTF-8";
    public static String fileCharset = "UTF-8";
    public static long waitTimeBeforeClose = 1000;
    //--file=C:\Users\Azat\Documents\IdeaProjects\EjudgeExecutorLib\src\main\java\ru\ivmiit\Test.java --work-dir=C:\Users\Azat\Documents\IdeaProjects\EjudgeExecutorLib\target

    public static void main(String[] args) {
        String file = "C:\\Users\\Azat\\Documents\\IdeaProjects\\EjudgeExecutorLib\\solutions\\12\\Program.class";
        String directory = "C:\\Users\\Azat\\Documents\\IdeaProjects\\EjudgeExecutorLib\\solutions\\12\\";

        ExecutorCmd executor = new ExecutorCmd();
        try {
            List<String> result = executor.runFile(file, directory, "test");
            System.out.println(String.join("\n", result));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Map<String, String> argsMap = CommandLineUtils.getAllEjudgeCommand(args);
//
//        if (!argsMap.containsKey("file")) {
//            throw new IllegalArgumentException("Укажите файл для компиляции");
//        }
//
//        if (!argsMap.containsKey("work-dir")) {
//            throw new IllegalArgumentException("Укажите рабочую директорию");
//        }
//
//        try {
//            ExecutorCmd.compileJavaFile(argsMap.get("file"), argsMap.get("work-dir"));
//        } catch (IOException e) {
//            throw new IllegalArgumentException("Ошибка компиляции");
//        }
    }

    public void compileJavaFile(String file, String workingDirectory, int timeLimit, int maxVmSizeKB, int maxStackSizeKB) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "javac -encoding UTF-8 " + "-d " + workingDirectory + " " + file);
        builder.directory(new File(workingDirectory));
        builder.redirectErrorStream(true);
        Process p = builder.start();

        InputStreamReader cmdInputReader = new InputStreamReader(p.getInputStream(), cmdCharset);
        BufferedReader r = new BufferedReader(cmdInputReader);

        String line;

        int i = 0;

        while ((line = r.readLine()) != null) {
            i++;
            if (line.contains("error:")) {
                throw new IllegalArgumentException("Ошибка компиляции!");
            }
        }
        if (i > 0) {
            throw new IllegalArgumentException("Ошибка компиляции 107!");
        }
    }

    public List<String> runFile(String javaFile, String workingDirectory, String inputData) throws IOException {
        return runFile(javaFile, workingDirectory, inputData, waitTimeBeforeClose);
    }

    public List<String> runFile(String javaFile, String workingDirectory, String inputData, long waitTime) throws IOException {
        List<String> outputData = new ArrayList<>();

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "java -Dfile.encoding=UTF-8 -cp . " + javaFile);
        builder.directory(new File(workingDirectory));
        builder.redirectErrorStream(true);
        Process p = builder.start();

        InputStreamReader cmdInputReader = new InputStreamReader(p.getInputStream(), cmdCharset);
        OutputStreamWriter cmdOutputWriter = new OutputStreamWriter(p.getOutputStream(), fileCharset);

        cmdOutputWriter.write(inputData);
        cmdOutputWriter.flush();
        cmdOutputWriter.close();

        BufferedReader r = new BufferedReader(cmdInputReader);

        String line;
        while ((line = r.readLine()) != null) {
            outputData.add(line);

        }
        cmdInputReader.close();
        return outputData;
    }


}
