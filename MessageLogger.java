package YummyList;

import java.io.*;

public class MessageLogger {

    private static final String FILE_NAME = "message.txt";

    public static void saveMessage(String message){
        try(PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME,true))){
            pw.println(message);
            pw.println("=================================");
        }catch(IOException e){
            System.out.println("Error saving message");
        }
    }
}
