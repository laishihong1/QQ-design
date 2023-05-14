package common;

import com.example.qq.Client.Message;

import java.util.HashMap;

/**
 * 此方法是用来读取离线留言的
 */
public class AcceptMessage {
    private static HashMap<String, Message>hashMap=new HashMap<>();

    public static void setHashMap(String userId,Message message) {
        hashMap.put(userId,message);
    }

    public static HashMap<String, Message> getHashMap() {
        return hashMap;
    }
}
