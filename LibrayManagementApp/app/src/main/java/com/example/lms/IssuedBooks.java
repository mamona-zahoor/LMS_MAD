package com.example.lms;

import java.util.Date;

public class IssuedBooks {
    private String BookId;
    private String Id;
    private String IssueTo;
    private String IssuedDate;
    private String ReturnDate;
    private String ImageId;
    public IssuedBooks() {
    }

    public IssuedBooks(String ISBN, String id, String issueTo, String issuedDate, String returnDate/*, String ImageId*/) {
        this.BookId = ISBN;
        Id = id;
        IssueTo = issueTo;
        IssuedDate = issuedDate;
        //  this.ImageId = ImageId;
        ReturnDate = returnDate;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIssueTo() {
        return IssueTo;
    }

    public void setIssueTo(String issueTo) {
        IssueTo = issueTo;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        IssuedDate = issuedDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
}
