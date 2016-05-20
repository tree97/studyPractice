package mainpackage;

import java.util.ArrayList;

/**
 * all working data is here
 */

public class ChatData {

    private int lastId = 0;
    private int addedMessagesCount = 0;
    private int removedMessagesCount = 0;
    ArrayList < Message > history = new ArrayList < Message > ();

    void addMessage(String text, String author) {
        App.log.append("Request: add message" + '\n');
        App.log.append("text: " + text + '\n');
        App.log.append("author: " + author + '\n');
        
        int bigTextLength = 100;
        addedMessagesCount++;
        long timestamp = System.currentTimeMillis();
        Message temp = new Message(lastId, text, author, timestamp);
        history.add(temp);
        lastId++;
        if ( text.length() > bigTextLength ) {
            App.log.append("Warning: Text message is long ( longer than " + ((Integer) bigTextLength).toString() + " symbols )" + '\n');
        }
    }

    void removeMessage(int id) {   /// temp, will be edited later with small changes with ID
        App.log.append("Request: delete message" + '\n');
        App.log.append("ID: " + ((Integer)id).toString() + '\n');
        
        int j;
        for ( j = 0; j < history.size(); j++ ) {
            if ( history.get(j).getId() == id ) {
                break;
            }
        }
        if ( j < history.size() ) {
            history.remove(j);
            removedMessagesCount++;
        } else {
            App.log.append("Warning: No message with such ID for delete" + '\n');
        }
    }

    int getAddedMessagesCount() {
        return addedMessagesCount;
    }

    int getRemovedMessagesCount() {
        return removedMessagesCount;
    }
}
