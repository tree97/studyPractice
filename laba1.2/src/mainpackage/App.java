package mainpackage;


import java.io.IOException;

public class App {

    static StringBuilder log = new StringBuilder();
    static int addedMessagesCount = 0;
    static int removedMessagesCount = 0;

    public static void main(String[] args) {
        try {
            Chat my = new Chat();
            // my.downloadFromFile();
            my.request(args);
            my.endChat();
        } catch ( IOException e ) {
            System.out.println(e.getMessage() );
            App.log.append("Error: " + e.getMessage() + '\n');
        }
    }
}
