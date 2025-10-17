package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleCollectionTasksTest {

    @Test
    void shouldReturnCorrectCountForStringsStartingWithGivenLetter() {
        // Arrange
        List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Alex");
        char letter = 'A';

        // Act
        int result = SimpleCollectionTasks.countStringsStartingWith(names, letter);

        // Assert
        assertThat(result).isEqualTo(3);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        int result = SimpleCollectionTasks.countStringsStartingWith(new ArrayList<>(), 'A');
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldReturnZeroForNullList() {
        List<String> names = null;
        int result = SimpleCollectionTasks.countStringsStartingWith(names, 'A');
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldIgnoreNullAndEmptyStringsInList() {
        List<String> names = Arrays.asList("Alice", "Bob", "Alex", null, "");
        int result = SimpleCollectionTasks.countStringsStartingWith(names, 'A');
        assertThat(result).isEqualTo(2);
    }

    @Test
    void shouldBeCaseInsensitiveWhenComparingLetters() {
        List<String> names = Arrays.asList("Alice", "Bob", "alex");
        int result = SimpleCollectionTasks.countStringsStartingWith(names, 'A');
        assertThat(result).isEqualTo(2);
    }

    @Test
    void shouldHandleCustomScenario() {
        // TODO: Придумайте и реализуйте собственный тестовый сценарий для проверки метода
        List<String> names = Arrays.asList("Alice", "Bob", "alex", null, "test");
        int result = SimpleCollectionTasks.countStringsStartingWith(names, 'A');
        assertThat(result).isEqualTo(2);
    }
}