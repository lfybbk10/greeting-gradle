package ru.mentee.power.exceptions.config.exception;

/**
 * Исключение, выбрасываемое при неверном формате значения в конфигурации.
 */
public class InvalidConfigValueException extends ConfigException {
    private final String invalidConfigValue;

    public InvalidConfigValueException(String value) {
        super("Invalid config value: " + value);
        invalidConfigValue = value;
    }

    public InvalidConfigValueException(String value, Throwable cause) {
        super("Invalid config value: " + value, cause);
        this.invalidConfigValue = value;
    }


    public String getInvalidConfigValue() {
        return invalidConfigValue;
    }
}