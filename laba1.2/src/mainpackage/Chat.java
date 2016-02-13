package mainpackage;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.*;
import java.io.*;
/**
 I hope, that adding messages will be in chronological order, if no, i will add sorting

    1 -  add message
    2 - remove message
    3 - show history
    4 - search by author
    5 - show history in time range
 */
public class Chat {
    private static int messageCount=0;
    private static int containerSize=10;
    private static int lastId=0;
    private static Message history[]=new Message[containerSize];
    private static String copyArgs[];    ///  temp, may be will be removed later
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
    private static void addMessage(int i){
        sizeController();
        String text=new String(copyArgs[i+1]);
        String author=new String(copyArgs[i+2]);
        int time=Integer.parseInt(copyArgs[i+3]);
        history[messageCount++]=new Message(lastId,text,author,time);
        lastId++;
    }
    private static void removeMessage(int i){   /// temp, will be edited later with small changes
        int ind=Integer.parseInt(copyArgs[i+1]);
        int j;
        for(j=0;j<messageCount;j++){
            if(history[j].getId()==ind)
                break;
        }
        if(j<messageCount){
            while(j<messageCount){
                history[j]=history[j+1];
                j++;
            }
            messageCount--;
        }
    }
    private static void searchAuthor(int i){
        boolean found=false;
        String auth=new String(copyArgs[i+1]);
        for(int j=0;j<messageCount;j++)
            if(auth.equals(history[j].getAuthor())){
                if(!found){
                    found=true;
                    System.out.println("founded "+copyArgs[i+1]+"'s messages:");
                }
                System.out.println(history[j].toString());
            }
        if(!found)
            System.out.println(copyArgs[i+1]+"'s messages not found");
    }
    private static void showTimeRangeHistory(int i){
        boolean found=false;
        int l=Integer.parseInt(copyArgs[i+1]);
        int r=Integer.parseInt(copyArgs[i+2]);
        for(int j=0;j<messageCount;j++)
            if(history[j].getTimestamp()>=l && history[j].getTimestamp()<=r){
                if(!found){
                    found=true;
                    System.out.println("founded in time range "+copyArgs[i+1]+"-"+copyArgs[i+2]+" messages:");
                }
                System.out.println(history[j].toString());
            }
        if(!found)
            System.out.println("messages in time range "+copyArgs[i+1]+"-"+copyArgs[i+2]+" not found");
    }
    private static void showHistory(){     /// temp, will be edited later with small changes
        System.out.println("history:");
        for(int j=0;j<messageCount;j++){
            System.out.println(history[j].toString()+"\n");
        }
    }
    private static void downloadFromFile(String fileName) throws IOException{     /// temp, will be modified after changing ID
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
        copyArgs = new String[args.length];
        int i=0;
        for(i=0;i<args.length;i++)
            copyArgs[i]=new String(args[i]);
        i=0;
        while(i<args.length){
            if("1".equals(args[i])){
                addMessage(i);
                i+=4;
            }
            else if("2".equals(args[i])){
                removeMessage(i);
                i+=2;
            }
            else if("3".equals(args[i])){
                showHistory();
                i++;
            }
            else if("4".equals(args[i])){
                searchAuthor(i);
                i+=2;
            }
            else if("5".equals(args[i])){
                showTimeRangeHistory(i);
                i+=3;
            }
        }
        saveHistory();
        showHistory();
        ///
    }
}
