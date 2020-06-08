
/*
 * �����ύ�ɼ�����
 * ʹ��socket����ͷ������ϵ�Professor�ຯ��ͨ��
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SubmitGrades {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// SubmitGradesUI ui = new SubmitGradesUI();
	}
}

class SubmitGradesUI extends JFrame implements ActionListener {
	int width = 900;
	int height = 700;
	private JLabel label1, label2, label3, label4;
	private JComboBox cmb1, cmb2;
	private JScrollPane sp1;
	private JTable table1;
	private JButton button1, button2, button3;
	private Vector<Vector> rowData;// �ɼ�������
	private Vector columnNames; // �ɼ���ͷ
	private String semester = "-----��ѡ��-----";// ����ѡ�е�ѧ��
	private String course = "-----��ѡ��-----"; // ����ѡ�еĿγ�
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	String user_id;
	String password;

	public SubmitGradesUI(String id, String pw, Socket socket) throws UnknownHostException, IOException {
		// TODO Auto-generated constructor
		super("Submit Grades");
		/*
		 * int port = 8888; String host = "localhost"; socket = new Socket(host, port);
		 */
		this.user_id = id;
		this.password = pw;
		this.socket = socket;
		dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		dos = new DataOutputStream(new DataOutputStream(socket.getOutputStream()));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocation(0, 0);
		this.setLocationRelativeTo(null);
		setLayout(null);
		label1 = new JLabel("�ɼ��ύ");
		label2 = new JLabel("ѧ�ڣ�");
		label3 = new JLabel("�γ̣�");
		label4 = new JLabel("ѧ���ɼ�");
		label1.setBounds(30, -30, 200, 150);
		label2.setBounds(50, 80, 80, 50);
		label3.setBounds(300, 80, 80, 50);
		label4.setBounds(width / 2 - 75, 140, 160, 50);
		Font font1 = new Font("����", Font.PLAIN, 30);
		Font font2 = new Font("����", Font.PLAIN, 20);
		Font font3 = new Font("΢���ź�", Font.PLAIN, 16);
		Font font4 = new Font("����", Font.PLAIN, 23);
		label1.setFont(font1);
		label2.setFont(font2);
		label3.setFont(font2);
		label4.setFont(font4);
		button1 = new JButton("�ύ");
		button1.setBounds(width - 150, 45, 100, 40);
		button1.setFont(font2);
		button1.setForeground(Color.white);
		button1.setBackground(new Color(178, 34, 34));
		button2 = new JButton("����");
		button2.setBounds(width - 150, 100, 100, 40);
		button2.setFont(font2);
		button2.setForeground(Color.white);
		button2.setBackground(new Color(74, 112, 139));
		button3 = new JButton("��ѯ");
		button3.setBounds(560, 85, 80, 40);
		button3.setFont(font3);
		cmb1 = new JComboBox();
		cmb1.addItem("-----��ѡ��-----");
		cmb1.setBounds(110, 85, 170, 40);
		cmb1.setFont(font3);
		// ��ȡ�ý������ڿγ�ѧ����Ϣ,ʵ��Ӧ�����ݿ���ʣ���
		Vector<String> semester = new Vector<String>();
		semester.add("2020��ڶ�ѧ��");
		semester.add("2020���һѧ��");
		semester.add("2019��ڶ�ѧ��");
		semester.add("2019���һѧ��");
		for (int i = 0; i < semester.size(); i++)
			cmb1.addItem(semester.get(i));
		cmb2 = new JComboBox();
		cmb2.addItem("-----��ѡ��-----");
		cmb2.setBounds(360, 85, 170, 40);
		cmb2.setFont(font3);

		rowData = new Vector<Vector>();
		columnNames = new Vector<String>();
		columnNames.add("ѧ��");
		columnNames.add("ѧ������");
		columnNames.add("ѧ��");
		columnNames.add("�γ�����");
		columnNames.add("�ɼ�");

		table1 = new JTable(rowData, columnNames); // ����ָ�����������ݵı��
		sp1 = new JScrollPane(table1); // ������ʾ���Ĺ������
		sp1.setBounds(60, 195, width - 140, height - 260);
		JTableHeader tableHeader = table1.getTableHeader();
		tableHeader.setFont(font2);
		table1.setRowHeight(30);
		table1.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// ����table���ݾ���
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table1.setDefaultRenderer(Object.class, tcr);

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		cmb1.addActionListener(this);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(cmb1);
		this.add(cmb2);
		this.add(sp1, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) // �ύ���ݿ�
		{
			try {
				dos.writeUTF("27"); // ��Ӧ������SumbitGrades()
				for (int i = 0; i < rowData.size(); i++) {
					dos.writeUTF((String) rowData.get(i).get(0)); // ѧ��
					dos.writeUTF((String) rowData.get(i).get(3)); // �γ�����
					dos.writeUTF((String) rowData.get(i).get(4)); // �ɼ�
					dos.flush();
				}
				dos.writeUTF("end");
				dos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == button2) {
			// �ȷ��ص���һ�����棡��

			Prof_GUI ui = new Prof_GUI(user_id, password, socket);
			dispose();
		} else if (e.getSource() == button3) {
			course = cmb2.getSelectedItem().toString();
			rowData.clear();
			// �������ݿ���ѧ����Ϣ����
			try {
				dos.writeUTF("26");// ��Ӧ������������GetGrades()
				dos.writeUTF(semester);
				dos.writeUTF(course);
				dos.flush();
				String str = dis.readUTF();
				while (!str.equals("end")) {
					Vector tmp = new Vector();
					for (int i = 0; i < 5; i++) {
						tmp.add(str);
						str = dis.readUTF();
					}
					rowData.add(tmp);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��̬����table�������
			DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
			table1.setModel(model);
		} else if (e.getSource() == cmb1) {
			// ͨ��ѡ����semester�����ݻ�ȡ�ý��ڵĿγ̣���
			semester = cmb1.getSelectedItem().toString();
			Vector<String> course = new Vector<String>();
			course.add("-----��ѡ��-----");
			try {
				dos.writeUTF("25");// ��Ӧ����˽�����GetCourse()
				dos.writeUTF(semester);
				dos.flush();
				String str = dis.readUTF();
				while (!str.equals("end")) {
					course.add(str);
					str = dis.readUTF();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��̬����ComboBox�������
			ComboBoxModel model = new DefaultComboBoxModel(course);
			cmb2.setModel(model);
		}
	}

}