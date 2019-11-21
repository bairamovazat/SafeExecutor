package ru.ivmiit.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class SafeExecutor {
    static {
        //Если падает тут, то проверьте, есть ли libEjudgeExecutorDll.so в classpath.
        //Если запускается как отдельная jar, то надо переместить файл куда-либо на диск. Т.к прочитать файл из jar-ки нельзя
        //Если запускается как war, то нужно добавить импорт ресурсов
        //  maven-resources-plugin:
        //      outputDirectory - ${project.build.directory}/classes
        //      directory - ${project.parent.basedir}/executor-lib/target/cmake-out
        //      include - **/*.so
        //Эта шляпа не захотела заводиться через время видимо JNI тупит
        URL url = SafeExecutor.class.getClassLoader().getResource("libEjudgeExecutorDll.so");
        if(url == null) {
            throw new IllegalArgumentException("libEjudgeExecutorDll.so не найден");
        }
        File file = new File(url.getPath());
        System.load(file.getAbsolutePath());
    }

    private native int main(int argc, String[] argv);

    public static void main(String[] args) {
        try {
            SafeExecutor safeExecutor = new SafeExecutor();
            safeExecutor.main(2, new String[]{"java", "-v"});
        } catch (Throwable e) {
            e.printStackTrace();
        }
        int debug = 0;
    }
}
