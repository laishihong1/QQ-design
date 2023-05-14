package QQService;

import java.util.HashMap;

//�������ڹ���ͨ�ŵ��߳�
public class ManageClientThreads {
    private static HashMap<String,ServerConnectClientThread> hm= new HashMap<>();

    //���� hm
    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    //����̶߳���hm ����
    public static void addClientThread(String userId,ServerConnectClientThread serverConnectClientThread){
        hm.put(userId,serverConnectClientThread);
    }

    //����userId ���� serverConnectClientThread ����
    public static ServerConnectClientThread serverConnectClientThread(String userId){
        return hm.get(userId);
    }

    //���������û��б�
    public static String getOnlineUser(){
        //���ϱ��������� HashMap key ֵ
        String onlineUserList="";
        for (String s : hm.keySet()) {
            onlineUserList+=hm.get(s)+" ";
        }
        return onlineUserList;
    }

    //�Ӽ����У�ɾ��ĳ���̶߳���
    public static void remove(String userId){
        hm.remove(userId);
    }



}
