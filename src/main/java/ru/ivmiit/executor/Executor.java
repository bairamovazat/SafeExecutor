package ru.ivmiit.executor;

import java.io.IOException;
import java.util.List;

public interface Executor {
    List<String> runFile(java.lang.String javaFile, java.lang.String workingDirectory, java.lang.String inputData, long waitTime) throws IOException;

    void compileJavaFile(String file, String workingDirectory, int timeLimit, int maxVmSizeKB, int maxStackSizeKB) throws IOException;
}