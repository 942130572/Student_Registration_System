package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Professor {
	String id;
	String password;
	String name;
	String birthday;
	String SSN;
	String status;
	String department;
	
	private Connection conn;
	private PreparedStatement pst;
	private ResultSet rs;
	
	Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
	public Professor(Socket socket) {
		id = null;
		password=null;
		name=null;
		birthday=null;
		SSN=null;
		status=null;
		department=null;
		try {
			this.dis = new DataInputStream(
			        new BufferedInputStream(socket.getInputStream()));
			this.dos = new DataOutputStream(
	                new BufferedOutputStream(socket.getOutputStream()));//�����
			conn = Database.getNewConnection();
		} catch (SQLException | IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public String login(String id,String pw) throws SQLException {
		/*
		 * �����������յ����ڿͻ��˷����ĵ�¼����󣬴������ڶ���ִ�д˷���
		 * 1.�������ݿ⣬��id��pw���м�����������������ƥ�䣬��������0
		 * 2.��ƥ�䣬��������ݿ�ý��ڵ���Ϣ����ŵ��������У�������1��ʾ��¼�ɹ���
		 */
		//���䣺�����ݿ���м���
		
		String sql;
		sql = "select pid,password from professor where pid=?";
		pst=conn.prepareStatement(sql);
		pst.setString(1, id);
		rs = pst.executeQuery();
		rs.next();
		String testidString=rs.getString("pid");
		String testpwString=rs.getString("password");
		boolean flag=false;
		if(id.equals(testidString)&&pw.equals(testpwString)) {flag=true;}
		else {flag=false;}
		if(flag) {
			return "1";
		}else {
			return "0";
		}
	}
	
	public void close() {
		try {
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
}
