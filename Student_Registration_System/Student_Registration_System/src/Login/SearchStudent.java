package Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;


public class SearchStudent extends JFrame{
	private Vector<String> idd=new Vector<String>();
	private Map<String,String> mmp=new HashMap<String,String>();
	private JButton jb1;
	private JTextField tf;
	private JPanel con;
	private JScrollPane sp1 ;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	public SearchStudent(Socket socket)  {
		// TODO Auto-generated constructor stub
		super("������Ϣ��ѯ");
		this.socket=socket;
		try {
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

      
		
		setmmp();
		con=new JPanel();
		setBounds(150, 150, 1150, 500);//
		con.setLayout(null);
		
		 sp1 = new JScrollPane(con);
		this.add(sp1);
		//con=new JPanel();
		//setLayout(null);
		tf=new JTextField();
		tf.setBounds(470, 40, 120, 25);
		jb1=new JButton("ȷ��");
		jb1.setBounds(600, 40, 60, 25);
		JButton jb2=new JButton("ȡ��");
		jb2.setBounds(670, 40, 60, 25);
		con.add(jb1);
		con.add(jb2);
		con.add(tf);
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				MaintainStudent mp=new MaintainStudent(socket);
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setVisible(true);
	}
	public void searchType(String tp) {
		SearchStudent fs=this;
		JLabel jl=new JLabel("������Ҫ��ѯ��"+tp);
		jl.setBounds(300, 40, 180, 30);
		con.add(jl);
		JLabel jl3=new JLabel("���");
		JLabel jl4=new JLabel("sid");
		JLabel jl5=new JLabel("����");
		JLabel jl6=new JLabel("����");
		JLabel jl7=new JLabel("��������");
		JLabel jl8=new JLabel("SSN");
		JLabel jl9=new JLabel("�꼶");
		//JLabel jl10=new JLabel("Ժϵ");
	
	
		jl3.setBounds(35, 90, 60, 25);
		jl4.setBounds(135, 90, 60, 25);
		jl5.setBounds(235, 90, 60, 25);
		jl6.setBounds(365, 90, 60, 25);	
		jl7.setBounds(465, 90, 60, 25);
		jl8.setBounds(565, 90, 60, 25);
		jl9.setBounds(715, 90, 60, 25);	
		//jl10.setBounds(815, 90, 60, 25);
		con.add(jl3);
		con.add(jl4);
		con.add(jl5);
		con.add(jl6);
		con.add(jl7);
		con.add(jl8);
		con.add(jl9);
	//	con.add(jl10);
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					String sql="select * from Student where "+mmp.get(tp)+"='"+tf.getText().trim()+"'";// ('"+pid+"','"+name+"','"+birthday+"','"+ssn+"','"+depart+"','"+password+"','"+status+"')";   //SQL���
					try {
						dos.writeUTF("341"+"#"+sql);
						dos.flush();
						String data=dis.readUTF();
						String da[];
						da=data.split("#");
						int i=0;//i�ǵ�ǰ��Ϣ����ţ�����ʵ��ɾ���޸ĵĶ�λ
						int j=0;//j��Ϊ�˵õ������������ݣ���Ϊ����#�ֿ�������					 
						while(!da[j].equals(" ")){
					    	    final int k=i;
					           // ͨ���ֶμ���
					            String sid  = da[j++];
					       		String password=da[j++];
					            String name = da[j++];
					            String birthday= da[j++];			  				    
								String ssn= da[j++];
								String status=da[j++];								
									JLabel jl11=new JLabel(i+"");
									JLabel l1=new JLabel(sid);
									JLabel l2=new JLabel(password);
									JLabel l3=new JLabel(name);
									JLabel l4=new JLabel(birthday);
									JLabel l5=new JLabel(ssn);
									JLabel l6=new JLabel(status);
							//		JLabel l7=new JLabel(depart);
									con.add(jl11);
									//con.add(jl10);
									con.add(l1);
									con.add(l2);
									con.add(l3);
									con.add(l4);
									con.add(l5);
									con.add(l6);
								//	con.add(l7);
									jl11.setBounds(35, 120+70*i, 60, 25);//����λ������Ϣ���������ӱ仯
									l1.setBounds(135, 120+70*i, 60, 25);
									l2.setBounds(235, 120+70*i, 60, 25);	
									l3.setBounds(365, 120+70*i, 60, 25);
									l4.setBounds(465, 120+70*i, 60, 25);
									l5.setBounds(565, 120+70*i, 60, 25);
									l6.setBounds(715, 120+70*i, 60, 25);
								//	l7.setBounds(815, 120+70*i, 60, 25);
									JButton jb1=new JButton("ɾ��");//�Դ�����Ϣɾ��
									JButton jb2=new JButton("�޸�");//�Դ�����Ϣ�޸�
									con.add(jb1);
									jb1.setBounds(945, 120+70*i, 60, 25);
									con.add(jb2);
									jb2.setBounds(1015, 120+70*i, 60, 25);
									idd.add(sid);
									jb1.addActionListener(new ActionListener() {
										//ɾ����ť�����¼�����
										@Override
										public void actionPerformed(ActionEvent e) {
											// TODO Auto-generated method stub
											int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����?", "��Ϣ��", JOptionPane.YES_NO_OPTION);
											if (n == JOptionPane.YES_OPTION) {
												Search_Del rg;
												try {
													rg = new Search_Del(socket);
													rg.delS(k,idd);
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												//ɾ������
												dispose();
					
											}
										}
									});
									jb2.addActionListener(new ActionListener() {
										//�޸İ�ť�����¼�����
										@Override
										public void actionPerformed(ActionEvent e) {
											// TODO Auto-generated method stub
											Search_Del rg;
											try {
												rg = new Search_Del(socket);
												rg.modifyS(k,idd);
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											//�޸ĺ���
											dispose();
											
										}
									});	
									i++;
						       }
					con.setPreferredSize(new Dimension(1100,150+70*i));				
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
		});
	}
	public void setmmp()
	{
		mmp.put("sid", "sid");
		mmp.put("����", "name");
		mmp.put("��������", "birthday");
		mmp.put("SSN", "SSN");
		mmp.put("ְ��", "status");
	//	mmp.put("Ժϵ", "department");
		
	}
	
}