package ru.mentee.power.exceptions.config.exception;

/**
 * Исключение, выбрасываемое при отсутствии обязательного ключа конфигурации.
 */
public class MissingConfigKeyException extends ConfigException {
    private final String missingConfigKey;

    public MissingConfigKeyException(String key) {
        super("Key not found: " + key);
        this.missingConfigKey = key;
    }

    public MissingConfigKeyException(String key, Throwable cause) {
        super("Key not found: " + key, cause);
        this.missingConfigKey = key;
    }

    public String getMissingConfigKey() {
        return missingConfigKey;
    }
}
