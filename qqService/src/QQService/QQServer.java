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

//服务器，在端口9999 等待客户端的连接，保持通信
public class QQServer{


     public QQServer(){
         //端口，可以写在配置文件内

         ServerSocket ss = null;
         try {
             System.out.println("服务器在9999端口监听");

             //启动推送新闻线程
             new Thread(new SendNewToAllService()).start();


             ss =new ServerSocket(9999);

             while (true){

                 //当和某个客户端连接后，会继续监听，因为有多个客户端来连接服务器
                   Socket socket = ss.accept();        //没有客户端连接，程序阻塞

                 //得到socket 关联对象输入流
                   ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                 Message message=(Message)ois.readObject();

                   //读取客户端发送的user 对象
                   Bean bean=(Bean)ois.readObject();



                 if(message.getMesType().equals(MessageType.REGISTER)){


                     Mysql.alert(message,socket);


                 }


                 else if(message.getMesType().equals(MessageType.Count)){
                     System.out.println(bean.getAccount()+"准备进行登录....");


                     if(Mysql.mysqlSelect(bean.getAccount(), bean.getPassword(), socket)){

                         //创建一个线程，和客户端保存通信，该线程需要持有socket 对象

                         //线程启动
                         ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, bean.getAccount());

                         serverConnectClientThread.start();

                         //把该线程放入集合中管理
                         ManageClientThreads.addClientThread(bean.getAccount(),serverConnectClientThread);
                         System.out.println(bean.getAccount()+"已经成功登录,服务器正在服务客户端.....");
                     }

                 }

             }


         } catch (Exception e) {
             e.printStackTrace();
         }finally {

             //如果服务端退出while ，说明服务端不监听
             try {

                 Objects.requireNonNull(ss).close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
}
