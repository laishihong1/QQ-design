package com.example.qq.DataBase;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//jdbc 数据库访问工具类，使用druid 连接池
public class Utils {

    //定义连接池对象DataSource
    //连接池对象定义为静态
   private   static DataSource dataSource;

    //使用这个工具类时，创建连接池对象
    //意味创建连接池的代码只能执行一次
    //静态代码块：当第一次使用jdbcUtils 这个类时候，静态代码块会被自动执行一次
    static {
          Properties properties=new Properties();
          try{
              InputStream is= Utils.class.getResourceAsStream("/druid.properties.properties");

                  properties.load(is);

                  //创建连接池

                  dataSource= DruidDataSourceFactory.createDataSource(properties);

              } catch (Exception ioException) {
              ioException.printStackTrace();
          }

    }




    //返回连接池对象ds
    public  static DataSource getDataSource(){
        //将工具类产生的连接池对象返回给调用者
        return dataSource;
    }

    //返回连接对象
    public static Connection getConnection() throws SQLException {
        //将连接池的一个连接对象借给调用者

            return  dataSource.getConnection();

    }

    //关闭对象(归还对象 rs,pstmt,cn)
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
