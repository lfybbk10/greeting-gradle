package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionAnalyzerTest {

    @Test
    void shouldReturnStringsLongerThanMinLength() {
        // Arrange
        Collection<String> strings = Arrays.asList("a", "abc", "abcde", "xy");
        int minLength = 2;

        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

        // Assert
        assertThat(result)
                .hasSize(2)
                .containsExactly("abc", "abcde");
    }

    @Test
    void shouldReturnEmptyListWhenCollectionIsNull() {
        List<String> result = CollectionAnalyzer.findLongStrings(null, 1);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenCollectionIsEmpty() {
        List<String> result = CollectionAnalyzer.findLongStrings(new ArrayList<>(), 1);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullAndEmptyStringsWhenFindingLongStrings() {
        Collection<String> strings = Arrays.asList("a", "abc", "abcde", "xy", null, "");
        List<String> result = CollectionAnalyzer.findLongStrings(strings, 2);
        assertThat(result).containsExactly("abc", "abcde");
    }

    @Test
    void shouldCalculateCorrectDigitSumForPositiveNumber() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(123);

        // Assert
        assertThat(result).isEqualTo(6); // 1 + 2 + 3 = 6
    }

    @Test
    void shouldCalculateCorrectDigitSumForNegativeNumber() {
        int result = CollectionAnalyzer.calculateDigitSum(-123);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void shouldReturnZeroAsDigitSumForZero() {
        int result = CollectionAnalyzer.calculateDigitSum(0);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldCountNumbersWithDigitSumGreaterThanThreshold() {
        int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(Arrays.asList(145, 2, 3, 33), 5);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void shouldReturnZeroWhenCountingWithNullCollection() {
        assertThat(CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(null, 5)).isEqualTo(0);
    }

    @Test
    void shouldReturnZeroWhenCountingWithEmptyCollection() {
        assertThat(CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(new ArrayList<>(), 5)).isEqualTo(0);
    }

    @Test
    void shouldHandleCustomScenarioForDigitSumCount() {
        int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(Arrays.asList(145, 2, 3, 33, 0, null), 2);
        assertThat(result).isEqualTo(3);
    }
}
