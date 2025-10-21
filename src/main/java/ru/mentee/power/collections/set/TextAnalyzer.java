package ru.mentee.power.collections.set;

import java.util.*;

/**
 * Класс для анализа текста с использованием множеств
 */
public class TextAnalyzer {

    /**
     * Находит все уникальные слова в тексте
     * Слова разделяются пробелами и знаками препинания
     *
     * @param text текст для анализа
     * @return множество уникальных слов в нижнем регистре
     * @throws IllegalArgumentException если text равен null
     *
     * Рекомендуемая реализация: HashSet - наиболее эффективная для хранения
     * неупорядоченного набора уникальных элементов
     */
    public static Set<String> findUniqueWords(String text) {
        if(text == null)
            throw new IllegalArgumentException();

        if(text.length() == 0)
            return Collections.emptySet();

        String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");
        return new HashSet<>(Arrays.asList(words));
    }

    /**
     * Находит все слова, которые встречаются в обоих текстах
     * Это операция ПЕРЕСЕЧЕНИЯ множеств (Intersection)
     *
     * @param text1 первый текст
     * @param text2 второй текст
     * @return множество общих слов в нижнем регистре
     * @throws IllegalArgumentException если text1 или text2 равны null
     *
     * Рекомендуемая реализация: HashSet для создания множеств слов
     * и использование метода retainAll() для пересечения
     */
    public static Set<String> findCommonWords(String text1, String text2) {
        if(text1 == null || text2 == null)
            throw new IllegalArgumentException();

        String[] words1 = text1.toLowerCase().split("[\\s\\p{Punct}]+");
        String[] words2 = text2.toLowerCase().split("[\\s\\p{Punct}]+");
        Set<String> wordsSet1 = new HashSet<>(Arrays.asList(words1));
        Set<String> wordsSet2 = new HashSet<>(Arrays.asList(words2));
        wordsSet1.retainAll(wordsSet2);
        return wordsSet1;
    }

    /**
     * Находит все слова, которые встречаются в первом тексте, но отсутствуют во втором
     * Это операция РАЗНОСТИ множеств (Difference)
     *
     * @param text1 первый текст
     * @param text2 второй текст
     * @return множество слов, уникальных для первого текста, в нижнем регистре
     * @throws IllegalArgumentException если text1 или text2 равны null
     *
     * Рекомендуемая реализация: HashSet для создания множеств слов
     * и использование метода removeAll() для вычитания множеств
     */
    public static Set<String> findUniqueWordsInFirstText(String text1, String text2) {
        if(text1 == null || text2 == null)
            throw new IllegalArgumentException();

        String[] words1 = text1.toLowerCase().split("[\\s\\p{Punct}]+");
        String[] words2 = text2.toLowerCase().split("[\\s\\p{Punct}]+");
        Set<String> wordsSet1 = new HashSet<>(Arrays.asList(words1));
        Set<String> wordsSet2 = new HashSet<>(Arrays.asList(words2));
        wordsSet1.removeAll(wordsSet2);
        return wordsSet1;
    }

