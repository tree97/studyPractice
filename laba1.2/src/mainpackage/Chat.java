package mainpackage;

import java.io.*;
/**
 *  I hope, that adding messages will be in chronological order, if no, i will add sorting
 *
 *  1 - add message
 *  2 - remove message
 *  3 - show history
 *  4 - search by author
 *  5 - show history in time range
 *  6 - find message by lexem
 *  7 - search by regular expression
 */
public class Chat {

    static StringBuilder log = new StringBuilder();
    static int addedMessagesCount = 0;
    static int removedMessagesCount = 0;

    private static void showHistory() {     /// temp, will be edited later with small changes after adding another requests
        System.out.println("history:");
        for( int j = 0; j < ChatData.messageCount; j++ ) {
            System.out.println(ChatData.history[j].toString() + "\n");
        }
    }

    private static void finalLogging() {
        log.append("End of program work:" + '\n');
        log.append("added messages count = " + ((Integer) addedMessagesCount).toString() + '\n');
        log.append("removed messages count = " + ((Integer) removedMessagesCount).toString() + '\n');
    }

    public static void main(String[] args) {
        try {
            ///    temp, will be removed later with changing work principle
            //   Storage.downloadFromFile("output.json");
            int i = 0;
            while( i < args.length ) {
                switch( args[i] ) {
                    case "1": {
                        log.append("Request: add message" + '\n');
                        log.append("text: " + args[i + 1] + '\n');
                        log.append("author: " + args[i + 2] + '\n');
                        String text = args[i + 1];
                        String author = args[i + 2];
                        ChatData.addMessage(text, author);
                        i += 3;
                        break;
                    }
                    case "2": {
                        log.append("Request: delete message" + '\n');
                        log.append("ID: " + args[i + 1] + '\n');
                        int id=Integer.parseInt(args[i + 1]);
                        ChatData.removeMessage(id);
                        i += 2;
                        break;
                    }
                    case "3": {
                        log.append("Request: show messages" + '\n');
                        showHistory();
                        i++;
                        break;
                    }
                    case "4": {
                        log.append("Request: show messages with author" + '\n');
                        log.append("author: " + args[i + 1] + '\n');
                        Searcher.Author(args[i + 1]);
                        i += 2;
                        break;
                    }
                    case "5": {
                        log.append("Request: show messages with time range" + '\n');
                        log.append("time range: " + args[i + 1] + "-" + args[i + 2] + '\n');
                        long l, r;
                        l = Long.parseLong(args[i + 1]);
                        r = Long.parseLong(args[i + 2]);
                        Searcher.TimeRangeHistory(l, r);
                        i += 3;
                        break;
                    }
                    case "6": {
                        log.append("Request: show messages with key word in text" + '\n');
                        log.append("key word: " + args[i + 1] + '\n');
                        Searcher.MessageByLexem(args[i + 1]);
                        i += 2;
                        break;
                    }
                    case "7": {
                        log.append("Request: show messages with regular expression" + '\n');
                        log.append("regular expression: \" " + args[i + 1] + " \"" + '\n');
                        Searcher.MessageWithRegularExpression(args[i + 1]);
                        i += 2;
                        break;
                    }
                }
            }
            finalLogging();
            Storage.saveLog();
            //   Storage.saveHistory();
            //    showHistory();
        } catch ( IllegalArgumentException | IOException e ) {
            System.out.println(e.getMessage() );
            log.append("Error: " + e.getMessage() + '\n');
        }
    }
}
