package edu.uca.final_project.XMLExample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Book {
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    @JacksonXmlProperty(isAttribute = true)
    private String category;

    private String title;
    private String author;
    private int year;
    private double price;

    public Book() {
        super();
    }


    public Book(int id, String category, String title, String author, int year, double price) {
        this();

        setCategory(category);
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
    }

    // ID Gesetter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // Category Gesetter
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    // Year Gesetter
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    // Price Gesetter
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    // Title Gesetter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    // Author Gesetter
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
