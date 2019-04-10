package ru.ivmiit.executor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ExecutorCommon implements Executor {

    public static String cmdCharset = "UTF-8";
    public static String fileCharset = "UTF-8";
    public static int waitTimeBeforeClose = 1000;

    public abstract ProcessBuilder getCompileProcessBuilder(String javaFile, String workingDirectory) throws IOException;

    public ExecutorResult compileJavaFile(String javaFile, String workingDirectory) throws IOException {
        ProcessBuilder builder = getCompileProcessBuilder(javaFile, workingDirectory);

        builder.directory(new File(workingDirectory));

        builder.redirectErrorStream(true);
        Process p = builder.start();

        InputStreamReader cmdInputReader = new InputStreamReader(p.getInputStream(), cmdCharset);
        BufferedReader r = new BufferedReader(cmdInputReader);

        List<String> result = new ArrayList<>();
        String line;
        int i = 0;

        while ((line = r.readLine()) != null) {
            i++;
            result.add(line);
        }

        ExecutorResult status = ExecutorResult.from(result);
        if(!status.isOk()){
            throw new IllegalArgumentException("Compile Runtime Error! " + status.toString());
        }
        return status;
    }

    public abstract ProcessBuilder getRunProcessBuilder(String classFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException;

    public ExecutorResult runFile(String classFile, String workingDirectory, String inputData, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException {
        List<String> outputData = new ArrayList<>();

        ProcessBuilder builder = getRunProcessBuilder(classFile, workingDirectory, timeLimitMillis, realTimeLimitSec, maxStackSizeMb);

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
        ExecutorResult result = ExecutorResult.from(outputData);

        return result;
    }

}