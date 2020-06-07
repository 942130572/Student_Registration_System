/*
 *@author �׺ƽ�
 *@version 1.0
 * ��¼����,����������׽�����������ݣ�����ѧ��ע��Ա����¼���������
 * */
package Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
public class Login {
	public static void main(String[] args) {
		Main_Login_GUI MLG = new Main_Login_GUI();
	}
}

class Main_Login_GUI extends JFrame implements ActionListener
{	
	//GUI�����������
	public int sys_width;//����ߴ�
	public int sys_height;
	public int windowsWidth;//���ڳߴ�
	public int windowsHeight;
	public String name;//�洢�û���������
	public String pw;
	public JLabel title;//��ӭ����
	public JComboBox<String> jc;//ѡ�����
	public JPanel jp;
	public JPanel jp2;
	public JPanel jp3;
	public JTextField nameField;//�����û���
	public JPasswordField pwField;//��������
	public JLabel nameLabel;
	public JLabel pwLabel;
	public JButton jb1;
	public JButton jb2;
	
	//�������������
	public Socket socket;
	public DataInputStream dis;
	public DataOutputStream dos;
	
	public Main_Login_GUI() {
		jp = new JPanel();
		jp2 = new JPanel();
		jp3 =  new JPanel();
		//GUI����
		this.setTitle("ѧ��ѡ�ι���ϵͳ");
		
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
	    title = new JLabel("��ӭʹ��ѧ��ѡ�ι���ϵͳ",JLabel.CENTER);
	    this.add(title);
	    
		
	    //�����¼��Ϣ
	    jp2.setLayout(new GridLayout(2,2,10,5));
	    nameLabel = new JLabel("�û���:",JLabel.CENTER);
	    nameField = new JTextField();
	    pwLabel = new JLabel("����:",JLabel.CENTER);
	    pwField = new JPasswordField();
	    jp2.add(nameLabel);
	    jp2.add(nameField);
	    jp2.add(pwLabel);
	    jp2.add(pwField);
	    this.add(jp2);
	    
	    //ѡ�����
	    jc = new JComboBox<String>();
	    jc.addItem("---��ѡ���������---");
	    jc.addItem("ע��Ա");
	    jc.addItem("ѧ��");
	    jc.addItem("����");
	    jp.add(jc);
	    this.add(jp);
	    
	    //ȷ����ȡ����ť
	    jb1 = new JButton("��¼");
	    jb2 = new JButton("ȡ��");
	    jb1.addActionListener(this);
	    jb2.addActionListener(this);
	    jp3.setLayout(new GridLayout(1,2));
	    jp3.add(jb1);
	    jp3.add(jb2);
	    this.add(jp3);
	    
	    this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//�����ͻ���socket�������˽��й�����
		try {
			socket = new Socket("127.0.0.1",8888);
			dis = new DataInputStream(
	                new BufferedInputStream(socket.getInputStream()));
	        dos = new DataOutputStream(
	                new BufferedOutputStream(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		/*
		 * �¼�������������ֻ��һ���¼�����¼
		 */
		String buttonName = e.getActionCommand();
		if(buttonName.equals("��¼")) {
			//�����������ݡ��û���������
			name = new String(nameField.getText());
			pw = new String(pwField.getPassword());
			int temp = jc.getSelectedIndex();
			if(temp==1) {//��ǰ��ע��Ա
				this.dispose();
				Registrar_GUI rGui = new Registrar_GUI(socket);
			}else if (temp==2) {//��ǰ��ѧ��
				if(isCorrect(temp)) {
					this.dispose();
					Stu_GUI stu_GUI = new Stu_GUI(name,pw,socket);
				}else {
					JOptionPane.showMessageDialog(null, "������û����������������������룡", "����",JOptionPane.ERROR_MESSAGE);
				}
			}else if (temp==3) {//��ǰ�ǽ���
				if(isCorrect(temp)) {
					this.dispose();
					Prof_GUI prof_GUI = new Prof_GUI(name,pw,socket);
				}else {
					JOptionPane.showMessageDialog(null, "������û����������������������룡", "����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(buttonName.equals("ȡ��")){
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}

	public boolean isCorrect(int id) {  
		/*
		 * �����жϵ��øú�������ʲô��ݣ�ѧ��������ʦ
		 * ���û���name������pw���͸��ͻ�����֤�����Ƿ���ȷ
		 */
		String idString=new String();
		String flag="0";//�øñ������淵�صĽ�������û���������ȷ����Ϊ1������Ϊ0
		if(id==2) {
			idString = "10";//1����ѧ����0����ִ�е�¼����
		}else if(id==3){
			idString = "20";//2������ڣ�0����ִ�е�¼����
		}
		
		try {			
			dos.writeUTF(idString);//�ȷ��������룬���߷����ִ����Ӧ����
			dos.writeUTF(name);//��ѧ�������뷢��������
			dos.writeUTF(pw);
			dos.flush();
			flag = dis.readUTF();
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		if(flag.equals("1")) return true;
		else return false;
	}

}









