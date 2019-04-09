package ru.ivmiit.executor;

import java.io.IOException;
import java.util.List;

public interface Executor {

    ExecutorResult compileJavaFile(String javaFile, String workingDirectory) throws IOException;

    ExecutorResult runFile(String classFile, String workingDirectory, String inputData, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException;

}