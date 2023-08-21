package com.hb.demo;

import java.io.*;

/**
 * 批量转换文件编码：
 */
public class ConvertFleEncoding {

    private static final String path
            = "E:\\AndroidStudioProjects\\MyProjects\\PlaneGame\\app\\src\\main\\java\\com\\hb\\android\\planegame";
    private static final String readFileEncoding = "gbk";
    private static final String writeFileEncoding = "utf-8";

    public static void main(String[] args) {
        changeFormat(path);
        System.out.println("---转换结束---");
    }

    private static void changeFormat(String strPath) {
        File[] files = new File(strPath).listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            int i;
            for (i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    if (!fileName.equalsIgnoreCase("build")
                            && !fileName.contains("."))
                        changeFormat(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("java")) {
                    process(files[i], ".java");
                }
            }
        }
    }

    private static void process(File file, String suffix) {
        String strFileName = file.getAbsolutePath();
        System.out.println("文件：---" + strFileName);
        String content = readTxtFile(file, readFileEncoding);
        File txtFile = new File(strFileName.substring(0,
                strFileName.indexOf(".")) + ".txt");
        writeTxtFile(content, txtFile, writeFileEncoding);
        file.delete();
        strFileName = txtFile.getAbsolutePath();
        txtFile.renameTo(new File(strFileName.substring(0,
                strFileName.lastIndexOf(".")) + suffix));
    }

    private static String readTxtFile(File file, String format) {
        String result = "";
        try {
            BufferedReader bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), format));
            String read = "";
            while ((read = bufReader.readLine()) != null) {
                result = result + read + "\r\n";
            }
            bufReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean writeTxtFile(String content, File fileName,
                                       String format) {
        boolean flag = false;
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes(format));
            o.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
