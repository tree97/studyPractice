package mainpackage;

import java.util.StringTokenizer;

/**
 *  all searching requests
 */
public class Searcher {

    static void Author(String auth) {
        int count = 0;
        for( int j = 0; j < ChatData.messageCount; j++ ) {
            if ( auth.equals(ChatData.history[j].getAuthor() ) ) {
                if ( count == 0 ) {
                    System.out.println("founded " + auth + "'s messages:");
                }
                count++;
                System.out.println(ChatData.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println(auth + "'s messages not found");
        }
        Chat.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    static void MessageByLexem(String lexem) {
        int count = 0;
        for( int j = 0; j < ChatData.messageCount; j++ ) {
            StringTokenizer my = new StringTokenizer(ChatData.history[j].getText(), ",.?! \"()[]<>:;'");
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
                System.out.println(ChatData.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("'messages with key word \" " + lexem + " \" not found");
        }
        Chat.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    static void MessageWithRegularExpression(String expression) {
        int count = 0;
        for( int j = 0; j < ChatData.messageCount; j++ ) {
            String text = ChatData.history[j].getText();
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
                System.out.println(ChatData.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("'messages with expression \" " + expression + " \" not found");
        }
        Chat.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    static void TimeRangeHistory(long l, long r) {
        if( l > r ) {
            Chat.log.append("Warning: Incorrect time period" + '\n');
        }
        int count = 0;
        for( int j = 0; j < ChatData.messageCount; j++ ) {
            if ( ( ChatData.history[j].getTimestamp() >= l ) && ( ChatData.history[j].getTimestamp() <= r ) ) {
                if ( count == 0 ) {
                    System.out.println("founded in time range " + l + "-" + r + " messages:");
                }
                count++;
                System.out.println(ChatData.history[j].toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("messages in time range " + l + "-" + r + " not found");
        }
        Chat.log.append(((Integer) count).toString() + " messages found" + '\n');
    }
}
