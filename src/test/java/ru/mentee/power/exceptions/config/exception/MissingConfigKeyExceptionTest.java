package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissingConfigKeyExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением и ключом")
    void shouldCreateExceptionWithMessageAndKey() {
        String key = "database.url";

        MissingConfigKeyException exception = new MissingConfigKeyException(key);

        assertThat(exception)
                .isInstanceOf(MissingConfigKeyException.class)
                .hasMessage("Ошибка конфигурации Key not found: " + key)
                .hasNoCause();

        assertThat(exception.getMissingConfigKey())
                .isEqualTo(key);
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом и причиной")
    void shouldCreateExceptionWithMessageKeyAndCause() {
        String key = "api.token";
        Throwable cause = new IllegalArgumentException("Некорректный формат токена");

        MissingConfigKeyException exception = new MissingConfigKeyException(key, cause);

        assertThat(exception)
                .isInstanceOf(MissingConfigKeyException.class)
                .hasMessage("Ошибка конфигурации Key not found: " + key)
                .hasCause(cause);

        assertThat(exception.getMissingConfigKey())
                .isEqualTo(key);

        assertThat(exception.getCause())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Некорректный формат токена");
    }

    @Test
    @DisplayName("Должен вернуть ключ, который отсутствует в конфигурации")
    void shouldReturnMissingKey() {
        String key = "server.port";

        MissingConfigKeyException exception = new MissingConfigKeyException(key);

        assertThat(exception.getMissingConfigKey())
                .isEqualTo("server.port");
    }
}
