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
        for( int j = 0; j < data.history.size(); j++ ) {
            if ( auth.equals(data.history.get(j).getAuthor() ) ) {
                if ( count == 0 ) {
                    System.out.println("founded " + auth + "'s messages:");
                }
                count++;
                System.out.println(data.history.get(j).toString() );
            }
        }
        if( count == 0 ) {
            System.out.println(auth + "'s messages not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    void keyWord(String lexem) {
        int count = 0;
        for( int j = 0; j < data.history.size(); j++ ) {
            StringTokenizer my = new StringTokenizer(data.history.get(j).getText(), ",.?! \"()[]<>:;'");
            boolean ok = false;
            while( my.hasMoreTokens() && !ok ) {
                ok = lexem.equals(my.nextToken() );
            }
            if( ok ) {
                if( count == 0 ) {
                    System.out.println("'messages with key word \" " + lexem + " \" found:");
                }
                count++;
                System.out.println(data.history.get(j).toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("'messages with key word \" " + lexem + " \" not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }

    void regularExpression(String expression) {
        int count = 0;
        for( int j = 0; j < data.history.size(); j++ ) {
            String text = data.history.get(j).getText();
            if( text.contains(expression) ) {
                if( count == 0 ) {
                    System.out.println("'messages with expression \" " + expression + " \" found:");
                }
                count++;
                System.out.println(data.history.get(j).toString() );
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
        for( int j = 0; j < data.history.size(); j++ ) {
            if ( ( data.history.get(j).getTimestamp() >= l ) && ( data.history.get(j).getTimestamp() <= r ) ) {
                if ( count == 0 ) {
                    System.out.println("founded in time range " + l + "-" + r + " messages:");
                }
                count++;
                System.out.println(data.history.get(j).toString() );
            }
        }
        if( count == 0 ) {
            System.out.println("messages in time range " + l + "-" + r + " not found");
        }
        App.log.append(((Integer) count).toString() + " messages found" + '\n');
    }
}
