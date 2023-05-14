package QQService;

//�����Ӧ�Ķ����ĳ���ͻ��˱���ͨ��

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
    public void run() {  //�̴߳���run ��״̬�����Է���/������Ϣ
        while (true){

            try {
                System.out.println("����˺Ϳͻ��˱���ͨ��"+userId+"��ȡ���ݵ���....");

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


                //ʹ��message,����message�����ͣ�����Ӧ����

                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    //�ͻ���Ҫ�����û��б�
                    String onlineUser = ManageClientThreads.getOnlineUser();

                    //����message
                    //����һ��Message ���� �����ظ��ͻ���
                    Message message1=new Message();
                    message1.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    message1.setGetter(message.getSender());
                    //���ظ��ͻ���

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


                //�ͻ���׼���˳������� MessageType.MESSAGE_CLIENT_EXIT

                else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    System.out.println(message.getSender()+" �Ѿ��˳������... ");
                    //������ͻ��˶�Ӧ�̴߳��̼߳���ɾ��
                   Socket socket=  ManageClientThreads.serverConnectClientThread(message.getSender()).getSocket();
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message);


                        oos.close();

                        ManageClientThreads.remove(message.getSender());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //�˳��߳�

                    break;
                }


                //�ͻ��������������������� MessageType.MESSAGE_ADD_FRIEND

                else if(message.getMesType().equals(MessageType.MESSAGE_ADD_FRIEND)){

                    //��Ѱ��Ӧ�����̣߳����������Ϣ
                    System.out.println(message.getSender()+"����Ѱ��"+message.getGetter()+"����Ϣ......");
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getGetter());
                    Socket socket = serverConnectClientThread.getSocket();


                    Message select = Mysql.mysqlSelect(message.getSender());

                    Message message1=new Message();

                    message1.setGetter(message.getSender());

                    message1.setMesType(MessageType.MESSAGE_ADD_FRIEND);

                    message1.setAccount_name(select.getAccount_name());


                    if(select.getAccount_name()==null){
                        message1.setContent(select.getSender()+"�������.....");
                    }
                    else{
                        message1.setContent(select.getAccount_name()+"�������.....");
                    }

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                    System.out.println("��Ϣ�Ѿ�����......");

                }


                //�ͻ��˽������룬������Ϣ���͸�����������������ȷ��������Ϣ���͸�����ͻ���

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


                 //�ͻ��˷�����Ϣ�÷�����ȡѰ�Ҷ�Ӧ�Ŀͻ�����Ϣ MessageType.MESSAGE_SEARCH

                else if(message.getMesType().equals(MessageType.MESSAGE_SEARCH)){
                    System.out.println("�ͻ���"+message.getSender()+"����Ѱ��"+message.getGetter()+"����Ϣ.....");
                    Socket socket1 = ManageClientThreads.serverConnectClientThread(message.getSender()).getSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());


                    Message message1 = Mysql.mysqlSelect(message.getGetter());
                    message1.setMesType(MessageType.MESSAGE_SEARCH);

                    if(message==null){
                       message1.setGetter("���޴���.....");
                        oos.writeObject(message1);
                    }
                     else {
                        oos.writeObject(message1);
                    }


                    System.out.println("�����ѷ���....");

                }
                  
                  
                  
                  else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){

                      //����message��ȡgetterId,Ȼ���ٵõ���Ӧ�߳�
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getGetter());
                    //�õ���Ӧsocket�Ķ�Ӧ���������message ����ת����ָ���ͻ���
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
                    //��Ҫ���������̼߳��ϣ��������߳�socket �õ���Ȼ���message ����ת��
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();

                    for (String s : hm.keySet()) {

                        //ȡ�������û� id
                        if (!s.equals(message.getSender())) { //�ų�Ⱥ����Ϣ���û�

                            //����ת��
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(s).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }

                }


                        //����getterId ��ȡ��Ӧ�̣߳���message ����ת�� �����ļ�
                        else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES)){
                        ServerConnectClientThread serverConnectClientThread = ManageClientThreads.serverConnectClientThread(message.getSender());
                        ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                }

                else
                    {
                    System.out.println("�������͵�message...");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
