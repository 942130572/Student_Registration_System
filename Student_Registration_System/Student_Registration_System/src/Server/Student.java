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
import java.sql.Statement;
import java.util.ArrayList;

public class Student {
	String id;
	String password;
	String name;
	String birthday;
	String SSN;
	String status;
	String graduation_date;
	Schdule schdule;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
	public Student(Socket socket) {
		id = null;
		password=null;
		name=null;
		birthday=null;
		SSN=null;
		status=null;
		graduation_date=null;
		this.socket=socket;
    	try {
			this.dis = new DataInputStream(
			        new BufferedInputStream(socket.getInputStream()));
			this.dos = new DataOutputStream(
	                new BufferedOutputStream(socket.getOutputStream()));//�����
			this.conn = Database.getNewConnection();
		} catch (IOException | SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public String login(String id,String pw) throws SQLException {
		/*
		 * �����������յ�ѧ���ͻ��˷����ĵ�¼����󣬴���ѧ������ִ�д˷���
		 * 1.�������ݿ⣬��id��pw���м�����������������ƥ�䣬��������0
		 * 2.��ƥ�䣬��������ݿ��ѧ������Ϣ����ŵ��������У�������1��ʾ��¼�ɹ���
		 */
		//���䣺�����ݿ���м���
		String sql;
		sql = "select sid,password from student where sid=?";
		pst=conn.prepareStatement(sql);
		pst.setString(1, id);
		rs = pst.executeQuery();
		rs.next();
		String testidString=rs.getString("sid");
		String testpwString=rs.getString("password");
		boolean flag=false;//�����ã�֮��ɾ��
		if(id.equals(testidString)&&pw.equals(testpwString)) {flag=true;}
		else {flag=false;}
		if(flag) {
			return "1";
		}else {
			return "0";
		}
	}
	
	public void Register_for_Courses() {
		
	}
	public void View_Report_Card() {
		
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



class Schdule{
	String stu_id;
	ArrayList<String>main_lesson;
	ArrayList<String>alternate_lesson;
	int status;//��ǰ�α���ύ״̬��0δ�ύ��1�ѱ��棬2���ύ
	public Schdule() {
		stu_id=null;
		main_lesson=new ArrayList<String>(4);;
		alternate_lesson=new ArrayList<String>(2);;
		status=0;
	}
	public void edit_id(String id) {stu_id=id;}
	public void edit_status(int s) {status=s;}
	public void edit_main_lesson(int index, String lesson_id) {
		main_lesson.add(index, lesson_id);
	}
	public void edit_alternate_lesson(int index, String lesson_id) {
		alternate_lesson.add(index, lesson_id);
	}
	
}
