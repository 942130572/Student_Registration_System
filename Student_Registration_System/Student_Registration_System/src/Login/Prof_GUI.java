/*
 * @author �׺ƽ�
 * @version 1.0
 * ���ڿͻ��˴���
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

public class Prof_GUI extends JFrame implements ActionListener{
	public int sys_width;
	public int sys_height;
	public int windowsWidth;
	public int windowsHeight;
	
	public String user_id;//�����û��������̹���
	public String password;//��������
	
	private JButton jb1;//�����������ڵ�����������ѡ��ִ�̿γ����ύ�ɼ�
	private JButton jb2;
	private JPanel jp;
	private JLabel title;
	
	//�����������ͨ�ŵ�socket�����ڹ��캯���г�ʼ��
	public Socket socket;
	public DataInputStream dis;
	public DataOutputStream dos;
	
	public Prof_GUI(String name, String pw,Socket socket) {
		this.user_id = name;
		this.password = pw;
		this.socket=socket;
		try {
			this.dis = new DataInputStream(
			        new BufferedInputStream(socket.getInputStream()));
			this.dos = new DataOutputStream(
	                new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		this.setTitle("����ѡ��ϵͳ");
		
		//���ô��ڴ�С
		sys_width = Toolkit.getDefaultToolkit().getScreenSize().width;
	    sys_height = Toolkit.getDefaultToolkit().getScreenSize().height;
	    windowsWidth = 350;
	    windowsHeight = 350;
	    this.setSize(windowsWidth,windowsHeight);
	    this.setBounds((sys_width- windowsWidth) / 2,
                (sys_height - windowsHeight) / 2, windowsWidth, windowsHeight);
		
	    //����
	    this.setLayout(new GridLayout(2,1));
	    
	    //��ӭ��
	    title = new JLabel("��ӭʹ��ѡ��ϵͳ��",JLabel.CENTER);
	    this.add(title);
	    
	    //��������
	    jb1 = new JButton("ѡ��ִ�̿γ�");
	    jb2 = new JButton("�ύ�ɼ�");
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jp = new JPanel();
	    jp.setLayout(new GridLayout(1,2));
	    jp.add(jb1);
	    jp.add(jb2);
	    this.add(jp);
	    
	    this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1) {  //ѡ��ִ�̿γ̹���
			//���䣺ִ��ѡ��ִ�̿γ�����
		}else if(e.getSource()==jb2) {  //�ύ�ɼ�
			//���䣺ִ���ύ�ɼ�����
		}
	}
}
