package com.example.qq.QQThread;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.ToolClass.MessageType;
import com.example.qq.ToolClass.SenderAcceptGenderMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;

/**
 *  该类实现客户端接收服务器发来的数据
 */

public class ClientConnectServerThead implements Runnable {

    //线程需要持有Socket
    private static Socket socket;

    //接收一个Socket 对象
    public ClientConnectServerThead(Socket socket){

       this.socket =socket;

    }

    @Override
    public void run() {

        while (true){
            //此线程 需要一直在后台和服务器通信，使用while 循环
            try {
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //如果服务器没有发送message 对象 ，线程会阻塞
                Message mes= null;
                try {
                    if(ois!=null){
                        mes = (Message) ois.readObject();
                    }

                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                if(ois==null){
                    return;
                }
                //判断这个message类型，做相应处理
                //如果是读到的是 服务端返回的是在线用户列表

                if((mes).getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    String[] outLineUsers = mes.getContent().split(" ");
                    for (String outLineUser : outLineUsers) {
                        System.out.println(outLineUser);
                    }
                }

                //同意添加好友
              else if(mes.getMesType().equals(MessageType.MESSAGE_ACCEPT_ADD_FRIEND)){
                   SenderAcceptGenderMessage.setAddMessage(mes.getSender(),mes);
                }

                //如果是添加好友
                else if(mes.getMesType().equals(MessageType.MESSAGE_ADD_FRIEND)){
                    //把添加信息放入到添加集合
                    SenderAcceptGenderMessage.setMessages(mes.getGetter(),mes);
                }

                //普通聊天消息
                //把从服务器转发消息，显示出来
                else if(mes.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                      EventBus.getDefault().post(mes);
                }

                else if(mes.getMesType().equals(MessageType.MESSAGE_SEARCH)){
                   Bean bean=new Bean();
                   bean.setAccount(mes.getGetter());
                   EventBus.getDefault().post(bean);

                }


                //接收群发消息
                else if(mes.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                   EventBus.getDefault().post(mes);
                }

                //接收文件
                else if(mes.getMesType().equals(MessageType.MESSAGE_FILE_MES)){
                    System.out.println("\n"+mes.getSender()+"给"+mes.getGetter()
                            +"发文件"+mes.getSrc()+"到电脑目录："+mes.getDest());

                    //取出message 的文件字节数组 通过文件输出流写入到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(mes.getDest());
                    fileOutputStream.write(mes.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n "+"保存文件成功");
                }

                else if(mes.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //方便得到Socket
    public  static Socket getSocket() {
        return socket;
    }
}
