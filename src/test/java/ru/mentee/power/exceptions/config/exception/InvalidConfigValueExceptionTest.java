package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidConfigValueExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением и некорректным значением")
    void shouldCreateExceptionWithMessageKeyAndValue() {
        String value = "123abc";

        InvalidConfigValueException exception = new InvalidConfigValueException(value);

        assertThat(exception)
                .isInstanceOf(InvalidConfigValueException.class)
                .hasMessage("Ошибка конфигурации Invalid config value: " + value)
                .hasNoCause();

        assertThat(exception.getInvalidConfigValue())
                .isEqualTo(value);
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, некорректным значением и причиной")
    void shouldCreateExceptionWithMessageKeyValueAndCause() {
        String value = "null";
        Throwable cause = new IllegalArgumentException("Недопустимое значение конфигурации");

        InvalidConfigValueException exception = new InvalidConfigValueException(value, cause);

        assertThat(exception)
                .isInstanceOf(InvalidConfigValueException.class)
                .hasMessage("Ошибка конфигурации Invalid config value: " + value)
                .hasCause(cause);

        assertThat(exception.getInvalidConfigValue()).isEqualTo(value);

        assertThat(exception.getCause())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Недопустимое значение конфигурации");
    }

    @Test
    @DisplayName("Должен вернуть некорректное значение")
    void shouldReturnInvalidValue() {
        String value = "bad_format";

        InvalidConfigValueException exception = new InvalidConfigValueException(value);

        assertThat(exception.getInvalidConfigValue()).isEqualTo("bad_format");
    }
}
