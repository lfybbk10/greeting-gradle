package ru.mentee.power.methods;

/**
 * Утилитарный класс для работы со строками.
 */
public class StringUtils {

    /**
     * Подсчитывает количество вхождений символа в строке.
     *
     * @param str Исходная строка
     * @param target Искомый символ
     * @return Количество вхождений символа
     */
    public static int countChars(String str, char target) {
        if (str == null || str.length() == 0)
            return 0;

        int count = 0;
        for (char c : str.toCharArray()) {
            if(c == target)
                count++;
        }
        return count;
    }

    /**
     * Обрезает строку до указанной максимальной длины.
     * Если строка длиннее maxLength, добавляет "..." в конце.
     *
     * @param str Исходная строка
     * @param maxLength Максимальная длина
     * @return Обрезанная строка
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() == 0)
            return "";

        String returnedString = str.substring(0, maxLength);
        if(str.length() > maxLength)
            returnedString+="...";
        return returnedString;
    }

    /**
     * Проверяет, является ли строка палиндромом.
     * Игнорирует регистр и не-буквенные символы.
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     */
    public static boolean isPalindrome(String str) {
        if (str == null || str.length() == 0)
            return false;

        str = str.toLowerCase();
        str = str.replaceAll("[^a-zA-Z0-9]", "");
        str = str.replaceAll("\\s+", "");
        for (int i = 0; i < str.length() / 2; i++) {
            if(str.charAt(i) != str.charAt(str.length() - 1 - i))
                return false;
        }
        return true;
    }

    /**
     * Заменяет все последовательности пробельных символов одним пробелом.
     * Удаляет пробелы в начале и конце строки.
     *
     * @param str Исходная строка
     * @return Нормализованная строка
     */
    public static String normalizeSpaces(String str) {
        if(str == null || str.length() == 0)
            return "";

        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    /**
     * Объединяет массив строк, используя указанный разделитель.
     *
     * @param strings Массив строк
     * @param delimiter Разделитель
     * @return Объединенная строка
     */
    public static String join(String[] strings, String delimiter) {
        if(strings == null || strings.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            if(strings[i] == null)
                continue;

            sb.append(strings[i]);
            if(i < strings.length - 1)
                sb.append(delimiter);
        }

        return sb.toString();
    }
}