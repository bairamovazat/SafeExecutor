package ru.ivmiit.executor;

import java.io.IOException;
import java.util.List;

public interface Executor {

    void compileJavaFile(String javaFile, String workingDirectory) throws IOException;

    List<String> runFile(String classFile, String workingDirectory, String inputData, long timeLimitMillis, long realTimeLimitSec, long maxVmSizeMb) throws IOException;

}