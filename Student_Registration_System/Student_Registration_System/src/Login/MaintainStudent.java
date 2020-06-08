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
import java.sql.*;
import javax.swing.*;
import javax.xml.crypto.Data;

import java.util.*;
import javax.swing.*;
public class MaintainStudent extends JFrame {
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private Vector<String> idd=new Vector<String>();
	/*public static void main(String[] args) {
		MaintainStudent a=new MaintainStudent();
	}*/
	public MaintainStudent(Socket socket)   {
		super("ѧ����Ϣά��");
		this.socket=socket;
		try {
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

     
		
		JPanel con=new JPanel();
		setBounds(150, 150, 1150, 500);//
		//setLayout(null);
		con.setLayout(null);
		//con.setPreferredSize(new Dimension(1000,1000));
		JScrollPane sp1 = new JScrollPane(con);
		//sp1.setBounds(400,200,100,100);
		//sp1.setLayout(null);
		//this.getContentPane().add(sp1);
		this.add(sp1);
		
		//JLabel jl1=new JLabel("���̵���������"+shop.getLen()+"ֻ����");
		//con.add(jl1);
		//jl1.setBounds(0, 0, 160, 30);
		JComboBox jc=new JComboBox();//ѡ����ұ�׼��������
		jc.setEditable(false);//���ɱ༭
	    jc.setEnabled(true);
	    String[] arr ={"----���ұ�׼----"};
		ComboBoxModel cbm = new DefaultComboBoxModel(arr);//���������ʾ����ѡ
		jc.setModel(cbm);
		jc.addItem("sid");//���ѡ��
	    jc.addItem("����");
	    jc.addItem("��������");
	    jc.addItem("SSN");
	    jc.addItem("ְ��");
	    jc.addItem("�꼶");
	   
	    con.add(jc);
		jc.setBounds(420, 50, 110, 24);
		JButton jb=new JButton("����");//���Ұ�ť
		con.add(jb);
		jb.addActionListener(new ActionListener() {
			//�����Ұ�ť����¼�����
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(jc.getSelectedIndex()!=0)
				{
					SearchStudent sp;
					sp = new SearchStudent(socket);
					sp.searchType(jc.getSelectedItem().toString());
					
					dispose();
				}
				
				//shop.petSearch1(jc.getSelectedItem().toString());//�������Ϊ��׼
				//���ù���Ա�Ĳ��Һ���
			}
		});
		jb.setBounds(540, 50, 60, 25);
		JButton b=new JButton("���");
		con.add(b);
		b.setBounds(610, 50, 60, 25);
		JButton b2=new JButton("����");
		con.add(b2);
		b2.setBounds(1015, 50, 60, 25);
		JLabel jl1=new JLabel("���");
		JLabel jl3=new JLabel("sid");
		JLabel jl4=new JLabel("����");
		JLabel jl5=new JLabel("����");
		JLabel jl6=new JLabel("��������");
		JLabel jl7=new JLabel("SSN");
		JLabel jl8=new JLabel("�꼶");
		//JLabel jl9=new JLabel("Ժϵ");
		jl1.setBounds(35, 90, 60, 25);
		jl3.setBounds(135, 90, 60, 25);
		jl4.setBounds(235, 90, 60, 25);
		jl5.setBounds(365, 90, 60, 25);	
		jl6.setBounds(465, 90, 60, 25);
		jl7.setBounds(565, 90, 60, 25);
		jl8.setBounds(715, 90, 60, 25);	
		//jl9.setBounds(815, 90, 60, 25);
		con.add(jl3);
		con.add(jl4);
		con.add(jl5);
		con.add(jl6);
		con.add(jl7);
		con.add(jl8);
		String sql="select * from student";
		String data="";
		try {
			dos.writeUTF("341"+"#"+sql);
			dos.flush();
			data=dis.readUTF();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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
			JLabel jl10=new JLabel(i+"");
			JLabel l1=new JLabel(sid);
			JLabel l2=new JLabel(password);
			JLabel l3=new JLabel(name);
			JLabel l4=new JLabel(birthday);
			JLabel l5=new JLabel(ssn);
			JLabel l6=new JLabel(status);
			//JLabel l7=new JLabel(depart);
			con.add(jl1);
			con.add(jl10);
			con.add(l1);
			con.add(l2);
			con.add(l3);
			con.add(l4);
			con.add(l5);
			con.add(l6);
			//con.add(l7);
			jl10.setBounds(35, 120+70*i, 60, 25);//����λ������Ϣ���������ӱ仯
			l1.setBounds(135, 120+70*i, 60, 25);
			l2.setBounds(235, 120+70*i, 60, 25);	
			l3.setBounds(365, 120+70*i, 60, 25);
			l4.setBounds(465, 120+70*i, 60, 25);
			l5.setBounds(565, 120+70*i, 60, 25);
			l6.setBounds(715, 120+70*i, 60, 25);
			//l7.setBounds(815, 120+70*i, 60, 25);
			JButton jb1=new JButton("ɾ��");//�Դ�����Ϣɾ��
			JButton jb2=new JButton("�޸�");//�Դ�����Ϣ�޸�
			con.add(jb1);
			jb1.setBounds(945, 120+70*i, 60, 25);
			con.add(jb2);
			jb2.setBounds(1015, 120+70*i, 60, 25);
			idd.add(sid);
			jb1.addActionListener(new ActionListener() {
				//ɾ����ť����¼�����
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����?", "��Ϣ��", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						try {
							Search_Del rg=new Search_Del(socket);
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
				//�޸İ�ť����¼�����
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
		b.addActionListener(new ActionListener() {	
			//��Ӱ�ť����¼�����
			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudent add=new AddStudent(socket);
				dispose();
			}
		});
		b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
		
	
}
