package QQService;

import org.json.simple.JSONObject;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;

public class ServerHeart implements Runnable{
    private final Socket socket;
    private final String userId;
    private final ObjectInputStream ois;
    public ServerHeart(Socket socket, String userId, ObjectInputStream ois) {
        this.socket = socket;
        this.userId=userId;
        this.ois = ois;
    }

    @Override
    public void run() {

                  try {

                      JSONObject o=(JSONObject) ois.readObject();
                      LocalDateTime before=(LocalDateTime) o.get("time");
                      LocalDateTime after=LocalDateTime.now();
                      Duration duration=Duration.between(before,after);
                      if(duration.toMinutes()>20){
                          ManageClientThreads.serverConnectClientThread(userId);
                          socket.close();
                          System.out.println(userId+"已经下线.....");
                      }

                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
    }

