package mainpackage;

/**
 * all working data is here
 */
public class ChatData {

    int messageCount = 0;
    int containerSize = 10;
    int lastId = 0;
    Message[] history = new Message[containerSize];

    private void sizeController() {
        Message[] buf;
        if ( messageCount == containerSize ) {
            buf = new Message[containerSize];
            for ( int j = 0; j < containerSize; j++ ) {
                buf[j] = new Message(history[j]);
            }
            if ( containerSize < 10 ) {
                history = new Message[containerSize + 10];
            } else {
                history = new Message[containerSize * 2];
            }
            for ( int j = 0; j < containerSize; j++ ) {
                history[j] = new Message(buf[j]);
            }
            if ( containerSize < 10 ) {
                containerSize += 10;
            } else {
                containerSize *= 2;
            }
        }
    }

    void addMessage(String text, String author) {
        int bigTextLength = 100;
        sizeController();
        App.addedMessagesCount++;
        long timestamp = System.currentTimeMillis();
        history[messageCount++] = new Message(lastId, text, author, timestamp);
        lastId++;
        if ( text.length() > bigTextLength ) {
            App.log.append("Warning: Text message is long ( longer than " + ((Integer) bigTextLength).toString() + " symbols )" + '\n');
        }
    }

    void removeMessage(int id) {   /// temp, will be edited later with small changes with ID
        int j;
        for ( j = 0; j < messageCount; j++ ) {
            if ( history[j].getId() == id ) {
                break;
            }
        }
        if ( j < messageCount ) {
            while ( j < messageCount ) {
                history[j] = history[j + 1];
                j++;
            }
            messageCount--;
            App.removedMessagesCount++;
        } else {
            App.log.append("Warning: No message with such ID for delete" + '\n');
        }
    }
}
