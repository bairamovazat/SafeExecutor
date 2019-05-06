package ru.ivmiit.executor.models.executors;

import ru.ivmiit.executor.enums.ExecutorLanguage;
import ru.ivmiit.executor.enums.ExecutorPlatform;

import java.io.*;


public class JavaWindowsExecutor extends CommonExecutor {

    @Override
    public ProcessBuilder getCompileProcessBuilder(String file, String workingDirectory) throws IOException {
        return new ProcessBuilder(
                "cmd.exe", "/c", "javac -encoding UTF-8 " + "-d " + workingDirectory + " " + file);
    }

    @Override
    public ProcessBuilder getRunProcessBuilder(String compiledFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException {
        return new ProcessBuilder(
                "cmd.exe", "/c", "java -Dfile.encoding=UTF-8 -cp . " + compiledFile);
    }

    @Override
    public ExecutorLanguage getLanguage() {
        return ExecutorLanguage.Java;
    }

    @Override
    public ExecutorPlatform getPlatform() {
        return ExecutorPlatform.Windows;
    }
}
