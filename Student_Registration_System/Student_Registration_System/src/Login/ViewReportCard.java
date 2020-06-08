
/*
 * ѧ���鿴�ɼ�����
 * ʹ��socket����ͷ������ϵ�ѧ���ຯ��ͨ��
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewReportCard {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// ViewReportCardUI ui = new ViewReportCardUI();
	}
}

class ViewReportCardUI extends JFrame implements ActionListener {
	int width = 900;
	int height = 700;
	private JLabel label1, label2, label3;
	private JComboBox cmb1;
	private JScrollPane sp1;
	private JTable table1;
	private JButton button1, button2;
	private String semester = "-----��ѡ��-----"; // ѧ��ѡ�в�ѯ��ѧ��
	private Vector<Vector> rowData;
	Vector columnNames;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	String user_id;
	String password;

	public ViewReportCardUI(String id, String pw, Socket socket) throws UnknownHostException, IOException {
		// TODO Auto-generated constructor stub
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
		label1 = new JLabel("�ɼ���ѯ");
		label2 = new JLabel("ѧ�ڣ�");
		label3 = new JLabel("ѧ�ڳɼ�");
		label1.setBounds(30, -30, 200, 150);
		label2.setBounds(60, 80, 80, 50);
		label3.setBounds(width / 2 - 75, 140, 160, 50);
		Font font1 = new Font("����", Font.PLAIN, 30);
		Font font2 = new Font("����", Font.PLAIN, 20);
		Font font3 = new Font("΢���ź�", Font.PLAIN, 16);
		Font font4 = new Font("����", Font.PLAIN, 23);
		label1.setFont(font1);
		label2.setFont(font2);
		label3.setFont(font4);
		button1 = new JButton("����");
		button1.setBounds(width - 150, 40, 100, 40);
		button1.setFont(font2);
		button1.setForeground(Color.white);
		button1.setBackground(new Color(74, 112, 139));
		button2 = new JButton("��ѯ");
		button2.setBounds(330, 85, 80, 40);
		button2.setFont(font3);
		cmb1 = new JComboBox();
		cmb1.addItem("-----��ѡ��-----");
		cmb1.setBounds(130, 85, 170, 40);
		cmb1.setFont(font3);

		Vector<String> semester = new Vector<String>();
		semester.add("2020��ڶ�ѧ��");
		semester.add("2020���һѧ��");
		semester.add("2019��ڶ�ѧ��");
		semester.add("2019���һѧ��");
		for (int i = 0; i < semester.size(); i++)
			cmb1.addItem(semester.get(i));

		rowData = new Vector<Vector>();
		columnNames = new Vector();
		columnNames.add("ѧ��");
		columnNames.add("ѡ�οκ�");
		columnNames.add("�γ�����");
		columnNames.add("ѧ��");
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
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(button1);
		this.add(button2);
		this.add(cmb1);
		this.add(sp1, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			// ����ת��һ�����棡��
			Stu_GUI ui = new Stu_GUI(user_id, password, socket);
			dispose();
		} else if (e.getSource() == button2) {
			semester = cmb1.getSelectedItem().toString();
			rowData.clear();
			try {
				dos.writeUTF("18");// ��Ӧ������ѧ����ViewGrades()
				dos.writeUTF(semester);
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
			DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
			table1.setModel(model);

		}
	}
}