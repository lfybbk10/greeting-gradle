package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением и префиксом 'Ошибка конфигурации '")
    void shouldCreateExceptionWithMessage() {
        // given
        String message = "Не найден файл настроек";

        // when
        ConfigException exception = new ConfigException(message);

        // then
        assertThat(exception)
                .isInstanceOf(ConfigException.class)
                .hasMessage("Ошибка конфигурации " + message)
                .hasNoCause();
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, префиксом и причиной")
    void shouldCreateExceptionWithMessageAndCause() {
        // given
        String message = "Не удалось прочитать файл конфигурации";
        Throwable cause = new IllegalArgumentException("Неверный путь");

        // when
        ConfigException exception = new ConfigException(message, cause);

        // then
        assertThat(exception)
                .isInstanceOf(ConfigException.class)
                .hasMessage("Ошибка конфигурации " + message)
                .hasCause(cause)
                .cause() // проверяем саму причину
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Неверный путь");
    }
}
