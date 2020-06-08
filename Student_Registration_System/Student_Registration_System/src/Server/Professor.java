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
		password = null;
		name = null;
		birthday = null;
		SSN = null;
		status = null;
		department = null;
		try {
			this.dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			this.dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));// �����
			conn = Database.getNewConnection();
		} catch (SQLException | IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public String login(String id, String pw) throws SQLException {
		/*
		 * �����������յ����ڿͻ��˷����ĵ�¼����󣬴������ڶ���ִ�д˷��� 1.�������ݿ⣬��id��pw���м�����������������ƥ�䣬��������0
		 * 2.��ƥ�䣬��������ݿ�ý��ڵ���Ϣ����ŵ��������У�������1��ʾ��¼�ɹ���
		 */
		// ���䣺�����ݿ���м���

		this.id = id;
		this.password = pw;
		String sql;
		sql = "select pid,password from professor where pid=?";
		pst = conn.prepareStatement(sql);
		pst.setString(1, id);
		rs = pst.executeQuery();
		rs.next();
		String testidString = rs.getString("pid");
		String testpwString = rs.getString("password");
		boolean flag = false;
		if (id.equals(testidString) && pw.equals(testpwString)) {
			flag = true;
		} else {
			flag = false;
		}
		if (flag) {
			return "1";
		} else {
			return "0";
		}
	}

	public void GetCourse() throws IOException { // ��ȡָ��ѧ�ڵĿγ�
		System.out.println("GetCourse-------------------------------");
		String semester = dis.readUTF();
		try {
			Connection conn = Database.getNewConnection();
			String sql;
			PreparedStatement pst;
			if (semester.equals("-----��ѡ��-----")) { // û��ָ��ѧ��ʱ��Ĭ��ȫ��ѧ��
				sql = "select distinct cname " + "from grade " + "where pid = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, id);
			} else {
				sql = "select distinct cname " + "from grade " + "where semester= ? " + "and pid = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, semester);
				pst.setString(2, id);
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				dos.writeUTF(rs.getString("cname"));
				// dos.flush();
			}
			dos.writeUTF("end");
			dos.flush();
			Database.freeDB(rs, pst, conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void GetGrades() throws IOException { // ��ȡָ��ѧ�ڡ�ָ���γ̵�ѧ���ɼ�
		System.out.println("GetGrades-------------------------------");
		String semester = dis.readUTF();
		String course = dis.readUTF();
		try {
			Connection conn = Database.getNewConnection();
			String sql;
			PreparedStatement pst;
			if (course.equals("-----��ѡ��-----")) {
				if (semester.equals("-----��ѡ��-----")) {// û��ָ��ѧ�ںͿγ̣�Ĭ��ȫ��
					sql = "select grade.sid,name,semester,cname,grade "
							+ "from grade join student on grade.sid=student.sid " + "where pid = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, id);
				} else {// ָֻ����ѧ��
					sql = "select grade.sid,name,semester,cname,grade "
							+ "from grade join student on grade.sid=student.sid " + "where semester = ? and pid = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, semester);
					pst.setString(2, id);
				}
			} else {// ָ����ѧ�ںͿγ�
				sql = "select grade.sid,name,semester,cname,grade "
						+ "from grade join student on grade.sid=student.sid "
						+ "where semester = ? and cname = ? and pid = ? ";
				pst = conn.prepareStatement(sql);
				pst.setString(1, semester);
				pst.setString(2, course);
				pst.setString(3, id);
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				dos.writeUTF(rs.getString("sid"));
				dos.writeUTF(rs.getString("name"));
				dos.writeUTF(rs.getString("semester"));
				dos.writeUTF(rs.getString("cname"));
				dos.writeUTF(String.valueOf(rs.getInt("grade")));
				dos.flush();
			}
			dos.writeUTF("end");
			dos.flush();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void SubmitGrades() throws IOException, SQLException { // �ύѧ���ɼ�
		System.out.println("Submit-------------------------------");
		String str = dis.readUTF();
		Connection conn = Database.getNewConnection();
		PreparedStatement pst = null;
		int rs = 0;
		String sql;
		while (!str.equals("end")) {
			String id = str;
			str = dis.readUTF();
			String course = str;
			str = dis.readUTF();
			int grade = Integer.valueOf(str);
			str = dis.readUTF();
			sql = "Update grade set grade= ? where sid = ? and cname = ? ";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, grade);
			pst.setString(2, id);
			pst.setString(3, course);
			rs = pst.executeUpdate();
		}
		Database.freeDB(null, pst, conn);
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
