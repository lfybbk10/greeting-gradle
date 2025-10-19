package ru.mentee.power.collections.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArrayListAnalyzerTest {

    @Test
    @DisplayName("Метод filterByPrefix должен корректно фильтровать строки по префиксу")
    void shouldFilterByPrefixCorrectly() {
        List<String> input = new ArrayList<>(Arrays.asList("apple", "banana", "apricot", "orange", "app"));
        List<String> expected = Arrays.asList("apple", "apricot", "app");

        List<String> result = ArrayListAnalyzer.filterByPrefix(input, "ap");

        assertThat(result).isNotNull().hasSize(3).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод filterByPrefix должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFilterByPrefix() {
        assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(null, "test")).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(Arrays.asList("test"), null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод filterByPrefix должен вернуть пустой список, если нет совпадений")
    void shouldReturnEmptyListWhenNoMatchesInFilterByPrefix() {
        List<String> input = Arrays.asList("apple", "banana", "apricot", "orange", "app");
        assertThat(ArrayListAnalyzer.filterByPrefix(input, "appp")).isEmpty();
    }

    @Test
    @DisplayName("Метод findMax должен корректно находить максимальный элемент")
    void shouldFindMaxCorrectly() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 8, 1, 9, 3));

        Integer result = ArrayListAnalyzer.findMax(numbers);

        assertEquals(9, result);
    }

    @Test
    @DisplayName("Метод findMax должен выбросить исключение для пустого списка или null")
    void shouldThrowExceptionForEmptyOrNullListInFindMax() {
        assertThatThrownBy(() -> ArrayListAnalyzer.findMax(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ArrayListAnalyzer.findMax(new ArrayList<>())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод partition должен корректно разбивать список на части")
    void shouldPartitionListCorrectly() {
        List<String> input = Arrays.asList("apple", "banana", "apricot", "orange", "app");
        assertThat(ArrayListAnalyzer.partition(input, 2)).hasSize(3).containsExactly(Arrays.asList("apple", "banana"), Arrays.asList("apricot", "orange"), Arrays.asList("app"));

        input = Arrays.asList("apple", "banana", "apricot", "orange", "app", "test");
        assertThat(ArrayListAnalyzer.partition(input, 2)).hasSize(3).containsExactly(Arrays.asList("apple", "banana"), Arrays.asList("apricot", "orange"), Arrays.asList("app", "test"));
    }

    @Test
    @DisplayName("Метод partition должен выбросить исключение при некорректных аргументах")
    void shouldThrowExceptionForInvalidArgumentsInPartition() {
        assertThatThrownBy(() -> ArrayListAnalyzer.partition(null, 2)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ArrayListAnalyzer.partition(new ArrayList<>(), 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод isPalindrome должен корректно определять палиндромы")
    void shouldIdentifyPalindromesCorrectly() {
        List<Integer> input = Arrays.asList(1, 2, 3, 3, 2, 1);
        assertThat(ArrayListAnalyzer.isPalindrome(input)).isEqualTo(true);

        input = Arrays.asList(1, 2, 3, 3, 2, 1, 1);
        assertThat(ArrayListAnalyzer.isPalindrome(input)).isEqualTo(false);

        input = Arrays.asList(1, 2, 3, 3, 2, 2);
        assertThat(ArrayListAnalyzer.isPalindrome(input)).isEqualTo(false);
    }

    @Test
    @DisplayName("Метод isPalindrome должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInIsPalindrome() {
        // TODO: Напишите тест для проверки исключения при null аргументе
        assertThatThrownBy(() -> ArrayListAnalyzer.isPalindrome(null)).isInstanceOf(IllegalArgumentException.class);
    }
}