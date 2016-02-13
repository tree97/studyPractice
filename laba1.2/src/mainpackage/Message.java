package mainpackage;
/* class message
    has ID,time of publishing,text message,author
 */
public class Message {
    private int id;
    private int timestamp;
    private String text;
    private String author;
    public Message(Message x){
        id=x.id;
        text=x.text;
        author=x.author;
        timestamp=x.timestamp;
    }
    public Message(int id2,String text2,String author2,int time2){
        id=id2;
        text=text2;
        author=author2;
        timestamp=time2;
    }
    public int getId(){
        return id;
    }
    public int getTimestamp(){
        return timestamp;
    }
    public String getText(){
        return text;
    }
    public String getAuthor(){
        return author;
    }
    public String toString(){
        StringBuffer res=new StringBuffer();
        res.append("ID number: ");
        res.append(((Integer)id).toString());
        res.append('\n');
        res.append("Text: ");
        res.append(text);
        res.append('\n');
        res.append("author: ");
        res.append(author);
        res.append('\n');
        res.append("date and time: ");
        res.append(timestamp);
        return res.toString();
    }
}
