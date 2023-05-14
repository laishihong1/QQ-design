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

        //ʹ��while ѭ�������߳�һֱ����
        while(true) {

            System.out.println("������������Ϣ[����exit�˳����ͷ���]��");

            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();

            if("exit".equals(next)){
                break;
            }


            Message message = new Message();
            message.setSender("������");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(next);
            message.setSendTime(new Date().toString());
            System.out.println("������������Ϣ:" + next);

            //������ǰ����ͨ���̵߳õ� socket ����message
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
