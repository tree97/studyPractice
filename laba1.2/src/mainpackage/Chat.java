package mainpackage;

import java.io.*;
/**
 *  I hope, that adding messages will be in chronological order, if no, i will add sorting
 *  1 - add message
 *  2 - remove message
 *  3 - show history
 *  4 - search by author
 *  5 - show history in time range
 *  6 - find message by lexem
 *  7 - search by regular expression
 */
public class Chat {

    private ChatData data;
    private Storage storage;
    private Searcher searcher;

    public Chat() {
        data = new ChatData();
        storage = new Storage(data);
        searcher = new Searcher(data);
    }

    void downloadFromFile() throws IOException {
        storage.downloadFromFile("output.json");
    }

    void endChat() throws IOException {
        finalLogging();
        storage.saveLog("log2.txt");
        storage.saveHistory("output.json");
    }

    void showHistory() {     /// temp, will be edited later with small changes after adding another requests
        System.out.println("history:");
        for( int j = 0; j < data.history.size(); j++ ) {
            System.out.println(data.history.get(j).toString() + "\n");
        }
    }

    void finalLogging() {
        App.log.append("End of program work:" + '\n');
        App.log.append("added messages count = " + ((Integer) App.addedMessagesCount).toString() + '\n');
        App.log.append("removed messages count = " + ((Integer) App.removedMessagesCount).toString() + '\n');
    }

    void request(String[] args) {
        try {
            int i = 0;
            while( i < args.length ) {
                switch( args[i] ) {
                    case "1": {
                        App.log.append("Request: add message" + '\n');
                        App.log.append("text: " + args[i + 1] + '\n');
                        App.log.append("author: " + args[i + 2] + '\n');
                        data.addMessage(args[i + 1], args[i + 2]);
                        i += 3;
                        break;
                    }
                    case "2": {
                        App.log.append("Request: delete message" + '\n');
                        App.log.append("ID: " + args[i + 1] + '\n');
                        int id=Integer.parseInt(args[i + 1]);
                        data.removeMessage(id);
                        i += 2;
                        break;
                    }
                    case "3": {
                        App.log.append("Request: show messages" + '\n');
                        showHistory();
                        i++;
                        break;
                    }
                    case "4": {
                        App.log.append("Request: show messages with author" + '\n');
                        App.log.append("author: " + args[i + 1] + '\n');
                        searcher.author(args[i + 1]);
                        i += 2;
                        break;
                    }
                    case "5": {
                        App.log.append("Request: show messages with time range" + '\n');
                        App.log.append("time range: " + args[i + 1] + "-" + args[i + 2] + '\n');
                        long l, r;
                        l = Long.parseLong(args[i + 1]);
                        r = Long.parseLong(args[i + 2]);
                        searcher.timeRangeHistory(l, r);
                        i += 3;
                        break;
                    }
                    case "6": {
                        App.log.append("Request: show messages with key word in text" + '\n');
                        App.log.append("key word: " + args[i + 1] + '\n');
                        searcher.keyWord(args[i + 1]);
                        i += 2;
                        break;
                    }
                    case "7": {
                        App.log.append("Request: show messages with regular expression" + '\n');
                        App.log.append("regular expression: \" " + args[i + 1] + " \"" + '\n');
                        searcher.regularExpression(args[i + 1]);
                        i += 2;
                        break;
                    }
                }
            }
        } catch ( IllegalArgumentException e ) {
            System.out.println(e.getMessage() );
            App.log.append("Error: " + e.getMessage() + '\n');
        }
    }
}
