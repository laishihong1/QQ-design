package com.example.qq.DataBase;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//jdbc ���ݿ���ʹ����࣬ʹ��druid ���ӳ�
public class Utils {

    //�������ӳض���DataSource
    //���ӳض�����Ϊ��̬
   private   static DataSource dataSource;

    //ʹ�����������ʱ���������ӳض���
    //��ζ�������ӳصĴ���ֻ��ִ��һ��
    //��̬����飺����һ��ʹ��jdbcUtils �����ʱ�򣬾�̬�����ᱻ�Զ�ִ��һ��
    static {
          Properties properties=new Properties();
          try{
              InputStream is= Utils.class.getResourceAsStream("/druid.properties.properties");

                  properties.load(is);

                  //�������ӳ�

                  dataSource= DruidDataSourceFactory.createDataSource(properties);

              } catch (Exception ioException) {
              ioException.printStackTrace();
          }

    }




    //�������ӳض���ds
    public  static DataSource getDataSource(){
        //����������������ӳض��󷵻ظ�������
        return dataSource;
    }

    //�������Ӷ���
    public static Connection getConnection() throws SQLException {
        //�����ӳص�һ�����Ӷ�����������

            return  dataSource.getConnection();

    }

    //�رն���(�黹���� rs,pstmt,cn)
    public static void close(ResultSet rs, Statement stmt,Connection cn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

            if(stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }

            if(cn!=null){
                try {
                    cn.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

}
