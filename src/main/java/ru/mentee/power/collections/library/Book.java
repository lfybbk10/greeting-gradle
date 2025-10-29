package ru.mentee.power.collections.library;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String isbn;
    private String title;
    private Set<String> authors;
    private Genre genre;
    private int publicationYear;
    private int pageCount;
    private boolean available;

    public enum Genre {
        FICTION, NON_FICTION, SCIENCE, HISTORY, FANTASY, DETECTIVE, ROMANCE, BIOGRAPHY, CHILDREN
    }

    // Должен инициализировать множество авторов как пустой HashSet и устанавливать available в true.
    public Book(String isbn, String title, int publicationYear, Genre genre) {
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.genre = genre;

        authors = new HashSet<>();
        available = true;
    }



    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void addAuthor(String author) {
        authors.add(author);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Book) {
            return Objects.equals(isbn, ((Book) o).isbn);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Isbn: "+isbn+", Title: "+title+", Authors: "+authors+", Genre: "+genre+", Publication Year: "+publicationYear+", Page Count: "+pageCount ;
    }
}