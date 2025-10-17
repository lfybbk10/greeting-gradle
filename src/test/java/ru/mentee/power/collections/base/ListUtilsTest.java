package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ListUtilsTest {

    @Test
    void shouldMergeTwoListsAndRemoveDuplicates() {
        // Arrange
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = Arrays.asList("Banana", "Cherry", "Date");

        // Act
        List<String> result = ListUtils.mergeLists(list1, list2);

        // Assert
        assertThat(result)
                .hasSize(4)
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");
    }

    @Test
    void shouldReturnFirstListElementsWhenSecondListIsEmpty() {
        // TODO: Напишите тест для проверки объединения, когда один из списков пустой
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = new ArrayList<>();
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).hasSize(3).containsExactly("Apple", "Banana", "Cherry");
    }

    @Test
    void shouldReturnEmptyListWhenBothListsAreEmpty() {
        // TODO: Напишите тест для проверки объединения двух пустых списков
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnSecondListWhenFirstListIsNull() {
        // TODO: Напишите тест для проверки объединения, когда один из списков null
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = null;
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).hasSize(3).containsExactly("Apple", "Banana", "Cherry");
    }

    @Test
    void shouldReturnEmptyListWhenBothListsAreNull() {
        // TODO: Напишите тест для проверки объединения двух null списков
        List<String> list1 = null;
        List<String> list2 = null;
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullElementsWhenMergingLists() {
        // TODO: Напишите тест для проверки, что null элементы внутри списков игнорируются при объединении
        List<String> list1 = Arrays.asList("Apple", "Banana", null);
        List<String> list2 = Arrays.asList("Banana", "Cherry", null);
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).hasSize(3).containsExactly("Apple", "Banana", "Cherry");
    }

    @Test
    void shouldHandleCustomScenarioForMergeLists() {
        // TODO: Придумайте и реализуйте собственный тестовый сценарий для проверки метода
        List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> list2 = Arrays.asList("Banana", "Cherry", "Date");
        List<String> result = ListUtils.mergeLists(list1, list2);
        assertThat(result).hasSize(4).containsExactly("Apple", "Banana", "Cherry", "Date");
    }
}
