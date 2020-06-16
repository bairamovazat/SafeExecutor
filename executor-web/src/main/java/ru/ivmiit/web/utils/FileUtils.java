package ru.ivmiit.web.utils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;

public class FileUtils {

    public static void writeToFile(String filePath, String text, boolean create) throws IOException {

        File commandFile = new File(filePath);
        if (create) {
            commandFile.setWritable(true, false);
            commandFile.setReadable(true, false);
            commandFile.setExecutable(true, false);
            boolean resultJavaFile = commandFile.createNewFile();
            commandFile.setWritable(true, false);
            commandFile.setReadable(true, false);
            commandFile.setExecutable(true, false);
        }

        PrintWriter out = new PrintWriter(commandFile.getAbsolutePath(), "UTF-8");
        out.print(text);
        out.close();
    }


    public static void unzip(InputStream zip, String destPath) throws IOException {
        ZipArchiveInputStream input = new ZipArchiveInputStream(zip, "UTF-8", true, true);

        File destination = new File(destPath);
        ArchiveEntry entry = null;
        while ((entry = input.getNextEntry()) != null) {
            if (!input.canReadEntryData(entry)) {
                // log something?
                continue;
            }
            File f = newFile(destination, entry);
            if (entry.isDirectory()) {
                if (!f.isDirectory() && !f.mkdirs()) {
                    throw new IOException("failed to create directory " + f);
                }
            } else {
                File parent = f.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("failed to create directory " + parent);
                }
                try (OutputStream o = Files.newOutputStream(f.toPath())) {
                    IOUtils.copy(input, o);
                }
            }
        }

    }

    public static File newFile(File destinationDir, ArchiveEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}
