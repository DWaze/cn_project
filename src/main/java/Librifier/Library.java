package Librifier;

import java.util.ArrayList;

public class Library
{
    private String hubURL;
    private String author;
    private int creationDate = 0;
    private int stuffLength = 0;
    private String stuffName;
    private int bookLength = 0;
    private ArrayList<byte[]> books;

    public Library() { }
    public Library(String _hubURL, String _author, int _creationDate, int _stuffLength, String _stuffName, int _bookLength, ArrayList<byte[]> _books)
    {
        hubURL = _hubURL;
        author = _author;
        creationDate = _creationDate;
        stuffLength = _stuffLength;
        stuffName = _stuffName;
        bookLength = _bookLength;
        books = _books;
    }

    //Getter
    public String getHubURL() { return hubURL; }
    public String getAuthor() { return author; }
    public int getCreationDate() { return creationDate; }
    public int getStuffLength() { return stuffLength; }
    public String getStuffName() { return stuffName; }
    public int getBookLength() { return bookLength; }
    public ArrayList<byte[]> getBooks() { return books; }

    //Setter
    public void setHubURL(String _hubURL) { hubURL = _hubURL; }
    public void setAuthor(String _author) { author = _author; }
    public void setCreationDate(int _creationDate) { creationDate = _creationDate; }
    public void setStuffLength(int _stuffLength) { stuffLength = _stuffLength; }
    public void setStuffName(String _stuffName) { stuffName = _stuffName; }
    public void setBookLength(int _bookLength) { bookLength = _bookLength; }
    public void setBooks(ArrayList<byte[]> _books) { books = _books; }

    //Print
    public void print()
    {
        if(!hubURL.isEmpty()) System.out.println("hubURL -------> " + hubURL);
        else System.out.println("hubURL is empty");
        if(!author.isEmpty()) System.out.println("author -------> " + author);
        else System.out.println("author is empty");
        if(creationDate != 0) System.out.println("creationDate -> " + creationDate);
        else System.out.println("creationDate is empty");
        if(stuffLength != 0) System.out.println("stuffLength --> " + stuffLength);
        else System.out.println("stuffLength is empty");
        if(!stuffName.isEmpty()) System.out.println("stuffName ----> " + stuffName);
        else System.out.println("stuffName is empty");
        if(bookLength != 0) System.out.println("bookLength ---> " + bookLength);
        else System.out.println("bookLength is empty");
        if(!books.isEmpty()) System.out.println("books --------> " + books.toString());
        else System.out.println("books is empty");
    }
}
