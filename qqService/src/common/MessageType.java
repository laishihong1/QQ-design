package common;

public interface MessageType {
    //�ӿڶ���һЩ����
    //��ͬ������ֵ����ʾ��ͬ��Ϣ����
    String MESSAGE_LOGIN_SUCCEED="1"; //��ʾ��¼�ɹ�
    String MESSAGE_FAIL="2"; //��ʾ��¼ʧ��
    String MESSAGE_COMM_MES="3";//��ͨ��Ϣ��
    String MESSAGE_GET_ONLINE_FRIEND="4"; //Ҫ�󷵻������û��б�
    String MESSAGE_RET_ONLINE_FRIEND="5"; //���������û��б�
    String MESSAGE_CLIENT_EXIT="6";//�ͻ��������˳�
    String MESSAGE_TO_ALL_MES="7"; //Ⱥ����Ϣ
    String MESSAGE_FILE_MES="8"; //�����ļ�
    String REGISTER="9"; //ע��
    String Count="10"; //����
    String MESSAGE_ADD_FRIEND="11"; //��Ӻ���
    String MESSAGE_ACCEPT_ADD_FRIEND="12"; //������Ӻ���
    String MESSAGE_REFUSE_ADD_FRIEND="13"; //�ܾ���Ӻ���
    String MESSAGE_SEARCH="14"; //��������
    String REGISTER_SUCCEED="15"; //ע��ɹ�
    String REGISTER_FAIL="16";    // ע��ʧ��
}
