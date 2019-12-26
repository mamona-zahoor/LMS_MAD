package com.example.lms;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

public class AllBooks {
    private String BookName;
    private String ISBN;
    private String Price;
    private String Edition;
    private String Id;
    private String Availability;
    private String BookId;
    private String ImageId;


    public AllBooks()
    {

    }


    public AllBooks(String bookName, String ISBN, String price, String edition, String id, String availability,String img) {
        BookName = bookName;
        this.ISBN = ISBN;
        Price = price;
        Edition = edition;
        Id = id;
        ImageId = img;
        Availability = availability;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getBookImage() {
        return BookId;
    }

    public void setBookImage(String bookImage) {
        BookId = bookImage;
    }

    public void Validation(String field, String s, Context c)
    {
        if(field == "name")
        {
            if(s == "")
            {
                Toast.makeText(c, "Please Enter Book Name", Toast.LENGTH_SHORT).show();

            }

        }
        if(field == "number")
        {
            if(s == "")
            {
                Toast.makeText(c, "Please Enter Book Number", Toast.LENGTH_SHORT).show();

            }

        }
        if(field == "price")
        {
            if(s == "")
            {
                Toast.makeText(c, "Please Enter Book Price", Toast.LENGTH_SHORT).show();

            }

        }
        if(field == "Edition")
        {
            if(s == "")
            {
                Toast.makeText(c, "Please Enter Book Edition", Toast.LENGTH_SHORT).show();

            }

        }


    }
    public void NumberValidation( String s, Context c)
    {
        if(s == "")
        {
            Toast.makeText(c, "All fields are Required", Toast.LENGTH_SHORT).show();
        }

    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getEdition() {
        return Edition;
    }

    public void setEdition(String edition) {
        Edition = edition;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }
}
