/*
 * @author �׺ƽ�
 * @version 1.0
 * ע��Ա�ͻ��˴���
 */
package Login;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Registrar_GUI extends JFrame implements ActionListener{
	private int sys_width;
	private int sys_height;
	private int windowsWidth;
	private int windowsHeight;
	
	private JButton jb1;//����ע��Ա��4�����ܣ�ά��ѧ��/������Ϣ����/�ر�ע��
	private JButton jb2;
	private JButton jb3;
	private JButton jb4;
	private JPanel jp;
	private JLabel title;
	
	//�����������ͨ�ŵ�socket�����ڹ��캯���г�ʼ��
	public Socket socket;
	public DataInputStream dis;
	public DataOutputStream dos;
	
	public Registrar_GUI(Socket socket) {
		this.socket = socket;
		try {
			this.dis = new DataInputStream(
			        new BufferedInputStream(socket.getInputStream()));
			this.dos = new DataOutputStream(
	                new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		this.setTitle("ע��Ա�������");
		
		//���ô��ڴ�С
		sys_width = Toolkit.getDefaultToolkit().getScreenSize().width;
	    sys_height = Toolkit.getDefaultToolkit().getScreenSize().height;
	    windowsWidth = 350;
	    windowsHeight = 350;
	    this.setSize(windowsWidth,windowsHeight);
	    this.setBounds((sys_width- windowsWidth) / 2,
                (sys_height - windowsHeight) / 2, windowsWidth, windowsHeight);
		
	    //����
	    this.setLayout(new GridLayout(4,1));
	    
	    //��ӭ��
	    title = new JLabel("��ӭ������ע��Ա",JLabel.CENTER);
	    this.add(title);
	    
	    //������ر�ע��
	    jb1 = new JButton("����ע��");
	    jb2 = new JButton("�ر�ע��");
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jp = new JPanel();
	    jp.setLayout(new GridLayout(1,2));
	    jp.add(jb1);
	    jp.add(jb2);
	    this.add(jp);
	    
	    //ά��ѧ����Ϣ
	    jb3 = new JButton("ά��ѧ����Ϣ");
	    jb3.addActionListener(this);
	    this.add(jb3);
	    
	    //ά����ʦ��Ϣ
	    jb4 = new JButton("ά��������Ϣ");
	    jb4.addActionListener(this);
	    this.add(jb4);
	    
	    
	    this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1) {  //����ע�ᰴť
			//���䣺�����������������������״̬��Ϊ����ע���״̬
		}else if(e.getSource()==jb2){  //�ر�ע�ᰴť
			//���䣺ִ�йر�ע�������Ĺ��ܣ��ص��д
		}else if (e.getSource()==jb3) {  //ά��ѧ����Ϣ
			//���䣺ִ��ά��ѧ����Ϣ����
		}else if(e.getSource()==jb4) {   //ά��������Ϣ
			//���䣺ִ��ά��������Ϣ����
		}
	}
	
}
