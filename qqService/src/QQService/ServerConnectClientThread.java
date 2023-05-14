package QQService;

//该类对应的对象和某个客户端保存通信

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Mysql;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnectClientThread  extends Thread{
      private final Socket socket;

      private final String userId;



    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {  //线程处于run 的状态，可以发送/接收消息
        while (true){

            try {
                System.out.println("服务端和客户端保存通信"+userId+"读取数据当中....");

                ObjectInputStream ois = null;
                try {



                    ois = new ObjectInputStream(socket.getInputStream());



                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message= null;
                try {
                    assert ois != null;
                    message = (Message)ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


                //使用message,根据message的类型，做对应处理

                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    //客户端要在线用户列表
                    String onlineUser = ManageClientThreads.getOnlineUser();

                    //返回message
                    //构建一个Message 对象 ，返回给客户端
                    Message message1=new Message();
                    message1.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    message1.setGetter(message.getSender());
                    //返回给客户端

                    ObjectOutputStream oos = null;
                    try {
                        oos = new ObjectOutputStream(socket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        oos.writeObject(message1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                //客户端准备退出服务器 MessageType.MESSAGE_CLIENT_EXIT

                else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    System.out.println(message.getSender()+" 已经退出服务端... ");
                    //将这个客户端对应线程从线程集合删除
                   Socket socket=  ManageClientThreads.serverConnectClientThread(message.getSender()).getSocket();
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message);


                        oos.close();

                        ManageClientThreads.remove(message.getSender());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //退出线程

                    break;
                }


                //客户端向服务器发起好友申请 MessageType.MESSAGE_ADD_FRIEND

                else if(message.getMesType().equals(MessageType.MESSAGE_ADD_FRIEND)){

                    //找寻对应好友线程，发送添加信息
                    System.out.println(message.getSender()+"正在寻找"+message.getGetter()+"的信息......");
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getGetter());
                    Socket socket = serverConnectClientThread.getSocket();


                    Message select = Mysql.mysqlSelect(message.getSender());

                    Message message1=new Message();

                    message1.setGetter(message.getSender());

                    message1.setMesType(MessageType.MESSAGE_ADD_FRIEND);

                    message1.setAccount_name(select.getAccount_name());


                    if(select.getAccount_name()==null){
                        message1.setContent(select.getSender()+"想添加你.....");
                    }
                    else{
                        message1.setContent(select.getAccount_name()+"想添加你.....");
                    }

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                    System.out.println("消息已经返回......");

                }


                //客户端接收申请，申请信息发送给服务器，服务器将确认申请信息发送给申请客户端

                 else if(message.getMesType().equals(MessageType.MESSAGE_ACCEPT_ADD_FRIEND)){

                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getGetter());
                    Socket socket1 = serverConnectClientThread.getSocket();


                    Message select = Mysql.mysqlSelect(message.getSender());

                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESSAGE_ACCEPT_ADD_FRIEND);
                    message1.setGetter(message.getSender());
                    message1.setSender(message.getGetter());
                    message1.setAccount_name(select.getAccount_name());

                    ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
                    oos.writeObject(message1);
                }


                 //客户端发送消息让服务器取寻找对应的客户端信息 MessageType.MESSAGE_SEARCH

                else if(message.getMesType().equals(MessageType.MESSAGE_SEARCH)){
                    System.out.println("客户端"+message.getSender()+"正在寻找"+message.getGetter()+"的信息.....");
                    Socket socket1 = ManageClientThreads.serverConnectClientThread(message.getSender()).getSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());


                    Message message1 = Mysql.mysqlSelect(message.getGetter());
                    message1.setMesType(MessageType.MESSAGE_SEARCH);

                    if(message==null){
                       message1.setGetter("查无此人.....");
                        oos.writeObject(message1);
                    }
                     else {
                        oos.writeObject(message1);
                    }


                    System.out.println("数据已返回....");

                }
                  
                  
                  
                  else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){

                      //根据message获取getterId,然后再得到对应线程
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getGetter());
                    //得到对应socket的对应输出流，将message 对象转发给指定客户端
                    ObjectOutputStream oos = null;
                    try {
                        oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        oos.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


                else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    //需要遍历管理线程集合，把所有线程socket 得到，然后把message 进行转发
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();

                    for (String s : hm.keySet()) {

                        //取出在线用户 id
                        if (!s.equals(message.getSender())) { //排除群发消息的用户

                            //进行转发
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(s).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }

                }


                        //根据getterId 获取对应线程，将message 对象转发 发送文件
                        else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)){
                        ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getSender());
                        ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                }

                else
                    {
                    System.out.println("其它类型的message...");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
