package ru.ivmiit.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ExecutorCmd extends ExecutorCommon {

    @Override
    public ProcessBuilder getCompileProcessBuilder(String javaFile, String workingDirectory) throws IOException {
        return new ProcessBuilder(
                "cmd.exe", "/c", "javac -encoding UTF-8 " + "-d " + workingDirectory + " " + javaFile);
    }

    @Override
    public ProcessBuilder getRunProcessBuilder(String classFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxVmSizeMb) throws IOException {
        return new ProcessBuilder(
                "cmd.exe", "/c", "java -Dfile.encoding=UTF-8 -cp . " + classFile);
    }
}
