package common;

import com.example.qq.Client.Message;

import java.util.HashMap;

/**
 * �˷�����������ȡ�������Ե�
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
