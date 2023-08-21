package com.hb.poihandleexcel;

/**
 * 字符串工具类，包含以下方法：
 * <p> toUpperCase(String),将输入的字符串全部转换为大写;
 * <p> toLowerCase(String),将输入的字符串全部转换为小写;
 * <p> toCamelCase(String),将输入的字符串转换为驼峰形式 包含空格;
 * <p> toCamelCaseNoSpace(String),将输入的字符串转换为驼峰形式 不含空格;
 * <p> fromCamelCase(String),将驼峰形式的字符串转换为句子;
 */
public class TextUtils {

    /**
     * 将输入的字符串全部转换为大写
     *
     * @param input 原字符串
     * @return 大写形式的字符串
     */
    public static String toUpperCase(String input) {
        return input.toUpperCase();
    }

    /**
     * 将输入的字符串全部转换为小写
     *
     * @param input 原字符串
     * @return 小写形式的字符串
     */
    public static String toLowerCase(String input) {
        return input.toLowerCase();
    }

    /**
     * 将输入的字符串转换为驼峰形式，包含空格
     *
     * @param input 原字符串
     * @return 驼峰形式的字符串（包含空格）
     */
    public static String toCamelCase(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean capitalizeNextChar = true;
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                // 遇到空格，将capitalizeNextChar标记为true
                capitalizeNextChar = true;
                stringBuilder.append(c);
            } else {
                // 需要大写字母，将字符转换为大写并将capitalizeNextChar标记为false
                stringBuilder.append(capitalizeNextChar ? Character.toUpperCase(c) : Character.toLowerCase(c));
                capitalizeNextChar = false;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将输入的字符串转换为驼峰形式，不含空格
     *
     * @param input 原字符串
     * @return 驼峰形式的字符串（不含空格）
     */
    public static String toCamelCaseNoSpace(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        // 将字符串用空格分成一个一个的单词
        String[] words = input.split("\\s");
        for (String word : words) {
            if (!word.isEmpty()) { // 跳过空单词
                // 将首字母大写，其余字符小写，拼接到结果中
                stringBuilder.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将驼峰形式的字符串转换为句子
     *
     * @param input 原字符串
     * @return 驼峰形式的字符串（不含空格）
     */
    public static String fromCamelCase(String input) {
        StringBuilder sentence = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (i > 0 && Character.isUpperCase(currentChar)) {
                // 在大写字母前加入空格
                sentence.append(" ");
            }
            // 添加当前字符到句子中
            sentence.append(currentChar);
        }
        return sentence.toString();
    }

}