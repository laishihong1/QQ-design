package com.example.qq.ToolClass;

import com.example.qq.Client.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class FileClientService {
    /**
     *
     * @param src 文件路径
     * @param dest 文件名
     * @param senderId 发送者
     * @param getterId 接收者
     */
    public void sendFileToOne(String src,String dest,String senderId,String getterId){

        //读取src文件 --》message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);


        //需要将文件读取
        FileInputStream fileInputStream=null;
        byte[]fileBytes=new byte[(int)new File(src).length()];
        try {
            fileInputStream= new FileInputStream(src);
            fileInputStream.read(fileBytes);

            //将文件对应的字节数组设置为message



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\n "+getterId+"给"+senderId+"发送文件："+src+"到对方的电脑目录"+dest);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManagerClimentConnectServerThread.clientConnectServerThead(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
