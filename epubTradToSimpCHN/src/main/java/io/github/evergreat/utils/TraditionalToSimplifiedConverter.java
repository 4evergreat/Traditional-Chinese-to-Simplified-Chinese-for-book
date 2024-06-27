package io.github.evergreat.utils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wendao
 * @since 2024-06-27
 * 用opencc库把繁体中文转换成简体中文
 **/


public class TraditionalToSimplifiedConverter {

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

