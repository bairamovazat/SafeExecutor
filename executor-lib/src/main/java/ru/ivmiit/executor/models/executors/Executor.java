package ru.ivmiit.executor.models.executors;

import ru.ivmiit.executor.models.ExecutorResult;

import java.io.IOException;

public interface Executor {

    ExecutorResult compileFile(String javaFile, String workingDirectory) throws IOException;

    ExecutorResult runFile(String classFile, String workingDirectory, String inputData, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException;

}