package ru.ivmiit.executor.utils;

import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class NativeLoadUtils {
    /**
     * Копирует файл во временную папку и подгружает его
     * Но пока это не работает
     */
    @Deprecated
    public static void putLibToTmpAndLoad(String file) {
        try {
            InputStream in = NativeLoadUtils.class.getResourceAsStream(file);
            File fileOut = new File(System.getProperty("java.io.tmpdir") + "/dllDir/" + file);
            fileOut.createNewFile();
            OutputStream out = new FileOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
            System.load(fileOut.getAbsolutePath());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load required DLL " + file, e);
        }
    }
}
