package com.example.lms;

public class ImageuploadInfo {


    public String imageName;
    public String imageURL;
    public String bookId;
    public ImageuploadInfo(){}

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public ImageuploadInfo(String name, String url, String bookId) {
        //  this.Id = Id;
        this.imageName = name;
        this.imageURL = url;
        this.bookId = bookId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImageURL() {
        return imageURL;
    }
}
