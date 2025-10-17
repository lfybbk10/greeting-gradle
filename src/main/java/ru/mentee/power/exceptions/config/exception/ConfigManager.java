package ru.mentee.power.exceptions.config.exception;

import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.Map;
import java.util.HashMap;

/**
 * Класс для работы с конфигурационными параметрами.
 */
public class ConfigManager {
    private final Map<String, String> config;

    /**
     * Создает новый менеджер конфигурации с указанной картой параметров.
     *
     * @param config Карта конфигурационных параметров
     */
    public ConfigManager(Map<String, String> config) {
        this.config = config;
    }

    /**
     * Создает новый менеджер конфигурации с пустой картой параметров.
     */
    public ConfigManager() {
        this.config = new HashMap<>();
    }

    /**
     * Получает значение по ключу, выбрасывая исключение, если ключ не найден.
     *
     * @param key Ключ для поиска.
     * @return Значение параметра.
     * @throws MissingConfigKeyException Если ключ отсутствует в конфигурации.
     */
    public String getRequiredValue(String key) throws MissingConfigKeyException {
        if(config.containsKey(key)) {
            return config.get(key);
        }
        throw new MissingConfigKeyException(key);
    }

    /**
     * Получает числовое значение по ключу, выбрасывая исключение, если значение не является числом.
     *
     * @param key Ключ для поиска.
     * @return Числовое значение параметра.
     * @throws MissingConfigKeyException Если ключ отсутствует в конфигурации.
     * @throws InvalidConfigValueException Если значение не является числом.
     */
    public int getRequiredIntValue(String key)
            throws MissingConfigKeyException, InvalidConfigValueException {
        try{
            if(config.containsKey(key)) {
                String value = config.get(key);
                return Integer.parseInt(value);
            }
            else{
                throw new MissingConfigKeyException(key);
            }
        } catch (NumberFormatException e){
            throw new InvalidConfigValueException(key);
        }
    }

    /**
     * Получает булево значение по ключу, выбрасывая исключение, если значение не является булевым.
     *
     * @param key Ключ для поиска.
     * @return Булево значение параметра.
     * @throws MissingConfigKeyException Если ключ отсутствует в конфигурации.
     * @throws InvalidConfigValueException Если значение не является булевым.
     */
    public boolean getRequiredBooleanValue(String key)
            throws MissingConfigKeyException, InvalidConfigValueException {
        if(config.containsKey(key)) {
            String value = config.get(key);
            if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(value);
            }
            else{
                throw new InvalidConfigValueException(key);
            }
        }
        else{
            throw new MissingConfigKeyException(key);
        }
    }

    /**
     * Добавляет параметр в конфигурацию.
     *
     * @param key Ключ параметра
     * @param value Значение параметра
     */
    public void setValue(String key, String value) {
        config.put(key, value);
    }
}
