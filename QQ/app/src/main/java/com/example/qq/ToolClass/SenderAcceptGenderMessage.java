package com.example.qq.ToolClass;

import com.example.qq.Client.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SenderAcceptGenderMessage {

    private static HashMap<String,Message> messages=new HashMap<>();

    private static HashMap<String,Message> addMessage=new HashMap<>();



    public static void setMessages(String sender, Message mes) {
               messages.put(sender,mes);
    }

    public static HashMap<String, Message> getMessages() {
        return messages;
    }

    public static void reMoveGetMessage(String key){
          messages.remove(key);
    }

    public static void setAddMessage(String sender,Message message) {
        addMessage.put(sender,message);
    }

    public static HashMap<String, Message> getAddMessage() {
        return addMessage;
    }



}
