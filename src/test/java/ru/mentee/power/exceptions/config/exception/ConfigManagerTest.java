package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigManagerTest {

    private ConfigManager configManager;
    private Map<String, String> testConfig;

    @BeforeEach
    void setUp() {
        // TODO: Инициализировать тестовую карту конфигурации
        testConfig = new HashMap<>();

        // TODO: Добавить тестовые данные в карту
        testConfig.put("database.url", "jdbc:mysql://localhost:3306/mydb");
        testConfig.put("database.user", "root");
        testConfig.put("database.password", "secret");
        testConfig.put("server.port", "8080");
        testConfig.put("debug.mode", "true");
        testConfig.put("max.connections", "100");
        testConfig.put("timeout", "30");


        configManager = new ConfigManager(testConfig);
    }

    @Test
    @DisplayName("Должен успешно получить строковое значение по существующему ключу")
    void shouldGetStringValueWhenKeyExists() throws MissingConfigKeyException {
        String value = configManager.getRequiredValue("database.user");
        assertThat(value).isEqualTo("root");
    }

    @Test
    @DisplayName("Должен выбросить MissingConfigKeyException при запросе несуществующего ключа")
    void shouldThrowMissingConfigKeyExceptionWhenKeyDoesNotExist() {
        assertThatThrownBy(() -> configManager.getRequiredValue("database.urll")).isInstanceOf(MissingConfigKeyException.class);
    }

    @Test
    @DisplayName("Должен успешно получить целочисленное значение по существующему ключу")
    void shouldGetIntValueWhenKeyExists() throws MissingConfigKeyException, InvalidConfigValueException {
        int maxConnections = configManager.getRequiredIntValue("max.connections");
        assertThat(maxConnections).isEqualTo(100);
    }

    @Test
    @DisplayName("Должен выбросить InvalidConfigValueException при запросе некорректного целочисленного значения")
    void shouldThrowInvalidConfigValueExceptionWhenIntValueIsInvalid() {
        assertThatThrownBy(() -> configManager.getRequiredIntValue("database.url")).isInstanceOf(InvalidConfigValueException.class);
    }

    @Test
    @DisplayName("Должен успешно получить булево значение по существующему ключу")
    void shouldGetBooleanValueWhenKeyExists() throws MissingConfigKeyException, InvalidConfigValueException {
        boolean value = configManager.getRequiredBooleanValue("debug.mode");
        assertThat(value).isEqualTo(true);
    }

    @Test
    @DisplayName("Должен выбросить InvalidConfigValueException при запросе некорректного булева значения")
    void shouldThrowInvalidConfigValueExceptionWhenBooleanValueIsInvalid() {
        assertThatThrownBy(() -> configManager.getRequiredBooleanValue("database.url")).isInstanceOf(InvalidConfigValueException.class);
    }

    @Test
    @DisplayName("Должен успешно добавить новое значение в конфигурацию")
    void shouldAddNewValueToConfig() throws MissingConfigKeyException {
        configManager.setValue("testKey", "testValue");
        assertThat(configManager.getRequiredValue("testKey")).isEqualTo("testValue");
    }

    @Test
    @DisplayName("Должен успешно обновить существующее значение в конфигурации")
    void shouldUpdateExistingValueInConfig() throws MissingConfigKeyException {
        configManager.setValue("max.connections", String.valueOf(200));
        assertThat(configManager.getRequiredValue("max.connections")).isEqualTo("200");
    }
}
