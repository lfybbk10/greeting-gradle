package ru.mentee.power.collections.library;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Reader implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private ReaderCategory category;

    public enum ReaderCategory {
        STUDENT, TEACHER, REGULAR, VIP
    }

    public Reader(String id, String name, String email, ReaderCategory category) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ReaderCategory getCategory() {
        return category;
    }

    public void setCategory(ReaderCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Reader reader) {
            return Objects.equals(id, reader.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name + ", Email: " + email + ", Category: " + category;
    }
}