package ru.ivmiit.executor.models.executors;

import ru.ivmiit.executor.models.ExecutorResult;
import ru.ivmiit.executor.enums.ExecutorLanguage;
import ru.ivmiit.executor.enums.ExecutorPlatform;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonExecutor implements Executor {

    public static String cmdCharset = "UTF-8";
    public static String fileCharset = "UTF-8";

    public abstract ProcessBuilder getCompileProcessBuilder(String file, String workingDirectory) throws IOException;

    public ExecutorResult compileFile(String file, String workingDirectory) throws IOException {
        ProcessBuilder builder = getCompileProcessBuilder(file, workingDirectory);

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
        return status;
    }

    public abstract ProcessBuilder getRunProcessBuilder(String compiledFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException;

    public ExecutorResult runFile(String compileFile, String workingDirectory, String inputData, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException {
        List<String> outputData = new ArrayList<>();

        ProcessBuilder builder = getRunProcessBuilder(compileFile, workingDirectory, timeLimitMillis, realTimeLimitSec, maxStackSizeMb);

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

    public abstract ExecutorLanguage getLanguage();

    public abstract ExecutorPlatform getPlatform();

}
