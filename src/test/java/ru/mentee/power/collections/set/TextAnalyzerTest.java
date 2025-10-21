package ru.mentee.power.collections.set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TextAnalyzerTest {

    @Test
    @DisplayName("Метод findUniqueWords должен находить все уникальные слова в тексте")
    void shouldFindUniqueWordsInText() {
        String text = "Привет, мир! Привет всем в этом мире!";
        Set<String> expected = new HashSet<>(Arrays.asList("привет", "мир", "всем", "в", "этом", "мире"));

        Set<String> result = TextAnalyzer.findUniqueWords(text);

        assertThat(result)
                .isNotNull()
                .hasSize(6)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findUniqueWords должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullTextInFindUniqueWords() {
        assertThatThrownBy(() -> TextAnalyzer.findUniqueWords(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findUniqueWords должен вернуть пустое множество для пустого текста")
    void shouldReturnEmptySetForEmptyTextInFindUniqueWords() {
        Set<String> result = TextAnalyzer.findUniqueWords("");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Метод findCommonWords должен находить общие слова в двух текстах (операция пересечения)")
    void shouldFindCommonWordsInTexts() {
        String text1 = "Привет, мир! Привет всем в этом мире!";
        String text2 = "Лучший в мире";
        Set<String> expected = new HashSet<>(Arrays.asList("в", "мире"));
        Set<String> result = TextAnalyzer.findCommonWords(text1, text2);
        assertThat(result).hasSize(2).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findCommonWords должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFindCommonWords() {
        assertThatThrownBy(() -> TextAnalyzer.findCommonWords(null, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findUniqueWordsInFirstText должен находить слова, уникальные для первого текста (операция разности)")
    void shouldFindUniqueWordsInFirstText() {
        String text1 = "apple banana orange";
        String text2 = "banana cherry kiwi";
        Set<String> expected = new HashSet<>(Arrays.asList("apple", "orange"));

        Set<String> result = TextAnalyzer.findUniqueWordsInFirstText(text1, text2);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Метод findUniqueWordsInFirstText должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFindUniqueWordsInFirstText() {
        assertThatThrownBy(() -> TextAnalyzer.findUniqueWordsInFirstText("", null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findTopNWords должен находить наиболее часто встречающиеся слова")
    void shouldFindTopNWords() {
        String text = "apple, banana orange, apple, apple, test, test orange";
        LinkedHashSet<String> expected = new LinkedHashSet<>(Arrays.asList("apple", "orange", "test"));
        assertThat(TextAnalyzer.findTopNWords(text, 3)).hasSize(3).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findTopNWords должен выбросить исключение при некорректных аргументах")
    void shouldThrowExceptionForInvalidArgumentsInFindTopNWords() {
        assertThatThrownBy(() -> TextAnalyzer.findTopNWords(null, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findAnagrams должен находить группы анаграмм")
    void shouldFindAnagrams() {
        Set<Set<String>> expected = new HashSet<>();

        expected.add(new TreeSet<>(Arrays.asList("пила", "пали", "липа")));
        expected.add(new TreeSet<>(Arrays.asList("тор", "рот")));
        expected.add(new TreeSet<>(Arrays.asList("но", "он")));
        expected.add(new TreeSet<>(Arrays.asList("тест")));


        Set<Set<String>> result = TextAnalyzer.findAnagrams(Arrays.asList("тор", "пила", "но", "пали", "рот", "он", "тест", "липа"));

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findAnagrams должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInFindAnagrams() {
        assertThatThrownBy(() -> TextAnalyzer.findAnagrams(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод isSubset должен определять, является ли одно множество подмножеством другого")
    void shouldCheckIfSetIsSubset() {
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        boolean result1 = TextAnalyzer.isSubset(set1, set2);
        boolean result2 = TextAnalyzer.isSubset(set2, set1);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("Метод isSubset должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInIsSubset() {
        assertThatThrownBy(()->TextAnalyzer.isSubset(null, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод removeStopWords должен удалять стоп-слова из текста")
    void shouldRemoveStopWordsFromText() {
        String text = "apple banana orange test";
        String expected = "apple  orange ";
        assertThat(TextAnalyzer.removeStopWords(text, new HashSet<>(Arrays.asList("banana", "test"))))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Метод removeStopWords должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInRemoveStopWords() {
        assertThatThrownBy(()->TextAnalyzer.removeStopWords(null, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод compareSetPerformance должен сравнивать производительность разных типов множеств")
    void shouldCompareSetPerformance() {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            words.add("word" + i);
        }

        Map<String, Long> results = TextAnalyzer.compareSetPerformance(words);
        System.out.println(results);

        assertThat(results)
                .isNotNull()
                .isNotEmpty()
                .containsKeys(
                        "HashSet - add", "HashSet - contains", "HashSet - remove",
                        "LinkedHashSet - add", "LinkedHashSet - contains", "LinkedHashSet - remove",
                        "TreeSet - add", "TreeSet - contains", "TreeSet - remove"
                );

        results.forEach((key, value) -> {
            assertThat(value)
                    .as("Ожидалось положительное время выполнения для операции: " + key)
                    .isGreaterThanOrEqualTo(0L);
        });    }
}