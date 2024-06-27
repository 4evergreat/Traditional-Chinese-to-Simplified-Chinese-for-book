package io.github.evergreat.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wendao
 * @since 2024-06-27
 **/

public class EpubPacker {

    public static void packEpub(String sourceDir, String outputFilePath) throws IOException {
        try (ZipArchiveOutputStream archive = new ZipArchiveOutputStream(new FileOutputStream(outputFilePath))) {
            File sourceFile = new File(sourceDir);
            addFileToZip(sourceFile, sourceFile, archive);
        }
    }

    private static void addFileToZip(File rootDir, File sourceFile, ZipArchiveOutputStream archive) throws IOException {
        if (sourceFile.isDirectory()) {
            for (File file : sourceFile.listFiles()) {
                addFileToZip(rootDir, file, archive);
            }
        } else {
            String entryName = rootDir.toURI().relativize(sourceFile.toURI()).getPath();
            ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
            archive.putArchiveEntry(entry);
            try (FileInputStream inputStream = new FileInputStream(sourceFile)) {
                IOUtils.copy(inputStream, archive);
            }
            archive.closeArchiveEntry();
        }
    }
}
