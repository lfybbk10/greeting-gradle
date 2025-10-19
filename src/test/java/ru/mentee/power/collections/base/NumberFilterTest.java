package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class NumberFilterTest {

    @Test
    void shouldReturnOnlyEvenNumbersFromMixedList() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Act
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);

        // Assert
        assertThat(result)
                .hasSize(3)
                .containsExactly(2, 4, 6);
    }

    @Test
    void shouldReturnEmptyListWhenSourceContainsOnlyOddNumbers() {
        List<Integer> numbers = Arrays.asList(1, 3, 5);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllNumbersWhenSourceContainsOnlyEvenNumbers() {
        List<Integer> numbers = Arrays.asList(2, 4, 6);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).hasSize(3).containsExactly(2, 4, 6);
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsEmpty() {
        List<Integer> result = NumberFilter.filterEvenNumbers(new ArrayList<>());
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsNull() {
        List<Integer> result = NumberFilter.filterEvenNumbers(null);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullElementsWhenFilteringList() {
        // TODO: Напишите тест для проверки обработки списка с null элементами
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, null);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).containsExactly(2, 4, 6);
    }

    @Test
    void shouldHandleCustomScenarioForFilterEvenNumbers() {
        // TODO: Придумайте и реализуйте собственный тестовый сценарий для проверки метода
        List<Integer> numbers = Arrays.asList(0,null, 4, 5);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).containsExactly(0, 4);
    }
}
