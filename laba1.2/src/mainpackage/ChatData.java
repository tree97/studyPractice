package mainpackage;

import java.util.ArrayList;

/**
 * all working data is here
 */
public class ChatData {

    int lastId = 0;
    ArrayList < Message > history = new ArrayList < Message > ();

    void addMessage(String text, String author) {
        int bigTextLength = 100;
        App.addedMessagesCount++;
        long timestamp = System.currentTimeMillis();
        Message temp = new Message(lastId, text, author, timestamp);
        history.add(temp);
        lastId++;
        if ( text.length() > bigTextLength ) {
            App.log.append("Warning: Text message is long ( longer than " + ((Integer) bigTextLength).toString() + " symbols )" + '\n');
        }
    }

    void removeMessage(int id) {   /// temp, will be edited later with small changes with ID
        int j;
        for ( j = 0; j < history.size(); j++ ) {
            if ( history.get(j).getId() == id ) {
                break;
            }
        }
        if ( j < history.size() ) {
            history.remove(j);
            App.removedMessagesCount++;
        } else {
            App.log.append("Warning: No message with such ID for delete" + '\n');
        }
    }
}