    /**
     * Находит топ-N наиболее часто встречающихся слов в тексте
     *
     * @param text текст для анализа
     * @param n количество слов для возврата
     * @return множество из n наиболее часто встречающихся слов
     * @throws IllegalArgumentException если text равен null или n <= 0
     *
     * Рекомендуемая реализация: использование HashMap для подсчета частоты слов
     * и LinkedHashSet для хранения результата с сохранением порядка вставки
     */
    public static Set<String> findTopNWords(String text, int n) {
        if(text == null || n <= 0)
            throw new IllegalArgumentException();

        HashMap<String, Integer> countOfOccurrences = new HashMap<>();
        String[] words = text.split("[\\s\\p{Punct}]+");
        for (String word : words) {
            if(word==null || word.isEmpty())
                continue;

            if (countOfOccurrences.containsKey(word)) {
                countOfOccurrences.put(word, countOfOccurrences.get(word) + 1);
            }
            else {
                countOfOccurrences.put(word, 1);
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countOfOccurrences.entrySet());

        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        Set<String> result = new LinkedHashSet<>();
        for (int i = 0; i < Math.min(n, entries.size()); i++) {
            result.add(entries.get(i).getKey());
        }

        return result;
    }

    /**
     * Находит все анаграммы в списке слов
     * Анаграммы - это слова, составленные из одних и тех же букв
     * Например: "пила", "липа", "пали" - анаграммы
     *
     * @param words список слов для проверки
     * @return множество множеств, где каждое внутреннее множество содержит группу анаграмм
     * @throws IllegalArgumentException если words равен null
     *
     * Рекомендуемая реализация: использование TreeSet для внутренних множеств
     * для хранения анаграмм в алфавитном порядке
     */
    public static Set<Set<String>> findAnagrams(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException();
        }

        Map<String, Set<String>> anagramGroups = new HashMap<>();

        for (String word : words) {
            if (word == null || word.isBlank()) continue;

            String normalized = word.toLowerCase();
            char[] chars = normalized.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            anagramGroups
                    .computeIfAbsent(key, k -> new TreeSet<>())
                    .add(word);
        }

        Set<Set<String>> result = new LinkedHashSet<>();
        for (Set<String> group : anagramGroups.values()) {
            if (!group.isEmpty()) {
                result.add(group);
            }
        }

        return result;
    }

    /**
     * Проверяет, является ли первое множество подмножеством второго
     * Метод демонстрирует операцию проверки ПОДМНОЖЕСТВА
     *
     * @param <T> тип элементов множества
     * @param set1 первое множество
     * @param set2 второе множество
     * @return true, если все элементы set1 содержатся в set2
     * @throws IllegalArgumentException если set1 или set2 равны null
     *
     * Рекомендуемая реализация: использование метода containsAll()
     */
    public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
        if(set1 == null || set2 == null)
            throw new IllegalArgumentException();
        return set2.containsAll(set1);
    }

    /**
     * Удаляет все стоп-слова из текста
     * Демонстрирует ПРАКТИЧЕСКОЕ ПРИМЕНЕНИЕ множеств для фильтрации
     *
     * @param text исходный текст
     * @param stopWords множество стоп-слов
     * @return текст без стоп-слов
     * @throws IllegalArgumentException если text или stopWords равны null
     *
     * Рекомендуемая реализация: использование HashSet для быстрой проверки
     * принадлежности слова к стоп-словам
     */
    public static String removeStopWords(String text, Set<String> stopWords) {
        if(text == null || stopWords == null)
            throw new IllegalArgumentException();

        for (String stopWord : stopWords) {
            text = text.replace(stopWord, "");
        }
        return text;
    }

    /**
     * Сравнивает производительность работы с разными типами множеств
     * (HashSet, LinkedHashSet, TreeSet)
     *
     * @param words список слов для тестирования
     * @return Map с результатами замеров времени (в наносекундах)
     */
    public static Map<String, Long> compareSetPerformance(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("words cannot be null");
        }

        Map<String, Long> results = new LinkedHashMap<>();

        // Наборы для теста
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
        Set<String> treeSet = new TreeSet<>();

        // Тестируем три операции: add, contains, remove
        results.putAll(testSet("HashSet", hashSet, words));
        results.putAll(testSet("LinkedHashSet", linkedHashSet, words));
        results.putAll(testSet("TreeSet", treeSet, words));

        return results;
    }

    private static Map<String, Long> testSet(String name, Set<String> set, List<String> words) {
        Map<String, Long> map = new LinkedHashMap<>();

        // --- Добавление ---
        long start = System.nanoTime();
        for (String w : words) {
            set.add(w);
        }
        long addTime = System.nanoTime() - start;
        map.put(name + " - add", addTime);

        // --- Поиск (contains) ---
        start = System.nanoTime();
        for (String w : words) {
            set.contains(w);
        }
        long containsTime = System.nanoTime() - start;
        map.put(name + " - contains", containsTime);

        // --- Удаление ---
        start = System.nanoTime();
        for (String w : words) {
            set.remove(w);
        }
        long removeTime = System.nanoTime() - start;
        map.put(name + " - remove", removeTime);

        return map;
    }
}