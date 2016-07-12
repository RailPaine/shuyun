package recyclerview;

/**
 * Created by rail on 2016/6/30.
 */
public class Item {

    public String bookname;
    public String author;
    public Item(String bookname,String author)
    {
        this.bookname=bookname;
        this.author=author;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
