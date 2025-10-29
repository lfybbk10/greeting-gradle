// --- Файл: LibraryManagerPersistenceTest.java ---
package ru.mentee.power.collections.library;

// Импорты JUnit, AssertJ, моделей
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
// ... другие импорты ...
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты сохранения/загрузки состояния LibraryManager")
class LibraryManagerPersistenceTest {

    @TempDir
    Path tempDir;

    private Path stateFilePath;
    private LibraryManager managerToSave;

    @BeforeEach
    void setUp() {
        stateFilePath = tempDir.resolve("library_test_state.ser");
        managerToSave = new LibraryManager();

        Book book1 = new Book("111", "Война и мир", 1869, Book.Genre.ROMANCE);
        book1.setAuthors(new HashSet<>(List.of("Лев Толстой")));
        Book book2 = new Book("222", "Преступление и наказание", 1866, Book.Genre.FICTION);
        book2.setAuthors(new HashSet<>(List.of("Фёдор Достоевский", "Лев Толстой")));

        Reader reader1 = new Reader("R1", "Иван Иванов", "ivan@mail.ru", Reader.ReaderCategory.STUDENT);

        managerToSave.addBook(book1);
        managerToSave.addBook(book2);
        managerToSave.addReader(reader1);
    }

    @Test
    @DisplayName("Должен сохранять и загружать непустое состояние")
    void shouldSaveAndLoadNonEmptyState() {
        // When
        managerToSave.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Then
        assertThat(loadedManager).isNotNull();
        assertThat(loadedManager.getAllBooks().size()).isEqualTo(managerToSave.getAllBooks().size());
        assertThat(loadedManager.getAllReaders().size()).isEqualTo(managerToSave.getAllReaders().size());
        assertThat(loadedManager.getAllBorrowings().size()).isEqualTo(managerToSave.getAllBorrowings().size());
        assertThat(loadedManager.getBooksByGenre(Book.Genre.ROMANCE)).hasSize(1);
    }

    @Test
    @DisplayName("Должен сохранять и загружать пустое состояние")
    void shouldSaveAndLoadEmptyState() {
        // Given
        LibraryManager emptyManager = new LibraryManager();

        // When
        emptyManager.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString()); // Placeholder

        // Then
        assertThat(loadedManager).isNotNull();
        assertThat(loadedManager.getAllBooks().size()).isEqualTo(0);
        assertThat(loadedManager.getAllReaders().size()).isEqualTo(0);
        assertThat(loadedManager.getAllBorrowings().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("loadLibraryState должен возвращать null для несуществующего файла")
    void loadShouldReturnNullForNonExistentFile() {
        // Given: файл не существует (не вызываем save)
        // When
        LibraryManager loadedManager = LibraryManager.loadLibraryState("Test"); // Placeholder

        // Then
        assertThat(loadedManager).isNull();
    }

}