package chatServer;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;

public class Gui
{

	private JFrame frmServer;
	private JList list;
	private JLabel lblPortNr;
	private JTextField textFieldPortNr;
	private JButton btnStartServer;
	private JTextField textNachrichtenEingabe;
	private JButton btnStoppen;
	private JScrollPane scrollPane;
	private JButton btnShowUserList;
	private JList listUser;

	private boolean userList = false;
	private JScrollPane scrollPaneListUser;

	public Gui()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 450, 477, 363);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		frmServer.getContentPane().add(getList());
		frmServer.getContentPane().add(getLblPortNr());
		frmServer.getContentPane().add(getTextFieldPortNr());
		frmServer.getContentPane().add(getBtnShowUserList());
		frmServer.getContentPane().add(getBtnStartServer());
		frmServer.getContentPane().add(getTextNachrichtenEingabe());
		frmServer.getContentPane().add(getBtnStoppen());
		frmServer.getContentPane().add(getScrollPane());
		frmServer.getContentPane().add(getListUser());
		frmServer.getContentPane().add(getScrollPaneListUser());

		frmServer.setAlwaysOnTop(true);
		frmServer.setVisible(true);
	}

	protected void titelAendern(String s)
	{
		frmServer.setTitle("Server (IP: " + s + ")");
	}

	protected JList getList()
	{
		if (list == null)
		{
			list = new JList();
			list.setBounds(22, 66, 275, 136);
		}
		return list;
	}

	protected void addListListener(MouseMotionAdapter l)
	{
		getList().addMouseMotionListener(l);
	}

	private JLabel getLblPortNr()
	{
		if (lblPortNr == null)
		{
			lblPortNr = new JLabel("PORT:");
			lblPortNr.setBounds(11, 10, 45, 13);
		}
		return lblPortNr;
	}

	public void addPortEingabeListener(ActionListener l)
	{
		this.textFieldPortNr.addActionListener(l);
	}

	protected JTextField getTextFieldPortNr()
	{
		if (textFieldPortNr == null)
		{
			textFieldPortNr = new JTextField();
			textFieldPortNr.setBounds(10, 26, 96, 19);
			textFieldPortNr.setColumns(10);
		}
		return textFieldPortNr;
	}

	public void addBtnStartListener(ActionListener l)
	{
		this.btnStartServer.addActionListener(l);
	}

	public void showStart()
	{
		try
		{
			if (Integer.parseInt(textFieldPortNr.getText()) >= 1024
					&& Integer.parseInt(textFieldPortNr.getText()) <= 49151)
			{
				this.btnStartServer.setVisible(true);
				this.btnStoppen.setVisible(false);
				this.textFieldPortNr.setEditable(true);
			}
		}
		catch (NumberFormatException e)
		{
			System.out.println("keine Zahl als Port");
		}

	}

	public void showStoppen()
	{
		this.btnStartServer.setVisible(false);
		this.btnStoppen.setVisible(true);
		this.textFieldPortNr.setEditable(false);
	}

	private JButton getBtnStartServer()
	{
		if (btnStartServer == null)
		{
			btnStartServer = new JButton("starten");
			btnStartServer.setBackground(new Color(102, 204, 51));
			btnStartServer.setBounds(185, 25, 112, 21);
			btnStartServer.setVisible(false);
		}
		return btnStartServer;
	}

	public void addNewNachrichtListener(ActionListener l)
	{
		this.getTextNachrichtenEingabe().addActionListener(l);
	}

	protected JTextField getTextNachrichtenEingabe()
	{
		if (textNachrichtenEingabe == null)
		{
			textNachrichtenEingabe = new JTextField();
			textNachrichtenEingabe.setBounds(10, 284, 287, 19);
			textNachrichtenEingabe.setColumns(10);
		}
		return textNachrichtenEingabe;
	}

	public void addBtnStoppenListener(ActionListener l)
	{
		this.btnStoppen.addActionListener(l);
	}

	private JButton getBtnStoppen()
	{
		if (btnStoppen == null)
		{
			btnStoppen = new JButton("stoppen");
			btnStoppen.setBackground(new Color(255, 102, 102));
			btnStoppen.setBounds(185, 26, 112, 19);
			btnStoppen.setVisible(false);
		}
		return btnStoppen;
	}

	private JScrollPane getScrollPane()
	{
		if (scrollPane == null)
		{
			JList list = getList();
			scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setBounds(10, 60, 287, 214);
			scrollPane.getVerticalScrollBar()
					.addAdjustmentListener(al -> al.getAdjustable().setValue(al.getAdjustable().getMaximum()));

		}
		return scrollPane;
	}

	private JButton getBtnShowUserList()
	{
		if (btnShowUserList == null)
		{
			btnShowUserList = new JButton("");
			btnShowUserList.setBackground(SystemColor.scrollbar);
			btnShowUserList.setBounds(307, 60, 11, 73);
			btnShowUserList.addActionListener(l -> showUserList());
		}
		return btnShowUserList;
	}

	private void showUserList()
	{
		Rectangle r = frmServer.getBounds();
		if (userList)
		{
			getScrollPaneListUser().setVisible(true);
			frmServer.setBounds(r.x, r.y, 342, 350);
			userList = false;
		}
		else
		{
			getScrollPaneListUser().setVisible(true);
			frmServer.setBounds(r.x, r.y, 465, 350);
			userList = true;
		}
	}

	protected JList getListUser()
	{
		if (listUser == null)
		{
			listUser = new JList();
		}
		return listUser;
	}

	protected int hoveredItem()
	{
		try
		{
			return getList().locationToIndex(getList().getMousePosition());
		}
		catch (NullPointerException e)
		{
			return -1;
		}
	}

	private JScrollPane getScrollPaneListUser()
	{
		if (scrollPaneListUser == null)
		{
			JList userList = getListUser();
			scrollPaneListUser = new JScrollPane(userList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneListUser.setBounds(328, 60, 106, 214);
		}
		return scrollPaneListUser;
	}
}
