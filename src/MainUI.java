import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Boolean isChatting = false;		// mốc tín hiệu có phải là đang trong chat mode hay không.
	
	private String Username;
	private JButton Bt_Ketnoi;
	private JTextField Tf_Diachi;
	private JButton Bt_Huyketnoi;
	private JTextArea Ta_Khungnhap;
	private JButton Bt_Send;
	private JTextArea Ta_Khungchat;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// borrowing window UI
				try {
		      		String str="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		    		UIManager.setLookAndFeel(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1170, 740);
		setTitle("Group_Chat_Legend");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// TODO Bảo sẽ thêm JMenu vào sau
//		// JMenu
//		JMenuBar menubar1 = new JMenuBar();
//		JMenu menu1 = new JMenu("Home");
//		menubar1.add(menu1);
//		
//		JMenuItem menu1_it1 = new JMenuItem();
		
		
		JPanel pad1 = new JPanel();
		pad1.setBackground(new Color(219, 219, 219));
		pad1.setBounds(10, 10, 436, 693);
		contentPane.add(pad1);
		pad1.setLayout(null);
		
		Tf_Diachi = new JTextField();
		Tf_Diachi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Tf_Diachi.setBackground(new Color(229, 229, 229));
		Tf_Diachi.setBounds(86, 261, 255, 39);
		Tf_Diachi.setColumns(10);
		Tf_Diachi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connect();
				
			}
		});
		pad1.add(Tf_Diachi);
		
		Bt_Ketnoi = new JButton("Kết nối");
		Bt_Ketnoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Bt_Ketnoi.setBounds(86, 326, 255, 51);
		Bt_Ketnoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connect();
				
			}
		});
		pad1.add(Bt_Ketnoi);
		
		Bt_Huyketnoi = new JButton("Hủy kết nối");
		Bt_Huyketnoi.setEnabled(false);
		Bt_Huyketnoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Bt_Huyketnoi.setBounds(86, 399, 255, 51);
		Bt_Huyketnoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Disconnect();
				
			}
		});
		pad1.add(Bt_Huyketnoi);
		
		JLabel Lb_Diachi = new JLabel("Địa chỉ IPv4:");
		Lb_Diachi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Lb_Diachi.setVerticalAlignment(SwingConstants.BOTTOM);
		Lb_Diachi.setBounds(87, 236, 136, 25);
		pad1.add(Lb_Diachi);
		
		JPanel pad2 = new JPanel();
		pad2.setBackground(new Color(243, 243, 243));
		pad2.setBounds(445, 10, 701, 693);
		pad2.setLayout(null);
		contentPane.add(pad2);
		
		Ta_Khungnhap = new JTextArea();
		Ta_Khungnhap.setEnabled(false);
		Ta_Khungnhap.setWrapStyleWord(true);
		Ta_Khungnhap.setLineWrap(true);
		Ta_Khungnhap.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Ta_Khungnhap.setText("");
		Ta_Khungnhap.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Ta_Khungnhap.setBackground(new Color(239, 239, 239));
		Ta_Khungnhap.setBounds(0, 0, 609, 54);
		Ta_Khungnhap.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Sending();
				}
			}
		});
		JScrollPane Ta_Khungnhap_Jsc = new JScrollPane(Ta_Khungnhap);
		Ta_Khungnhap_Jsc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Ta_Khungnhap_Jsc.setBounds(0, 639, 609, 54);
		pad2.add(Ta_Khungnhap_Jsc);
		
		Bt_Send = new JButton("");
		Bt_Send.setEnabled(false);
		Bt_Send.setBounds(606, 639, 95, 54);
		Bt_Send.setIcon(new ImageIcon(new ImageIcon(MainUI.class.getResource("/Images/send.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		Bt_Send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Sending();
				
			}
		});
		pad2.add(Bt_Send);
		
		Ta_Khungchat = new JTextArea();
		Ta_Khungchat.setEditable(false);
		Ta_Khungchat.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Ta_Khungchat.setBackground(new Color(219, 219, 219));
		Ta_Khungchat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Ta_Khungchat.setWrapStyleWord(true);
		Ta_Khungchat.setLineWrap(true);
		Ta_Khungchat.setBounds(0, 0, 701, 640);
		JScrollPane Ta_Khungchat_Jsc = new JScrollPane(Ta_Khungchat);
		Ta_Khungchat_Jsc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Ta_Khungchat_Jsc.setBounds(0, 0, 701, 640);
		pad2.add(Ta_Khungchat_Jsc);
		
		
		// hỏi tên Username
		JFrame DiaFrame = new JFrame("showDialog_Username's frame");
		showDialog_Username(DiaFrame);
		
	}
	
    private void showDialog_Username(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Nhập tên người dùng", true);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 290, 151);
		panel.setLayout(null);
		
		JLabel Username_Lb = new JLabel("Nhập tên:");
		Username_Lb.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Username_Lb.setBounds(26, 41, 85, 21);
		panel.add(Username_Lb);
		
		JTextField Username_Tf = new JTextField();
		Username_Tf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Username_Tf.setColumns(10);
		Username_Tf.setBackground(new Color(229, 229, 229));
		Username_Tf.setBounds(22, 60, 236, 30);
		Username_Tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String Username_Tf_Check = Username_Tf.getText();
				if (Username_Tf_Check.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Tên người dùng không được để trống!", "Nhập lại tên!", JOptionPane.PLAIN_MESSAGE);
				} else {
					setUsername(Username_Tf.getText());
					dialog.dispose();
				}
			}
		});
		panel.add(Username_Tf);
		
		JButton Dangnhap_Bt = new JButton("Đăng nhập");
		Dangnhap_Bt.setBounds(92, 110, 107, 31);
		Dangnhap_Bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String Username_Tf_Check = Username_Tf.getText();
				if (Username_Tf_Check.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Tên người dùng không được để trống!", "Nhập lại tên!", JOptionPane.PLAIN_MESSAGE);
				} else {
					setUsername(Username_Tf.getText());
					dialog.dispose();
				}
			}
		});
		panel.add(Dangnhap_Bt);
        
        // Set the content of the dialog to the panel
        dialog.getContentPane().add(panel);

        // Set dialog properties
        dialog.setSize(320, 200);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void Chatmode_On() {
    	isChatting = true;
    	
    	Bt_Ketnoi.setEnabled(false);
    	Tf_Diachi.setEnabled(false);
    	Tf_Diachi.setBackground(new Color(219, 219, 219));
    	
    	Bt_Huyketnoi.setEnabled(true);
    	Ta_Khungnhap.setEnabled(true);
    	Ta_Khungchat.setBackground(new Color(249, 249, 249));
    	Bt_Send.setEnabled(true);
    }
    
    private void Chatmode_Off() {
    	isChatting = false;
    	
    	Bt_Ketnoi.setEnabled(true);
    	Tf_Diachi.setEnabled(true);
    	Tf_Diachi.setBackground(new Color(229, 229, 229));
    	
    	Bt_Huyketnoi.setEnabled(false);
    	Ta_Khungnhap.setEnabled(false);
    	Ta_Khungchat.setBackground(new Color(219, 219, 219));
    	Bt_Send.setEnabled(false);
    }
    
    private void Connect() {
		Chatmode_On();
		
		// TODO	Thêm các chức năng kết nối vào đây
		
    }
    
    private void Sending() {
		// TODO Thêm các chức năng khi gửi thông tin(tin nhắn) vào đây
			
    	
    }
    
    private void Disconnect() {
		Chatmode_Off();
		
		// TODO Thêm các chức năng hủy kết nối vào đây
		
    	
    }
    
    
    
    
    
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}
}
