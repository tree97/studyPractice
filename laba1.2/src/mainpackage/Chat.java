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
        history[messageCount++]=new Message(lastId,text,author,timestamp);
        lastId++;
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
        }
        else
            System.out.println("No messages deleted");
    }
    private static void searchAuthor(String auth){
        boolean found=false;
        for(int j=0;j<messageCount;j++)
            if(auth.equals(history[j].getAuthor())){
                if(!found){
                    found=true;
                    System.out.println("founded "+auth+"'s messages:");
                }
                System.out.println(history[j].toString());
            }
        if(!found)
            System.out.println(auth+"'s messages not found");
    }
    private static void searchMessageByLexem(String lexem){
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
                System.out.println(history[j].toString());
            }
        }
        if(!found)
            System.out.println("'messages with key word \" "+lexem+" \" not found");
    }
    private static void searchMessageWithRegularExpression(String expression){
        boolean found=false;
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
                System.out.println(history[j].toString());
            }
        }
        if(!found)
            System.out.println("'messages with expression \" "+expression+" \" not found");
    }
    private static void showTimeRangeHistory(int l,int r){
        boolean found=false;
        for(int j=0;j<messageCount;j++)
            if(history[j].getTimestamp()>=l && history[j].getTimestamp()<=r){
                if(!found){
                    found=true;
                    System.out.println("founded in time range "+l+"-"+r+" messages:");
                }
                System.out.println(history[j].toString());
            }
        if(!found)
            System.out.println("messages in time range "+l+"-"+r+" not found");
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
    }
    public static void main(String args[]) throws IOException{

        ///    temp, will be removed later with adding the .json files
     //   downloadFromFile("output.json");
        int i=0;
        while(i<args.length){
            if("1".equals(args[i])){
                String text=args[i+1];
                String author=args[i+2];
                int timestamp=Integer.parseInt(args[i+3]);
                addMessage(text,author,timestamp);
                i+=4;
            }
            else if("2".equals(args[i])){
                int id=Integer.parseInt(args[i+1]);
                removeMessage(id);
                i+=2;
            }
            else if("3".equals(args[i])){
                showHistory();
                i++;
            }
            else if("4".equals(args[i])){
                searchAuthor(args[i+1]);
                i+=2;
            }
            else if("5".equals(args[i])){
                int l,r;
                l=Integer.parseInt(args[i+1]);
                r=Integer.parseInt(args[i+2]);
                showTimeRangeHistory(l,r);
                i+=3;
            }
            else if("6".equals(args[i])){
                searchMessageByLexem(args[i+1]);
                i+=2;
            }
            else if("7".equals(args[i])){
                searchMessageWithRegularExpression(args[i+1]);
                i+=2;
            }
        }
    //    saveHistory();
    //    showHistory();
        ///
    }
}
