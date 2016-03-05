package mainpackage;

import java.text.*;

/** class message
 *  has ID,timestamp of publishing,text message,author
 */

public class Message {

    private int id;
    private long timestamp;
    private String text;
    private String author;

    public Message(Message x) {
        id = x.id;
        text = x.text;
        author = x.author;
        timestamp = x.timestamp;
    }

    public Message(int id2, String text2, String author2, long time2) {
        id = id2;
        text = text2;
        author = author2;
        timestamp = time2;
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String toString() {
        SimpleDateFormat my = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        StringBuilder res = new StringBuilder();
        res.append("ID number: ");
        res.append(((Integer) id).toString() + '\n');
        res.append("Text: ");
        res.append(text + '\n');
        res.append("author: ");
        res.append(author + '\n');
        res.append("date and time: ");
        res.append(my.format(timestamp) );

        return res.toString();
    }
}
