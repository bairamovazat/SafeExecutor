package ru.ivmiit.executor.models.executors;

import ru.ivmiit.executor.models.ExecutorResult;
import ru.ivmiit.executor.enums.ExecutorLanguage;
import ru.ivmiit.executor.enums.ExecutorPlatform;
import ru.ivmiit.executor.utils.FileUtils;

import java.io.*;


public class JavaExecutor extends CommonExecutor {

    private String pathToBin;

    private static long maxVmSize = 8 * 1024;

    public static void main(String[] args) throws IOException {
        String dir = "/home/ejudge/test-ejudge";
        String program = "public class Program{\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.print(\"work\");   \n" +
                "    }\n" +
                "}";
        FileUtils.writeToFile(dir + "/Program.java", program, true);

        JavaExecutor javaExecutor = new JavaExecutor("/home/ejudge/inst-ejudge/bin/ejudge-execute");
        javaExecutor.compileFile(dir + "/Program.java",
                "/home/ejudge/test-ejudge");

        ExecutorResult result = javaExecutor.runFile( "Program",
                "/home/ejudge/test-ejudge", "1 2",999999999, 99999, 64 );
        System.out.println(result);
    }

    public JavaExecutor(String pathToEjudgeBin){
        this.pathToBin = pathToEjudgeBin;
    }

    @Override
    public ProcessBuilder getCompileProcessBuilder(String file, String workingDirectory) throws IOException {
        createCompileFile(file, workingDirectory);

        String ejudgeCommand = pathToBin + " --time-limit=" + 1000 +
                " --mode=664 --group=ejudge ./compiller";
        ProcessBuilder builder = new ProcessBuilder("/bin/bash",
                "-c", ejudgeCommand);
        return builder;
    }


    private void createCompileFile(String file, String workingDirectory) throws IOException {
        String command = "#! /bin/sh \n" +
                "exec javac -encoding UTF-8 -d " + workingDirectory + " " + file;

        String filePath = workingDirectory + "/compiller";
        FileUtils.writeToFile(filePath, command, true);
    }


    @Override
    public ProcessBuilder getRunProcessBuilder(String compiledFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxStackSizeMb) throws IOException {
        if(timeLimitMillis >= 1000000000){
            throw new IllegalArgumentException("timeLimitMillis may be less than 10,000");
        }
        if(realTimeLimitSec >= 100000){
            throw new IllegalArgumentException("realTimeLimitSec may be less than 10,000");
        }
        createRunFile(compiledFile, workingDirectory);

        String ejudgeCommand = pathToBin +
        " --time-limit-millis=" + timeLimitMillis +
                " --real-time-limit=" + realTimeLimitSec +
                " --max-vm-size=" + maxVmSize + "M" +
                " --max-stack-size=" + maxStackSizeMb + "M " +
                " --use-stdin --use-stdout" +
                " --memory-limit --security-violation" +
                " --mode=664 --group=ejudge ./run";

        return new ProcessBuilder("/bin/bash",
                "-c", ejudgeCommand);
    }

    @Override
    public ExecutorLanguage getLanguage() {
        return ExecutorLanguage.Java;
    }

    @Override
    public ExecutorPlatform getPlatform() {
        return ExecutorPlatform.Linux;
    }

    private void createRunFile(String file, String workingDirectory) throws IOException {
        String command = "#! /bin/sh \n" +
                "exec java -Xmx512m -Dfile.encoding=UTF-8 -cp . " + file;

        String filePath = workingDirectory + "/run";
        FileUtils.writeToFile(filePath, command, true);
    }

}