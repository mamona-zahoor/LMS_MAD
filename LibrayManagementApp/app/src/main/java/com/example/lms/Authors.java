package com.example.lms;

public class Authors {
    private String AuthorId;
    private String BookId;
    private String AuthorName;


    public Authors(String authorId, String bookId, String authorName) {
        AuthorId = authorId;
        BookId = bookId;
        AuthorName = authorName;
    }
    public Authors()
    {

    }

    public String getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(String authorId) {
        AuthorId = authorId;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }
}
