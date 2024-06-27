# Traditional-Chinese-to-Simplified-Chinese-for-book
繁中转简中！！！把epub格式的书本内的繁体中文转换成简体中文。

## 把epub中的繁体中文转换成简体中文主要有以下几步
1. 解析EPUB文档
2. 读取和解压EPUB文件
3. 转换繁体中文到简体中文
   [![opencc4j](https://jitpack.io/v/houbb/opencc4j.svg)](https://jitpack.io/#houbb/opencc4j)
4. 重新打包EPUB文件

## 使用方法
/*

#Main文件
String epubFilePath = "path/to/your/epub.epub";
String outputEpubFilePath = "path/to/output.epub";
*/

1.把需要转换的.edpu文件存到epubSources/ 中
2.修改Main中上述文件路径为你的文件
3.在epubOutput/中可以看到转换后的文件

