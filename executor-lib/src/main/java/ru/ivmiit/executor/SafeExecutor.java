package ru.ivmiit.executor;

import ru.ivmiit.executor.utils.NativeLoadUtils;

import java.io.File;

public class SafeExecutor {
    static {
        File file = new File(SafeExecutor.class.getClassLoader().getResource("libEjudgeExecutorDll.so").getPath());
        System.load(file.getAbsolutePath());
//        NativeLoadUtils.putLibToTmpAndLoad("libEjudgeExecutorDll.so");
    }

    private native int main(int argc, String[] argv);

    public static void main(String[] args) {
        SafeExecutor safeExecutor = new SafeExecutor();
        safeExecutor.main(0, new String[]{""});
    }
}
