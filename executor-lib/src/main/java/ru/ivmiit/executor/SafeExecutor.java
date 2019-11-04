package ru.ivmiit.executor;

import ru.ivmiit.executor.utils.NativeLoadUtils;

public class SafeExecutor {
    static {
        NativeLoadUtils.putLibToTmpAndLoad("libEjudgeExecutorDll.so");
    }

    native void main(int argc, String[] argv);

    public static void main(String[] args) {
        SafeExecutor safeExecutor = new SafeExecutor();
        safeExecutor.main(1, new String[]{"test"});
    }
}
