package io.github.evergreat.utils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * 提取epub文件
 * @author wendao
 * @since 2024-06-27
 * @Parm epubFilePath 输入文件路径.epub
 * @Parm outputDir 输出文件路径
 **/


public class EpubExtractor {

    public static void extractEpub(String epubFilePath, String outputDir) throws IOException {
        ZipFile zipFile = new ZipFile(new File(epubFilePath));
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();

        while (entries.hasMoreElements()) {
            ZipArchiveEntry entry = entries.nextElement();
            File outputFile = new File(outputDir, entry.getName());

            if (entry.isDirectory()) {
                outputFile.mkdirs();
            } else {
                outputFile.getParentFile().mkdirs();
                try (InputStream inputStream = zipFile.getInputStream(entry);
                     FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    IOUtils.copy(inputStream, outputStream);
                }
            }
        }
    }
}

