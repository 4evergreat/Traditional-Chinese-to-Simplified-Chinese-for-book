package io.github.evergreat;

import io.github.evergreat.utils.EpubExtractor;
import io.github.evergreat.utils.EpubPacker;
import io.github.evergreat.utils.TraditionalToSimplifiedConverter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
       /*
        String epubFilePath = "path/to/your/epub.epub";
        String outputDir = "path/to/extracted";
        String convertedDir = "path/to/converted";
        String outputEpubFilePath = "path/to/output.epub";
        */
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
}
