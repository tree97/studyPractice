package mainpackage;

import java.util.StringTokenizer;

/**
 *  all searching requests
 */
public class Searcher {

    private ChatData data;

    public Searcher(ChatData info) {
        data=info;
    }

    void author(String auth) {
        int count = 0;
        for( int j = 0; j < data.messageCount; j++ ) {
            if ( auth.equals(data.history[j].getAuthor() ) ) {
                if ( count == 0 ) {
                    System.out.println("founded " + auth + "'s messages:");
                }
                count++;
                System.out.println(data.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println(auth + "'s messages not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    void keyWord(String lexem) {
        int count = 0;
        for( int j = 0; j < data.messageCount; j++ ) {
            StringTokenizer my = new StringTokenizer(data.history[j].getText(), ",.?! \"()[]<>:;'");
            boolean ok = false;
            while( my.hasMoreTokens() ) {
                if( lexem.equals(my.nextToken() ) ) {
                    ok = true;
                    break;
                }
            }
            if( ok ) {
                if( count == 0 ) {
                    System.out.println("'messages with key word \" " + lexem + " \" found:");
                }
                count++;
                System.out.println(data.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("'messages with key word \" " + lexem + " \" not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    void regularExpression(String expression) {
        int count = 0;
        for( int j = 0; j < data.messageCount; j++ ) {
            String text = data.history[j].getText();
            boolean ok = false;
            for( int k = 0; k + expression.length() - 1 < text.length(); k++ ) {
                if( expression.equals(text.substring(k, k + expression.length() ) ) ) {
                    ok = true;
                    break;
                }
            }
            if( ok ) {
                if( count == 0 ) {
                    System.out.println("'messages with expression \" " + expression + " \" found:");
                }
                count++;
                System.out.println(data.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("'messages with expression \" " + expression + " \" not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    void timeRangeHistory(long l, long r) {
        if( l > r ) {
            App.log.append("Warning: Incorrect time period" + '\n');
        }
        int count = 0;
        for( int j = 0; j < data.messageCount; j++ ) {
            if ( ( data.history[j].getTimestamp() >= l ) && ( data.history[j].getTimestamp() <= r ) ) {
                if ( count == 0 ) {
                    System.out.println("founded in time range " + l + "-" + r + " messages:");
                }
                count++;
                System.out.println(data.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("messages in time range " + l + "-" + r + " not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }
}
