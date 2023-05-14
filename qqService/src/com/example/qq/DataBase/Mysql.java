package com.example.qq.DataBase;

import com.example.qq.Client.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class Mysql {
//      static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
//      static final String DB_URL="jdbc:mysql://localhost:3306/qq?serverTimezone=UTC";
//      static final String User="root";
//      static final String Pass="2997578627lsh";


    //×¢²áÕËºÅ
    public static void alert(Message message1, Socket socket) throws SQLException, IOException {

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        Message message = new Message();

        if (mysqlAlert(message1)) {

            message.setMesType(MessageType.REGISTER_SUCCEED);
            message.setContent("×¢²á³É¹¦......");
        } else {
            message.setMesType(MessageType.REGISTER_FAIL);
            message.setContent("×¢²áÊ§°Ü......");
        }
        oos.writeObject(message);
        oos.close();
    }

    private static Boolean mysqlAlert(Message message) throws SQLException {
//         Class.forName(JDBC_DRIVER);
//         Connection conn=DriverManager.getConnection(DB_URL,User,Pass);

        Connection conn;
        conn = Utils.getConnection();


        Statement st = conn.createStatement();
        String sql = "select id from message";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            if (rs.getString("id").equals(message.getSender())) {
              Utils.close(rs,st,conn);
                return false;
            }
        }
        sql = "insert into message(id,password,account_name,account_email)values (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, message.getSender());
        ps.setString(2, message.getGetter());
        ps.setString(3, message.getAccount_name());
        ps.setString(4, message.getAccount_email());

        ps.executeUpdate();
//        conn.close();
//        st.close();
//        rs.close();
        Utils.close(rs,st,conn);
        return true;
    }

    public static Boolean mysqlSelect(String acc, String pwd, Socket socket) throws SQLException, IOException {
//        Class.forName(JDBC_DRIVER);
//        Connection conn=DriverManager.getConnection(DB_URL,User,Pass);

        Connection conn;
        conn = Utils.getConnection();

        Statement st = conn.createStatement();
        String sql = "select id,password from message";
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            if (rs.getString("id").equals(acc) && rs.getString("password").equals(pwd)) {


                Message message = getSqlMessage(rs);


                message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                message.setContent("µÇÂ¼³É¹¦......");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message);

//                conn.close();
//                st.close();
//                rs.close();

                Utils.close(rs,st,conn);
                return true;
            }
        }

       Utils.close(rs,st,conn);
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FAIL);
        message.setContent("µÇÂ¼Ê§°Ü......");
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        oos.close();
        return false;

    }

    public static Message mysqlSelect(String str) throws SQLException {
//        Class.forName(JDBC_DRIVER);
//        Connection conn=DriverManager.getConnection(DB_URL,User,Pass);
        Connection conn;
        conn = Utils.getConnection();

        Statement st = conn.createStatement();
        String sql = "select id from message";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            if (rs.getString("id").equals(str)) {

                Message message = getSqlMessage(rs);

                Utils.close(rs,st,conn);

                return message;
            }
        }
        return null;
    }


    public static Message getSqlMessage(ResultSet resultSet) {
        Message message = new Message();
        Connection connection=null;
        PreparedStatement preparedStatement=null;



        try {
                connection=Utils.getConnection();
                String sql="select id,password,account_name,account_email from message where id=?";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setString(1,resultSet.getString("id"));
                resultSet=preparedStatement.executeQuery();

                while (resultSet.next()){
                    message.setSender(resultSet.getString("id"));
                    message.setAccount_name(resultSet.getString("account_name"));
                    message.setAccount_email(resultSet.getString("account_email"));
                }


        }catch (SQLException throwable){
            throwable.printStackTrace();
        }


        return message;

    }

}


