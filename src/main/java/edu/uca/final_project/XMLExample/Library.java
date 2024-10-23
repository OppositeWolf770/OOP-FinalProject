package edu.uca.final_project.XMLExample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import java.util.Objects;

public class Library {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "book")
    private List<Book> books;


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    // Add a book to the library
    public void addBook(String category, String title, String author, int year, double price) {
        int id = books.getLast().getId() + 1; // Set new book id to one more than last book id

        // Add the book to the end of the books List
        books.add(new Book(id, category, title, author, year, price));
    }

    // Remove a book by its title
    public void removeBook(String title) {
        for (Book book : books) {
            if (Objects.equals(book.getTitle(), title)) {
                books.remove(book);
                return;
            }
        }
    }

    // Remove a book by its id
    public void removeBook(int id) {
        for (Book book : books) {
            if (Objects.equals(book.getId(), id)) {
                books.remove(book);
                return;
            }
        }
    }


    // Display each book in the library
    public void displayBooks() {
        for (Book book : getBooks()) {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Category: " + book.getCategory());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Year: " + book.getYear());
            System.out.println("Price: " + book.getPrice());
            System.out.println();
        }
    }
}
