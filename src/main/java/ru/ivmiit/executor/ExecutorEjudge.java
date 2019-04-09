package ru.ivmiit.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import ru.ivmiit.web.utils.FileUtils;
import ru.ivmiit.web.utils.TaskUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ExecutorEjudge extends ExecutorCommon {

    public static String cmdCharset = "UTF-8";
    public static String fileCharset = "UTF-8";
    public static int waitTimeBeforeClose = 1000;
    private String programName = "Program";
    private String pathToBin;

    public static void main(String[] args) throws IOException {
        String dir = "/home/ejudge/test-ejudge";
        String program = "public class Program{\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.print(\"work\");   \n" +
                "    }\n" +
                "}";
        FileUtils.writeToFile(dir + "/Program.java", program, true);

        ExecutorEjudge executorEjudge = new ExecutorEjudge("/home/ejudge/inst-ejudge/bin/ejudge-execute");
        executorEjudge.compileJavaFile(dir + "/Program.java",
                "/home/ejudge/test-ejudge");

        List<String> result = executorEjudge.runFile( "Program",
                "/home/ejudge/test-ejudge", "1 2",999999999, 99999, 2147483647 );
        System.out.println(String.join("\n", result));
    }

    public ExecutorEjudge(String pathToEjudgeBin){
        this.pathToBin = pathToEjudgeBin;
    }

    @Override
    public ProcessBuilder getCompileProcessBuilder(String javaFile, String workingDirectory) throws IOException {
        createCompileFile(javaFile, workingDirectory);

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
    public ProcessBuilder getRunProcessBuilder(String classFile, String workingDirectory, long timeLimitMillis, long realTimeLimitSec, long maxVmSizeMb) throws IOException {
        if(timeLimitMillis >= 1000000000){
            throw new IllegalArgumentException("timeLimitMillis may be less than 10,000");
        }
        if(realTimeLimitSec >= 100000){
            throw new IllegalArgumentException("realTimeLimitSec may be less than 10,000");
        }
        createRunFile(classFile, workingDirectory);

        String ejudgeCommand = pathToBin +
        " --time-limit-millis=" + timeLimitMillis +
                " --real-time-limit=" + realTimeLimitSec +
                " --max-vm-size=" + maxVmSizeMb + "M" +
                " --max-stack-size=" + maxVmSizeMb + "M " +
                "  --memory-limit --security-violation" +
                " --mode=664 --group=ejudge ./run";

        return new ProcessBuilder("/bin/bash",
                "-c", ejudgeCommand);
    }

    private void createRunFile(String file, String workingDirectory) throws IOException {
        String command = "#! /bin/sh \n" +
                "exec java -Dfile.encoding=UTF-8 -cp . " + file;

        String filePath = workingDirectory + "/run";
        FileUtils.writeToFile(filePath, command, true);
    }


}
