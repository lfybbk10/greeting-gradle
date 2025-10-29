// --- Файл: LibraryPersistenceDemo.java ---
package ru.mentee.power.collections.library;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LibraryPersistenceDemo {
    private static final String STATE_FILE = "library_state.ser";
    private static final String BOOKS_CSV_FILE = "books_export.csv";
    private static final String BOOKS_CSV_IMPORT_FILE = "books_import.csv";
    private static final String CSV_DELIMITER = ";";

    public static void main(String[] args) {
        LibraryManager libraryManager;

        // 1. Загрузка состояния
        System.out.println("Попытка загрузки состояния из " + STATE_FILE + "...");
        libraryManager = LibraryManager.loadLibraryState(STATE_FILE);

        // 2. Создание нового менеджера, если загрузка не удалась
        if (libraryManager == null) {
            libraryManager = new LibraryManager();
            System.out.println("Создана новая пустая библиотека. Добавляем тестовые данные...");

            Book book1 = new Book("111", "Война и мир", 1869, Book.Genre.FICTION);
            book1.setAuthors(new HashSet<>(List.of("Лев Толстой")));
            Book book2 = new Book("222", "Преступление и наказание", 1866, Book.Genre.FICTION);
            book2.setAuthors(new HashSet<>(List.of("Фёдор Достоевский")));
            Book book3 = new Book("333", "Гарри Поттер и философский камень", 1997, Book.Genre.FANTASY);
            book3.setAuthors(new HashSet<>(List.of("Дж. К. Роулинг")));
            Book book4 = new Book("444", "Книга трёх авторов", 2022, Book.Genre.SCIENCE);
            book4.setAuthors(new HashSet<>(List.of("Иванов", "Петров", "Сидоров")));

            libraryManager.addBook(book1);
            libraryManager.addBook(book2);
            libraryManager.addBook(book3);
            libraryManager.addBook(book4);

            Reader reader1 = new Reader("R1", "Иван Иванов", "ivan@example.com", Reader.ReaderCategory.STUDENT);
            Reader reader2 = new Reader("R2", "Мария Петрова", "maria@example.com", Reader.ReaderCategory.STUDENT);
            libraryManager.addReader(reader1);
            libraryManager.addReader(reader2);
        }

        System.out.println("\n--- Текущее состояние библиотеки --- ");
        displayLibraryStats(libraryManager); // Используем старый метод для статистики

        // 4. Экспорт книг в CSV
        System.out.println("\n--- Экспорт книг в CSV --- ");
        libraryManager.exportBooksToCsv(BOOKS_CSV_FILE, CSV_DELIMITER);
        System.out.println("Экспорт завершен в " + BOOKS_CSV_FILE);

        // 5. Импорт книг из CSV
        System.out.println("\n--- Импорт книг из CSV --- ");
        int importedCount = libraryManager.importBooksFromCsv(BOOKS_CSV_IMPORT_FILE, CSV_DELIMITER, true); // Placeholder
        System.out.println("Импортировано книг: " + importedCount);

        System.out.println("\n--- Состояние библиотеки после импорта --- ");
        displayLibraryStats(libraryManager);

        // 6. Сохранение состояния перед выходом
        System.out.println("\n--- Сохранение финального состояния --- ");
        libraryManager.saveLibraryState(STATE_FILE);

        System.out.println("\nРабота программы завершена.");
    }

    // Метод для вывода статистики (можно скопировать из LibraryPersistenceDemo предыдущего урока)
    private static void displayLibraryStats(LibraryManager manager) {
        System.out.println("Всего книг: " + manager.getAllBooks().size());
        System.out.println("Всего читателей: " + manager.getAllReaders().size());
        System.out.println("Всего записей о выдаче: " + manager.getAllBorrowings().size());
        System.out.println("Доступных книг: " + manager.getAvailableBooks().size());
        System.out.println("Просроченных выдач: " + manager.getOverdueBorrowings().size());
    }
}