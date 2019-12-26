package com.example.lms;

public class Book_ModelClass {
    public String bookName;
    public String isbn;
    public String price;
    public String edition;
    public String id;
    public String availability;
    public String imageId;
    public String qrImageId;
    public Book_ModelClass(){

    }
    public Book_ModelClass(String bookName, String ISBN, String price, String edition, String id, String availability,String img) {
        this.bookName = bookName;
        this.isbn = ISBN;
        this.price = price;
        this.edition = edition;
        this.id = id;
        this.imageId = img;
        this.availability = availability;
    }
}
