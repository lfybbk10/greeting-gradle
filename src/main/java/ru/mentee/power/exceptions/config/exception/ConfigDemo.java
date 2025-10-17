package ru.mentee.power.exceptions.config.exception;


import java.util.HashMap;
import java.util.Map;

/**
 * Демонстрация работы ConfigManager.
 */
public class ConfigDemo {
    public static void main(String[] args) {
        // TODO: Создать тестовую карту конфигурации
        Map<String, String> config = new HashMap<>();

        // TODO: Добавить тестовые данные в карту
        config.put("database.url", "jdbc:mysql://localhost:3306/mydb");
        config.put("database.user", "root");
        config.put("database.password", "secret");
        config.put("server.port", "8080");
        config.put("debug.mode", "true");
        config.put("max.connections", "100");
        config.put("timeout", "30");

        ConfigManager manager = new ConfigManager(config);

        try {
            // TODO: Получить строковое значение
            String databaseUrl = manager.getRequiredValue("database.url");
            System.out.println(databaseUrl);
            // TODO: Получить числовое значение
            int maxConnections = manager.getRequiredIntValue("max.connections");
            System.out.println(maxConnections);
            // TODO: Получить булево значение
            boolean debug = manager.getRequiredBooleanValue("debug.mode");
            System.out.println(debug);

            // TODO: Попытаться получить несуществующий ключ
            int timeout = manager.getRequiredIntValue("Timeout");

            // TODO: Попытаться получить некорректное числовое значение
            int password = manager.getRequiredIntValue("database.password");

        } catch (ConfigException e) {
            // TODO: Обработать исключение
            System.out.println(e.getMessage());
        }
    }
}
