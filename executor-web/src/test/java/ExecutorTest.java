
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ivmiit.executor.models.executors.Executor;
import ru.ivmiit.executor.models.executors.JavaExecutor;
import ru.ivmiit.executor.models.ExecutorResult;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class ExecutorTest {

    public static String workingDir = System.getProperty("user.dir") + "/src/test/java";
    public static String simpleTestName = "SimpleTest";
    public static String sumTestName = "SumTest";
    public static String outOfTimeTestName = "OutOfTimeTest";
    public static String outOfMemoryTestName = "OutOfMemoryTest";
    public static String securityTestName = "SecurityTest";

    public static Executor executor;

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

    @BeforeAll
    public static void createExecutor(){
        executor = new JavaExecutor("/home/ejudge/inst-ejudge/bin/ejudge-execute");
    }

    @AfterAll
    public static void clearCompileFiles() {
        deleteFileIfExists(workingDir + "/" + simpleTestName + ".class");
        deleteFileIfExists(workingDir + "/" + sumTestName + ".class");
        deleteFileIfExists(workingDir + "/" + outOfTimeTestName + ".class");
        deleteFileIfExists(workingDir + "/" + securityTestName + ".class");
        deleteFileIfExists(workingDir + "/" + "run");
        deleteFileIfExists(workingDir + "/" + "compiller");
    }

    @Test
    void testCompile() throws IOException {
        String absoluteCompiledFileName = workingDir + "/" + simpleTestName + ".java";
        compile(workingDir, simpleTestName);
        if (fileExists(absoluteCompiledFileName) == null) {
            throw new IllegalArgumentException("Файл не скомпилировался");
        }
    }

    void compile(String workingDir, String testFileName) throws IOException {
        String absoluteJavaFileName = workingDir + "/" + testFileName + ".java";
        String absoluteCompiledFileName = workingDir + "/" + testFileName + ".class";

        deleteFileIfExists(absoluteCompiledFileName);
        executor.compileFile(absoluteJavaFileName, workingDir);
    }

    @Test
    void testSimpleCorrectAnswer() throws IOException {
        String testAnswer = "Work!";
        compile(workingDir, simpleTestName);
        ExecutorResult result = executor.runFile(simpleTestName, workingDir, "", 1000, 10, 64000);
        if (!result.isOk() || !result.getProgramResult().equals(testAnswer)) {
            fail("Результат не верен! Ожидалось \"" + testAnswer + "\". Получено \"" + result + "\"");
        }
    }

    @Test
    void testSumCorrectAnswer() throws IOException {
        String testAnswer = "3";
        compile(workingDir, sumTestName);
        ExecutorResult result  = executor.runFile(sumTestName, workingDir, "1 2", 1000, 10, 64);
        if (!result.isOk() || !result.getProgramResult().equals(testAnswer)) {
            fail("Результат не верен! Ожидалось \"" + testAnswer + "\". Получено \"" + result + "\"");
        }
    }

    @Test
    void testTimeOut() throws IOException {
        compile(workingDir, outOfTimeTestName);
        ExecutorResult result  = executor.runFile(outOfTimeTestName, workingDir, "1 2", 1000, 10, 64);
        if (result.isOk()) {
            fail("Результат не верен! Ожидалось timeout error. Получено \"" + result + "\"");
        }
    }

    @Test
    void securityTest() throws IOException {
        compile(workingDir, securityTestName);
        ExecutorResult result  = executor.runFile(securityTestName, workingDir, "1 2", 1000, 10, 64);
        if (result.isOk()) {
            fail("Результат не верен! Ожидалось security error. Получено \"" + result + "\"");
        }
        System.out.println(result);
    }

    void testMemoryOut() throws IOException {
        compile(workingDir, outOfMemoryTestName);
        ExecutorResult result  = executor.runFile(outOfMemoryTestName, workingDir, "1 2", 100000, 100, 64);
        if (result.isOk()) {
            fail("Результат не верен! Ожидалось memory limit error. Получено \"" + result + "\"");
        }
        System.out.println(result);
    }

}
