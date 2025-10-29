// --- Файл: LibraryManagerCsvTest.java ---
package ru.mentee.power.collections.library;

// Импорты JUnit, AssertJ, моделей, IO/NIO
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
// ... другие импорты ...
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты CSV экспорта/импорта LibraryManager")
class LibraryManagerCsvTest {

    @TempDir
    Path tempDir;

    private LibraryManager libraryManager;
    private Path csvFilePath;
    private final String delimiter = ";";

    @BeforeEach
    void setUp() {
        libraryManager = new LibraryManager();
        csvFilePath = tempDir.resolve("books_test.csv");
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
    }

    @Test
    @DisplayName("exportBooksToCsv должен создавать корректный CSV файл")
    void exportShouldCreateCorrectCsv() throws IOException {
        // When
        libraryManager.exportBooksToCsv(csvFilePath.toString(), delimiter);

        // Then
        assertThat(csvFilePath).exists();
        List<String> lines = Files.readAllLines(csvFilePath, StandardCharsets.UTF_8);
        assertThat(lines.getFirst()).isEqualTo(LibraryManager.BOOK_CSV_HEADER);
        assertThat(lines.size()).isEqualTo(5);
        assertThat(lines.get(1)).isEqualTo("111;Война и мир;Лев Толстой;FICTION;1869;0;true");
        assertThat(lines.get(2)).isEqualTo("222;Преступление и наказание;Фёдор Достоевский;FICTION;1866;0;true");
    }

    @Test
    @DisplayName("importBooksFromCsv должен добавлять книги (append=true)")
    void importShouldAddBooksWhenAppendIsTrue() throws IOException {
        // Given
        int initialBookCount = libraryManager.getAllBooks().size();
        String csvContent = LibraryManager.BOOK_CSV_HEADER + "\n" +
                "ISBN-TEST-1;Новая Книга 1;Автор Тест;FICTION;2024;100;true\n" +
                "ISBN-TEST-2;Новая Книга 2;Автор Тест;SCIENCE;2023;200;false";
        Files.writeString(csvFilePath, csvContent, StandardCharsets.UTF_8);

        // When
        int importedCount = libraryManager.importBooksFromCsv(csvFilePath.toString(), delimiter, true); // Placeholder

        // Then
        assertThat(importedCount).isEqualTo(2);
        assertThat(libraryManager.getAllBooks().size()).isEqualTo(initialBookCount + importedCount);
        assertThat(libraryManager.getBookByIsbn("ISBN-TEST-1")).isNotNull();
        assertThat(libraryManager.getBookByIsbn("ISBN-TEST-2")).isNotNull();
    }

    @Test
    @DisplayName("importBooksFromCsv должен заменять книги (append=false)")
    void importShouldReplaceBooksWhenAppendIsFalse() throws IOException {
        // Given
        String csvContent = LibraryManager.BOOK_CSV_HEADER + "\n" +
                "ISBN-REPLACE-1;Заменяющая Книга;Автор Иной;HISTORY;2022;300;true";
        Files.writeString(csvFilePath, csvContent, StandardCharsets.UTF_8);
        int importBookCount = 1;

        // When
        int importedCount = libraryManager.importBooksFromCsv(csvFilePath.toString(), delimiter, false); // Placeholder

        // Then
        assertThat(importedCount).isEqualTo(importBookCount);
        assertThat(libraryManager.getAllBooks().size()).isEqualTo(importBookCount);
        assertThat(libraryManager.getBookByIsbn("ISBN-REPLACE-1")).isNotNull();
        assertThat(libraryManager.getBookByIsbn("111")).isNull();
        assertThat(libraryManager.getBookByIsbn("222")).isNull();
    }

    @Test
    @DisplayName("importBooksFromCsv должен пропускать некорректные строки")
    void importShouldSkipMalformedLines() throws IOException {
        // Given
        int initialBookCount = libraryManager.getAllBooks().size();
        String csvContent = LibraryManager.BOOK_CSV_HEADER + "\n" +
                "ISBN-GOOD-1;Хорошая Книга;Автор;FANTASY;2020;150;true\n" +
                "bad-line-format\n" + // Неверное число полей
                "ISBN-BAD-YEAR;Плохой Год;Автор;SCIENCE;не_число;200;true";
        Files.writeString(csvFilePath, csvContent, StandardCharsets.UTF_8);

        // When
        int importedCount = libraryManager.importBooksFromCsv(csvFilePath.toString(), delimiter, true);; // Placeholder

        // Then
        assertThat(importedCount).isEqualTo(1);
        assertThat(libraryManager.getAllBooks().size()).isEqualTo(initialBookCount + importedCount);
        assertThat(libraryManager.getBookByIsbn("ISBN-GOOD-1")).isNotNull();
    }
}