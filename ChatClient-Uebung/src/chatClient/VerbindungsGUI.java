package chatClient;


import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

public class VerbindungsGUI
{

	private JFrame frame;
	private JToggleButton tglbtnCustomIp;
	private JTextField textFieldIP;
	private JButton btnVerbinden;
	private JPasswordField pwTextFieldPasswort;
	private JTextField textFieldUsername;
	private JSeparator separator;
	private JLabel lblIP;
	private JLabel lblNewLabel;
	private JTextField textFieldPort;
	private JLabel lblServer;
	private JLabel lblPasswort;
	private JLabel lblBenutzername;
	private JLabel lblLogin;

	private boolean customIP = false;
	
	public VerbindungsGUI()
	{
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 258);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(getLblServer());
		frame.getContentPane().add(getTglbtnCustomIp());
		frame.getContentPane().add(getTextFieldIP());
		frame.getContentPane().add(getLblIP());
		frame.getContentPane().add(getLblNewLabel());
		frame.getContentPane().add(getTextFieldPort());
		frame.getContentPane().add(getLblLogin());
		frame.getContentPane().add(getLblPasswort());
		frame.getContentPane().add(getPwTextFieldPasswort());
		frame.getContentPane().add(getLblBenutzername());
		frame.getContentPane().add(getTextFieldUsername());
		frame.getContentPane().add(getSeparator());
		frame.getContentPane().add(getBtnVerbinden());
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}

	public void hide(boolean hide)
	{
		if (hide)
		{
			frame.setVisible(false);
		} 
		else
		{
			frame.setVisible(true);
		}
	}

	public boolean getCustomIp()
	{
		return customIP;
	}

	private JToggleButton getTglbtnCustomIp()
	{
		if (tglbtnCustomIp == null)
		{
			tglbtnCustomIp = new JToggleButton("Custom IP");
			tglbtnCustomIp.setFont(new Font("Tahoma", Font.PLAIN, 11));
			tglbtnCustomIp.setToolTipText("CustomIP");
			tglbtnCustomIp.setBounds(44, 42, 121, 20);

			tglbtnCustomIp.addActionListener(l -> invertCustomIP());
		}
		return tglbtnCustomIp;
	}

	private void invertCustomIP()
	{
		if (customIP == false)
		{
			getTextFieldIP().setEditable(true);
			customIP = true;
		} 
		else
		{
			getTextFieldIP().setEditable(false);
			customIP = false;
		}
	}

	private JTextField getTextFieldIP()
	{
		if (textFieldIP == null)
		{
			textFieldIP = new JTextField();
			textFieldIP.setToolTipText("IP");
			textFieldIP.setBounds(44, 88, 121, 20);
			textFieldIP.setColumns(10);
			textFieldIP.setEditable(false);
		}
		return textFieldIP;
	}

	public String getIP()
	{
		return this.getTextFieldIP().getText();
	}

	private JButton getBtnVerbinden()
	{
		if (btnVerbinden == null)
		{
			btnVerbinden = new JButton("Verbinden");
			btnVerbinden.setBackground(new Color(102, 204, 51));
			btnVerbinden.setBounds(270, 165, 112, 23);
		}
		return btnVerbinden;
	}

	public void addBtnVerbindenListener(ActionListener l)
	{
		getBtnVerbinden().addActionListener(l);
	}

	private JPasswordField getPwTextFieldPasswort()
	{
		if (pwTextFieldPasswort == null)
		{
			pwTextFieldPasswort = new JPasswordField();
			pwTextFieldPasswort.setToolTipText("passwort");
			pwTextFieldPasswort.setBounds(245, 134, 160, 20);
		}
		return pwTextFieldPasswort;
	}

	protected JTextField getTextFieldUsername()
	{
		if (textFieldUsername == null)
		{
			textFieldUsername = new JTextField();
			textFieldUsername.setToolTipText("Benutzername");
			textFieldUsername.setBounds(245, 88, 160, 20);
			textFieldUsername.setColumns(10);
		}
		return textFieldUsername;
	}

	private JSeparator getSeparator()
	{
		if (separator == null)
		{
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(217, 11, 207, 197);
		}
		return separator;
	}

	private JLabel getLblIP()
	{
		if (lblIP == null)
		{
			lblIP = new JLabel("IP:");
			lblIP.setBounds(44, 73, 46, 14);
		}
		return lblIP;
	}

	private JLabel getLblNewLabel()
	{
		if (lblNewLabel == null)
		{
			lblNewLabel = new JLabel("PORT: ");
			lblNewLabel.setBounds(44, 119, 61, 14);
		}
		return lblNewLabel;
	}

	private JTextField getTextFieldPort()
	{
		if (textFieldPort == null)
		{
			textFieldPort = new JTextField();
			textFieldPort.setBounds(44, 134, 121, 20);
			textFieldPort.setColumns(10);
		}
		return textFieldPort;
	}
	
	protected void changePortColor(Color c)
	{
		getTextFieldPort().setBackground(c);
	}
	
	protected void changeUsernameColor(Color c)
	{
		getTextFieldUsername().setBackground(c);
	}

	public int getPort()
	{
		return Integer.parseInt(this.textFieldPort.getText());
	}

	private JLabel getLblServer()
	{
		if (lblServer == null)
		{
			lblServer = new JLabel("Server");
			lblServer.setVerticalAlignment(SwingConstants.TOP);
			lblServer.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblServer.setHorizontalAlignment(SwingConstants.CENTER);
			lblServer.setBounds(10, 11, 197, 20);
		}
		return lblServer;
	}

	private JLabel getLblPasswort()
	{
		if (lblPasswort == null)
		{
			lblPasswort = new JLabel("PASSWORT: ");
			lblPasswort.setBounds(245, 119, 112, 14);
		}
		return lblPasswort;
	}

	private JLabel getLblBenutzername()
	{
		if (lblBenutzername == null)
		{
			lblBenutzername = new JLabel("BENUTZERNAME: ");
			lblBenutzername.setBounds(245, 73, 112, 14);
		}
		return lblBenutzername;
	}

	private JLabel getLblLogin()
	{
		if (lblLogin == null)
		{
			lblLogin = new JLabel("Login");
			lblLogin.setVerticalAlignment(SwingConstants.TOP);
			lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogin.setBounds(227, 11, 197, 33);
		}
		return lblLogin;
	}
	
	protected Rectangle getFrmBounds()
	{
		return frame.getBounds();
	}
	
	protected void setFrmBounds(Rectangle r)
	{
		frame.setBounds(r.x, r.y, 450, 258);
	}
}
