package ru.ivmiit.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import ru.ivmiit.web.utils.FileUtils;
import ru.ivmiit.web.utils.TaskUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class ExecutorEjudge implements Executor {

    public static String cmdCharset = "UTF-8";
    public static String fileCharset = "UTF-8";
    public static long waitTimeBeforeClose = 1000;
    private String programName = "Program";
    private String pathToBin;

    public ExecutorEjudge(String pathToEjudgeBin){
        this.pathToBin = pathToEjudgeBin;
    }

    public void createCompileFile(String file, String workingDirectory) throws IOException {
        String command = "#! /bin/sh \n" +
                "exec javac -encoding UTF-8 -d " + workingDirectory + " " + file;

        String filePath = workingDirectory + "\\compiller";
        FileUtils.writeToFile(filePath, command, true);
    }

    public void compileJavaFile(String file, String workingDirectory, int timeLimit, int maxVmSizeKB, int maxStackSizeKB) throws IOException {

        createCompileFile(file, workingDirectory);

        ProcessBuilder builder = new ProcessBuilder(
                pathToBin, "--time-limit=" + timeLimit + "--max-vm-size=" + maxVmSizeKB + "M " +
                "--max-stack-size=" + maxStackSizeKB + " --mode=664 --group=ejudge compiller");
        builder.directory(new File(workingDirectory));
        builder.redirectErrorStream(true);
        Process p = builder.start();

        InputStreamReader cmdInputReader = new InputStreamReader(p.getInputStream(), cmdCharset);
        BufferedReader r = new BufferedReader(cmdInputReader);

        StringBuilder resultBuilder = new StringBuilder();
        String line;
        int i = 0;

        while ((line = r.readLine()) != null) {
            i++;
            resultBuilder.append(line + "\n");
        }

        String status = TaskUtils.getTaskStatus(resultBuilder.toString());
        if(status == null || !status.toLowerCase().equals("OK")){
            throw new IllegalArgumentException("Compile Runtime Error!");
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
