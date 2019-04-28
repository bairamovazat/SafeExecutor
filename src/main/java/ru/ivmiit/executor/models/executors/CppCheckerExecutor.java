package ru.ivmiit.executor.models.executors;

import ru.ivmiit.executor.enums.ExecutorLanguage;
import ru.ivmiit.executor.enums.ExecutorPlatform;
import ru.ivmiit.executor.models.ExecutorResult;
import ru.ivmiit.web.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class CppCheckerExecutor extends CommonExecutor {


    public static void main(String[] args) throws IOException {
        String dir = "/home/ejudge/unzip/0_1556461105352";

        CppCheckerExecutor cppCheckerExecutor = new CppCheckerExecutor();

        ExecutorResult compileResult = cppCheckerExecutor.compileFile("./check.cpp",
                dir + "/files");

        System.out.println(compileResult);

        ExecutorResult result = cppCheckerExecutor.runFile( "./check ../tests/01 ../tests/01.a ../tests/01.a",
                dir + "/files", "",0, 0, 0);

        System.out.println(result);
    }


    @Override
    public ProcessBuilder getCompileProcessBuilder(String file, String workingDirectory) throws IOException {
        String command = "g++ " + file + " -o check";
        ProcessBuilder builder = new ProcessBuilder("/bin/bash",
                "-c", command);
        
        builder.directory(new File(workingDirectory));
        
        return builder;
    }

    @Override
    public ProcessBuilder getRunProcessBuilder(String compiledFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException {
        String command = compiledFile;
        ProcessBuilder builder = new ProcessBuilder("/bin/bash",
                "-c", command);

        builder.directory(new File(workingDirectory));
        return builder;
    }

    @Override
    public ExecutorLanguage getLanguage() {
        return ExecutorLanguage.Cpp;
    }

    @Override
    public ExecutorPlatform getPlatform() {
        return ExecutorPlatform.Linux;
    }

}
