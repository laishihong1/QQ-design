package QQService;

import java.util.HashMap;

//该类用于管理通信的线程
public class ManageClientThreads {
    private static HashMap<String,ServerConnectClientThread> hm= new HashMap<>();

    //返回 hm
    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    //添加线程对象到hm 集合
    public static void addClientThread(String userId,ServerConnectClientThread serverConnectClientThread){
        hm.put(userId,serverConnectClientThread);
    }

    //根据userId 返回 serverConnectClientThread 对象
    public static ServerConnectClientThread serverConnectClientThread(String userId){
        return hm.get(userId);
    }

    //返回在线用户列表
    public static String getOnlineUser(){
        //集合遍历，遍历 HashMap key 值
        String onlineUserList="";
        for (String s : hm.keySet()) {
            onlineUserList+=hm.get(s)+" ";
        }
        return onlineUserList;
    }

    //从集合中，删除某个线程对象
    public static void remove(String userId){
        hm.remove(userId);
    }



}
