package QQService;


import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.DataBase.Mysql;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

//���������ڶ˿�9999 �ȴ��ͻ��˵����ӣ�����ͨ��
public class QQServer{


     public QQServer(){
         //�˿ڣ�����д�������ļ���

         ServerSocket ss = null;
         try {
             System.out.println("��������9999�˿ڼ���");

             //�������������߳�
             new Thread(new SendNewToAllService()).start();


             ss =new ServerSocket(9999);

             while (true){

                 //����ĳ���ͻ������Ӻ󣬻������������Ϊ�ж���ͻ��������ӷ�����
                   Socket socket = ss.accept();        //û�пͻ������ӣ���������

                 //�õ�socket ��������������
                   ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                 Message message=(Message)ois.readObject();

                   //��ȡ�ͻ��˷��͵�user ����
                   Bean bean=(Bean)ois.readObject();



                 if(message.getMesType().equals(MessageType.REGISTER)){


                     Mysql.alert(message,socket);


                 }


                 else if(message.getMesType().equals(MessageType.Count)){
                     System.out.println(bean.getAccount()+"׼�����е�¼....");


                     if(Mysql.mysqlSelect(bean.getAccount(), bean.getPassword(), socket)){

                         //����һ���̣߳��Ϳͻ��˱���ͨ�ţ����߳���Ҫ����socket ����

                         //�߳�����
                         ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, bean.getAccount());

                         serverConnectClientThread.start();

                         //�Ѹ��̷߳��뼯���й���
                         ManageClientThreads.addClientThread(bean.getAccount(),serverConnectClientThread);
                         System.out.println(bean.getAccount()+"�Ѿ��ɹ���¼,���������ڷ���ͻ���.....");
                     }

                 }

             }


         } catch (Exception e) {
             e.printStackTrace();
         }finally {

             //���������˳�while ��˵������˲�����
             try {

                 Objects.requireNonNull(ss).close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
}
