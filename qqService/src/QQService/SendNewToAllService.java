package QQService;



import com.example.qq.Client.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class SendNewToAllService implements Runnable{



    @Override
    public void run() {

        //使用while 循环，让线程一直启用
        while(true) {

            System.out.println("请输入推送消息[输入exit退出推送服务]：");

            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();

            if("exit".equals(next)){
                break;
            }


            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(next);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息:" + next);

            //遍历当前所有通信线程得到 socket 发送message
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String string = iterator.next().toString();
                ServerConnectClientThread serverConnectClientThread = hm.get(string);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
