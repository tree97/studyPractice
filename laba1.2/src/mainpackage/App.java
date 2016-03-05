package mainpackage;

import java.io.IOException;

/**
 *  request: code arguments
 *  code: 1 - 7
 *  1 - add message
 *  2 - remove message
 *  3 - show history
 *  4 - search by author
 *  5 - show history in time range
 *  6 - find message by lexem
 *  7 - search by regular expression
 *  
 *  arguments for each code:
 *  for 1: text_of_message author
 *  for 2: id_number
 *  for 3: no_arguments
 *  for 4: name_of_author
 *  difficult one  - for 5: time_in_milliseconds_since time_in_milliseconds_end
 *  for 6: key_word
 *  for 7: regular_expression_string (better to write it in "")
 */

public class App {

    static StringBuilder log = new StringBuilder();

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
