package io.github.evergreat;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;

public class EpubConverterInOne {

    public static void main(String[] args) {
 /*       String epubFilePath = "path/to/your/epub.epub";
        String outputDir = "path/to/extracted";
        String convertedDir = "path/to/converted";
        String outputEpubFilePath = "path/to/output.epub";*/

        String epubFilePath = "epubSources/在森崎書店的日子 = 森崎書店の日々 (八木沢里志 著  張秋明 譯) (Z-Library).epub";
        String outputDir = "epubSources/temp/";
        String convertedDir = "epubOutput/temp/";
        String outputEpubFilePath = "epubOutput/在森崎書店的日子 = 森崎書店の日々 (八木沢里志 著  張秋明 譯) (Z-Library).epub";
        try {
            // 解压EPUB文件
            EpubExtractor.extractEpub(epubFilePath, outputDir);

            // 转换繁体中文到简体中文
            TraditionalToSimplifiedConverter.convertDirectory(outputDir, convertedDir);

            // 重新打包EPUB文件
            EpubPacker.packEpub(convertedDir, outputEpubFilePath);

            System.out.println("EPUB文件转换完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class EpubExtractor {

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

    public static class TraditionalToSimplifiedConverter {

        public static void convertFile(String inputFilePath, String outputFilePath) throws IOException {
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String simplifiedContent = ZhConverterUtil.toSimple(content);

            File outputFile = new File(outputFilePath);
            outputFile.getParentFile().mkdirs(); // 确保目标目录存在
            Files.write(Paths.get(outputFilePath), simplifiedContent.getBytes());
        }

        public static void convertDirectory(String inputDir, String outputDir) throws IOException {
            File dir = new File(inputDir);
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    convertDirectory(file.getAbsolutePath(), outputDir + "/" + file.getName());
                } else if (file.getName().endsWith(".xhtml") || file.getName().endsWith(".html")) {
                    String outputFilePath = outputDir + "/" + file.getName();
                    convertFile(file.getAbsolutePath(), outputFilePath);
                }
            }
        }
    }

    public static class EpubPacker {

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
}
