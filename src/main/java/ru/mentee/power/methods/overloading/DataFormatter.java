package ru.mentee.power.methods.overloading;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Класс для форматирования различных типов данных в строку
 */
public class DataFormatter {

    /**
     * Форматирует целое число, разделяя его по разрядам
     *
     * @param value Целое число
     * @return Отформатированное представление числа
     */
    public static String format(int value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        df.setGroupingUsed(true);
        return df.format(value);
    }

    /**
     * Форматирует целое число с указанием префикса и суффикса
     *
     * @param value Целое число
     * @param prefix Префикс (например, символ валюты)
     * @param suffix Суффикс (например, код валюты)
     * @return Отформатированное представление числа с префиксом и суффиксом
     */
    public static String format(int value, String prefix, String suffix) {
        String formattedNumber = format(value);

        // Безопасная обработка null и пробелов
        String pre = (prefix == null || prefix.isEmpty()) ? "" : prefix;
        String suf = (suffix == null || suffix.isEmpty()) ? "" : " " + suffix;

        return pre + formattedNumber + suf;
    }

    /**
     * Форматирует число с плавающей точкой, используя два десятичных знака
     *
     * @param value Число с плавающей точкой
     * @return Отформатированное представление числа
     */
    public static String format(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());

        // Устанавливаем пробел в качестве разделителя разрядов
        symbols.setGroupingSeparator(' ');
        // Устанавливаем запятую в качестве десятичного разделителя
        symbols.setDecimalSeparator(',');

        // Создаем шаблон:
        // "#,##0.00" - использует 0 для обязательных десятичных разрядов
        // и # для необязательных целых, а также разделитель разрядов
        DecimalFormat formatter = new DecimalFormat("#,##0.00", symbols);

        return formatter.format(value);
    }

    /**
     * Форматирует число с плавающей точкой с указанным количеством десятичных знаков
     *
     * @param value Число с плавающей точкой
     * @param decimalPlaces Количество знаков после запятой
     * @return Отформатированное представление числа
     */
    public static String format(double value, int decimalPlaces) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' ');  // пробел между тысячами
        symbols.setDecimalSeparator(',');   // запятая для дробной части

        // Формируем шаблон, например "#,##0.00" или "#,##0"
        StringBuilder pattern = new StringBuilder("#,##0");
        if (decimalPlaces > 0) {
            pattern.append('.');
            for (int i = 0; i < decimalPlaces; i++) {
                pattern.append('0');
            }
        }

        // Создаём форматтер с нужными символами
        DecimalFormat df = new DecimalFormat(pattern.toString(), symbols);
        df.setGroupingUsed(true);

        return df.format(value);
    }

    /**
     * Форматирует дату в формате "дд.мм.гггг"
     *
     * @param date Дата
     * @return Отформатированное представление даты
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    /**
     * Форматирует дату согласно указанному шаблону
     *
     * @param date Дата
     * @param pattern Шаблон форматирования (как в SimpleDateFormat)
     * @return Отформатированное представление даты
     */
    public static String format(Date date, String pattern) {
        if (date == null) return "";
        if (pattern == null || pattern.isEmpty()) {
            return format(date); // fallback на стандартный формат
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return sdf.format(date);
    }

    /**
     * Форматирует список строк, объединяя их через запятую
     *
     * @param items Список строковых элементов
     * @return Объединенная строка
     */
    public static String format(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        // Преобразуем все элементы в строку и объединяем через ", "
        return items.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Форматирует список строк, объединяя их через указанный разделитель
     *
     * @param items Список строковых элементов
     * @param delimiter Разделитель
     * @return Объединенная строка
     */
    public static String format(List<String> items, String delimiter) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        if (delimiter == null) delimiter = ", ";
        return items.stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
    }
}