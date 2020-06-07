/*
 * @author �׺ƽ�
 * @version 1.0
 * ������ʵ�ִ���
 *IP��ַ������IP��127.0.0.1�����˿ں�:8888
 *��serversocket������������ʹ��ExecutorService�ഴ���̳߳�
 * ÿ�յ�һ�����Կͻ��˵����ӣ�����һ���߳�
 */
package Server;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;







public class SRSServer {
	private static int port=8888;  //�������˿ں�
	public int clientNo;
	public ServerSocket serverSocket;
	public ExecutorService exec;
	public static int isRegistration_time;//��ǰϵͳ�Ƿ�������ע���״̬�£�1Ϊ����ע�ᣬ0Ϊ�ر�ע��
	public static int isRegistration;//��¼��ǰ�ж����˴���ѡ�ν���
	
	public static void main(String[] args) throws IOException {
		//����������serversocket����
		SRSServer srsServer = new SRSServer();
	}
	public SRSServer() throws IOException  {
		SRSServer.isRegistration=0;
		SRSServer.isRegistration_time=0;
        clientNo = 0;
        try {
			serverSocket = new ServerSocket(port);//����һ��������
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        // �����̳߳�
        exec = Executors.newCachedThreadPool();
        while (true) {
            // ѭ���������Կͻ��˵��������󣬲����뵽�̳߳���
            Socket client = serverSocket.accept();
            clientNo++;
            exec.execute(new SingleServer(client, clientNo));
        }
	}
	
}



class SingleServer implements Runnable {
	/*����server���ӵ�ÿһ���ͻ��ˣ�����һ���̴߳����ö���ͨ���ö���ִ����Ե�ǰ�ͻ��˵�һ�в���
	 * */
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isEnd;
    private int clientNo;
    private int idendity;//ʶ���¼�Ŀͻ��˵����ͣ�1Ϊѧ����2Ϊ���ڣ�3Ϊ����Ա
    
    //�����û���������
    private String idString;
    private String pwString;
    
  //���ݿͻ�����ݵĲ�ͬʹ�ö�Ӧ����ִ�в���
    private Student student;
    private Professor professor;
    private Register register;
    
    private String request;//����ӿͻ��˷����������룬��ʽΪ�ܻ��Ե�Э���׼
    
    public SingleServer(Socket socket, int clientNo) {
        this.socket = socket;
        this.clientNo = clientNo;
        this.idendity=0;
        this.isEnd=false;
        this.request=null;
        this.student = new Student(socket);
        this.professor = new Professor(socket);
        this.register = new Register(socket);
    }
    @Override
    public void run() {
    	//������ͻ��˵�IO������
    	try {
			dis = new DataInputStream(
			        new BufferedInputStream(socket.getInputStream()));
			dos = new DataOutputStream(
	                new BufferedOutputStream(socket.getOutputStream()));//�����
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
    	do {
    		try {
                request=dis.readUTF();
                if((request.toCharArray())[0]=='1') {  //�ͻ������Ϊѧ��
                	idendity=1;
                	if(request.toCharArray()[1]=='0') {  //�����¼
                		idString = dis.readUTF();
                		pwString = dis.readUTF();
                		String result=student.login(idString, pwString);
                		dos.writeUTF(result);
                		dos.flush();
                	}
                	//���䣺�˴����else if���Ǹĳ�switch����������ѧ����ɫ����������
                }else if(request.toCharArray()[0]=='2') {//�ͻ�������ǽ���
                	idendity=2;
                	if(request.toCharArray()[1]=='0') {  //�����¼
                		String id = dis.readUTF();
                		String pw = dis.readUTF();
                		String result=professor.login(id, pw);
                		dos.writeUTF(result);
                		dos.flush();
                	}
                	//���䣺�˴����else if���Ǹĳ�switch���������ƽ��ڽ�ɫ����������
                }else if(request.toCharArray()[0]=='3') {//�ͻ��������ע��Ա
                	idendity=3;
                	//���䣺����ע��Ա��ɫ������
                }

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }finally {
            	try {
            		switch(idendity) {
            		case 1:student.close();break;
            		case 2:professor.close();break;
            		case 3:register.close();break;
            		default: break;
            		}
					socket.close();
					isEnd=true;
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
            }
    	}while(!isEnd);
    }
}


