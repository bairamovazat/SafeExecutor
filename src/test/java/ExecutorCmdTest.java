
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.ivmiit.executor.ExecutorCmd;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class ExecutorCmdTest {

    public static String workingDir = System.getProperty("user.dir") + "\\src\\test\\java";
    public static String simpleTestName = "SimpleTest";
    public static String sumTestName = "SumTest";
    public static String infinityTestName = "InfinityTest";

    private static void deleteFileIfExists(String name) {
        File file = new File(name);
        if (file.exists()) {
            file.delete();
        }
    }

    public static File fileExists(String name) {
        File file = new File(name);
        return file.exists() ? file : null;
    }

    @AfterAll
    public static void clearCompileFiles() {
        deleteFileIfExists(workingDir + "\\" + simpleTestName + ".class");
        deleteFileIfExists(workingDir + "\\" + sumTestName + ".class");
        deleteFileIfExists(workingDir + "\\" + infinityTestName + ".class");
    }

    @Test
    void testCompile() throws IOException {
        String absoluteCompiledFileName = workingDir + "\\" + simpleTestName + ".java";
        compile(workingDir, simpleTestName);
        if (fileExists(absoluteCompiledFileName) == null) {
            throw new IllegalArgumentException("Файл не скомпилировался");
        }
    }

    void compile(String workingDir, String testFileName) throws IOException {
        String absoluteJavaFileName = workingDir + "\\" + testFileName + ".java";
        String absoluteCompiledFileName = workingDir + "\\" + testFileName + ".class";

        deleteFileIfExists(absoluteCompiledFileName);
        ExecutorCmd executorCmd = new ExecutorCmd();
        executorCmd.compileJavaFile(absoluteJavaFileName, workingDir);

    }

    @Test
    void testSimpleCorrectAnswer() throws IOException {
        String testAnswer = "Work!";
        compile(workingDir, simpleTestName);
        ExecutorCmd executorCmd = new ExecutorCmd();
        List<String> result = executorCmd.runFile(simpleTestName, workingDir, "", 10, 100, 128);
        if (result.size() != 1 || !result.get(0).equals(testAnswer)) {
            fail("Результат не верен! Ожидалось \"" + testAnswer + "\". Получено \"" + result.get(0) + "\"");
        }
    }

    @Test
    void testSumCorrectAnswer() throws IOException {
        String testAnswer = "3";
        compile(workingDir, sumTestName);
        ExecutorCmd executorCmd = new ExecutorCmd();
        List<String> result = executorCmd.runFile(sumTestName, workingDir, "1 2", 10, 100, 128);
        if (result.size() != 1 || !result.get(0).equals(testAnswer)) {
            fail("Результат не верен! Ожидалось \"" + testAnswer + "\". Получено \"" + result.get(0) + "\"");
        }
    }

    @Test
    @Ignore
    void testTimeOut() throws IOException {
        fail();
//        String testAnswer = "3";
//        compile(workingDir, infinityTestName);
//        List<String> result = ExecutorCmd.runFile(infinityTestName, workingDir, "1 2", 500);
//        if (result.size() != 1 || !result.get(0).equals(testAnswer)) {
//            fail("Результат не верен! Ожидалось \"" + testAnswer + "\". Получено \"" + result.get(0) + "\"");
//        }
    }

}
