package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.*;

public class LibraryDemo {
    public static void main(String[] args) {
        System.out.println("Демонстрация работы библиотеки\n");

        LibraryManager library = new LibraryManager();

        Book book1 = new Book("111", "Война и мир", 1869, Book.Genre.FICTION);
        book1.setAuthors(new HashSet<>(List.of("Лев Толстой")));
        Book book2 = new Book("222", "Преступление и наказание", 1866, Book.Genre.FICTION);
        book2.setAuthors(new HashSet<>(List.of("Фёдор Достоевский")));
        Book book3 = new Book("333", "Гарри Поттер и философский камень", 1997, Book.Genre.FANTASY);
        book3.setAuthors(new HashSet<>(List.of("Дж. К. Роулинг")));
        Book book4 = new Book("444", "Книга трёх авторов", 2022, Book.Genre.SCIENCE);
        book4.setAuthors(new HashSet<>(List.of("Иванов", "Петров", "Сидоров")));

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        Reader reader1 = new Reader("R1", "Иван Иванов", "ivan@example.com", Reader.ReaderCategory.STUDENT);
        Reader reader2 = new Reader("R2", "Мария Петрова", "maria@example.com", Reader.ReaderCategory.STUDENT);
        library.addReader(reader1);
        library.addReader(reader2);

        library.borrowBook("111", "R1", 7);
        library.borrowBook("222", "R1", 10);
        library.borrowBook("333", "R2", 5);

        library.returnBook("111", "R1");

        System.out.println("🔍 Поиск по части названия 'поттер':");
        library.searchBooksByTitle("поттер")
                .forEach(b -> System.out.println("  • " + b.getTitle()));

        System.out.println("\nКниги жанра CLASSIC:");
        library.getBooksByGenre(Book.Genre.FICTION)
                .forEach(b -> System.out.println("  • " + b.getTitle()));

        System.out.println("\nКниги автора 'Дж. К. Роулинг':");
        library.getBooksByAuthor("Дж. К. Роулинг")
                .forEach(b -> System.out.println("  • " + b.getTitle()));

        System.out.println("\nСортировка по названию:");
        library.sortBooksByTitle(library.getAllBooks())
                .forEach(b -> System.out.println("  • " + b.getTitle()));

        System.out.println("\nСортировка по году публикации:");
        library.sortBooksByPublicationYear(library.getAllBooks())
                .forEach(b -> System.out.println("  • " + b.getTitle() + " (" + b.getPublicationYear() + ")"));

        System.out.println("\nИтератор по книгам жанра FANTASY и 1997 года:");
        Iterator<Book> itGenre = library.getBooksByGenreAndYearIterator(Book.Genre.FANTASY, 1997);
        while (itGenre.hasNext()) {
            System.out.println("  • " + itGenre.next().getTitle());
        }

        System.out.println("\nИтератор по книгам с 2+ авторами:");
        Iterator<Book> itMulti = library.getBooksWithMultipleAuthorsIterator(2);
        while (itMulti.hasNext()) {
            System.out.println("  • " + itMulti.next().getTitle());
        }

        System.out.println("\nИтератор по просроченным выдачам:");
        Iterator<Borrowing> itOverdue = library.getOverdueBorrowingsIterator();
        if (!itOverdue.hasNext()) {
            System.out.println("  • Нет просроченных выдач.");
        } else {
            while (itOverdue.hasNext()) {
                Borrowing b = itOverdue.next();
                System.out.println("  • " + b.getIsbn() + " у читателя " + b.getReaderId());
            }
        }

        System.out.println("\nСтатистика по жанрам:");
        Map<Book.Genre, Integer> genreStats = library.getGenreStatistics();
        genreStats.forEach((genre, count) -> System.out.println("  • " + genre + ": " + count + " книг"));

        System.out.println("\nСамые популярные книги:");
        library.getMostPopularBooks(3)
                .forEach((book, count) ->
                        System.out.println("  • " + book.getTitle() + " — " + count + " выдач"));

        System.out.println("\nСамые активные читатели:");
        library.getMostActiveReaders(3)
                .forEach((reader, count) ->
                        System.out.println("  • " + reader.getName() + " — " + count + " выдач"));

        System.out.println("\nЧитатели с просроченными книгами:");
        library.getReadersWithOverdueBooks()
                .forEach(r -> System.out.println("  • " + r.getName()));
    }
}
