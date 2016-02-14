package mainpackage;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.util.*;
import java.io.*;
/**
 I hope, that adding messages will be in chronological order, if no, i will add sorting

    1 -  add message
    2 - remove message
    3 - show history
    4 - search by author
    5 - show history in time range
    6 - find message by lexem
    7 - search by regular expression
 */
public class Chat {
    private static int messageCount=0;
    private static int containerSize=10;
    private static int lastId=0;
    private static Message history[]=new Message[containerSize];
    private static StringBuffer log=new StringBuffer();
    private static int bigTextLength=100;
    private static int addedMessagesCount=0;
    private static int removedMessagesCount=0;
    private static void sizeController(){
        Message buf[];
        if(messageCount==containerSize){
            buf=new Message[containerSize];
            for(int j=0;j<containerSize;j++)
                buf[j]=new Message(history[j]);
            if(containerSize<10)
                history= new Message[containerSize+10];
            else
                history = new Message[containerSize * 2];
            for(int j=0;j<containerSize;j++)
                history[j]=new Message(buf[j]);
            if(containerSize<10)
                containerSize+=10;
            else
                containerSize*=2;
        }
    }
    private static void addMessage(String text,String author,int timestamp){
        sizeController();
        addedMessagesCount++;
        history[messageCount++]=new Message(lastId,text,author,timestamp);
        lastId++;
        if(text.length()>bigTextLength){
            log.append("Warning: Text message is long ( longer than "+((Integer)bigTextLength).toString()+" symbols )");
            log.append('\n');
        }
    }
    private static void removeMessage(int id){   /// temp, will be edited later with small changes with ID
        int j;
        for(j=0;j<messageCount;j++){
            if(history[j].getId()==id)
                break;
        }
        if(j<messageCount){
            while(j<messageCount){
                history[j]=history[j+1];
                j++;
            }
            messageCount--;
            removedMessagesCount++;
        }
        else{
            log.append("Warning: No message with such ID for delete");
            log.append('\n');
        }
    }
    private static void searchAuthor(String auth){
        int count=0;
        boolean found=false;
        for(int j=0;j<messageCount;j++)
            if(auth.equals(history[j].getAuthor())){
                if(!found){
                    found=true;
                    System.out.println("founded "+auth+"'s messages:");
                }
                count++;
                System.out.println(history[j].toString());
            }
        if(!found)
            System.out.println(auth+"'s messages not found");
        log.append(((Integer)count).toString()+" messages found");
        log.append('\n');
    }
    private static void searchMessageByLexem(String lexem){
        int count=0;
        boolean found=false;
        for(int j=0;j<messageCount;j++){
            StringTokenizer my=new StringTokenizer(history[j].getText(),",.?! \"()[]<>:;'");
            boolean ok=false;
            while(my.hasMoreTokens()){
                if(lexem.equals(my.nextToken())){
                    ok=true;
                    break;
                }
            }
            if(ok){
                if(!found){
                    found=true;
                    System.out.println("'messages with key word \" "+lexem+" \" found:");
                }
                count++;
                System.out.println(history[j].toString());
            }
        }
        if(!found)
            System.out.println("'messages with key word \" "+lexem+" \" not found");
        log.append(((Integer)count).toString()+" messages found");
        log.append('\n');
    }
    private static void searchMessageWithRegularExpression(String expression){
        boolean found=false;
        int count=0;
        for(int j=0;j<messageCount;j++){
            String text=history[j].getText();
            boolean ok=false;
            for(int k=0;k+expression.length()-1<text.length();k++){
                if(expression.equals(text.substring(k,k+expression.length()))){
                    ok=true;
                    break;
                }
            }
            if(ok){
                if(!found){
                    found=true;
                    System.out.println("'messages with expression \" "+expression+" \" found:");
                }
                count++;
                System.out.println(history[j].toString());
            }
        }
        if(!found)
            System.out.println("'messages with expression \" "+expression+" \" not found");
        log.append(((Integer)count).toString()+" messages found");
        log.append('\n');
    }
    private static void showTimeRangeHistory(int l,int r){
        if(l>r){
            log.append("Warning: Incorrect time periond(iphone time found)");
            log.append('\n');
        }
        int count=0;
        boolean found=false;
        for(int j=0;j<messageCount;j++)
            if(history[j].getTimestamp()>=l && history[j].getTimestamp()<=r){
                if(!found){
                    found=true;
                    System.out.println("founded in time range "+l+"-"+r+" messages:");
                }
                count++;
                System.out.println(history[j].toString());
            }
        if(!found)
            System.out.println("messages in time range "+l+"-"+r+" not found");
        log.append(((Integer)count).toString()+" messages found");
        log.append('\n');
    }
    private static void showHistory(){     /// temp, will be edited later with small changes after adding timestamp
        System.out.println("history:");
        for(int j=0;j<messageCount;j++){
            System.out.println(history[j].toString()+"\n");
        }
    }
    private static void downloadFromFile(String fileName) throws IOException{
        messageCount=0;
        JsonReader my=new JsonReader(new InputStreamReader(new FileInputStream(fileName)));
        Gson gson=new Gson();

        history=gson.fromJson(my,Message[].class);

        containerSize=messageCount=history.length;
        //reader.close();
    }
    private static void saveHistory()throws IOException{
        Writer saving=new OutputStreamWriter(new FileOutputStream("output.json"),"UTF-8");
        Gson gson=new Gson();
        Message tempMessage[]=new Message[messageCount];
        for(int i=0;i<messageCount;i++)
            tempMessage[i]=history[i];
        gson.toJson(tempMessage,saving);
        saving.close();
        log.append("Current messages are saved");
        log.append('\n');
    }
    private static void saveLog()throws IOException{
        File save=new File("log2.txt");
        BufferedWriter output=new BufferedWriter(new FileWriter(save));
        output.write(log.toString());
        output.close();
    }
    private static void finalLogging(){
        log.append("End of program work:");
        log.append('\n');
        log.append("added messages count = "+((Integer)addedMessagesCount).toString());
        log.append('\n');
        log.append("removed messages count = "+((Integer)removedMessagesCount).toString());
        log.append('\n');
    }
    public static void main(String args[]) throws IOException{
        try{
            ///    temp, will be removed later with adding the .json files
            //   downloadFromFile("output.json");
            int i=0;
            while(i<args.length){
                if("1".equals(args[i])){
                    log.append("Request: add message");
                    log.append('\n');
                    log.append("text: "+args[i+1]);
                    log.append('\n');
                    log.append("author: "+args[i+2]);
                    log.append('\n');
                    log.append("time: "+args[i+3]);
                    log.append('\n');
                    String text=args[i+1];
                    String author=args[i+2];
                    int timestamp=Integer.parseInt(args[i+3]);
                    addMessage(text,author,timestamp);
                    i+=4;
                }
                else if("2".equals(args[i])){
                    log.append("Request: delete message");
                    log.append('\n');
                    log.append("ID: "+args[i+1]);
                    log.append('\n');
                    int id=Integer.parseInt(args[i+1]);
                    removeMessage(id);
                    i+=2;
                }
                else if("3".equals(args[i])){
                    log.append("Request: show messages");
                    log.append('\n');
                    showHistory();
                    i++;
                }
                else if("4".equals(args[i])){
                    log.append("Request: show messages with author");
                    log.append('\n');
                    log.append("author: "+args[i+1]);
                    log.append('\n');
                    searchAuthor(args[i+1]);
                    i+=2;
                }
                else if("5".equals(args[i])){
                    log.append("Request: show messages with time range");
                    log.append('\n');
                    log.append("time range: "+args[i+1]+"-"+args[i+2]);
                    log.append('\n');
                    int l,r;
                    l=Integer.parseInt(args[i+1]);
                    r=Integer.parseInt(args[i+2]);
                    showTimeRangeHistory(l,r);
                    i+=3;
                }
                else if("6".equals(args[i])){
                    log.append("Request: show messages with key word in text");
                    log.append('\n');
                    log.append("key word: "+args[i+1]);
                    log.append('\n');
                    searchMessageByLexem(args[i+1]);
                    i+=2;
                }
                else if("7".equals(args[i])){
                    log.append("Request: show messages with regular expression");
                    log.append('\n');
                    log.append("regular expression: \" "+args[i+1]+" \"");
                    log.append('\n');
                    searchMessageWithRegularExpression(args[i+1]);
                    i+=2;
                }
            }
            finalLogging();
            saveLog();
            //    saveHistory();
            //    showHistory();
            ///
        }
        catch(IllegalArgumentException | IOException e){
            System.out.println(e.getMessage());
            log.append("Error: "+e.getMessage());
            log.append('\n');
        }
    }
}
