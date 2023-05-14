package common;

public interface MessageType {
    //接口定义一些常量
    //不同常量的值，表示不同消息类型
    String MESSAGE_LOGIN_SUCCEED="1"; //表示登录成功
    String MESSAGE_FAIL="2"; //表示登录失败
    String MESSAGE_COMM_MES="3";//普通信息包
    String MESSAGE_GET_ONLINE_FRIEND="4"; //要求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND="5"; //返回在线用户列表
    String MESSAGE_CLIENT_EXIT="6";//客户端请求退出
    String MESSAGE_TO_ALL_MES="7"; //群发消息
    String MESSAGE_FILE_MES="8"; //发送文件
    String REGISTER="9"; //注册
    String Count="10"; //登入
    String MESSAGE_ADD_FRIEND="11"; //添加好友
    String MESSAGE_ACCEPT_ADD_FRIEND="12"; //接收添加好友
    String MESSAGE_REFUSE_ADD_FRIEND="13"; //拒绝添加好友
    String MESSAGE_SEARCH="14"; //搜索好友
    String REGISTER_SUCCEED="15"; //注册成功
    String REGISTER_FAIL="16";    // 注册失败
}
