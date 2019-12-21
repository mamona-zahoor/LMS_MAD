package com.example.lms;

import java.util.Date;

public class IssuedBooks {
    private String ISBN;
    private String Id;
    private String IssueTo;
    private String IssuedDate;
    private String ReturnDate;

    public IssuedBooks(String ISBN, String id, String issueTo, String issuedDate, String returnDate) {
        this.ISBN = ISBN;
        Id = id;
        IssueTo = issueTo;
        IssuedDate = issuedDate;
        ReturnDate = returnDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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
