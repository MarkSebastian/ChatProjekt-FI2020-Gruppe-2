package chatClient;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;
import java.awt.event.MouseListener;
import java.awt.ScrollPane;

public class Gui
{

	private JFrame frmClient;
	private JTextField textFieldEingabe;
	private JLabel lblStatus;
	private JList list;
	private JScrollPane scrollPane;
	private JButton btnStop;
	private JButton btnShowUserList;
	private JList listUser;

	private boolean userList = false;
	private JTabbedPane tabbedPane;
	private JPanel panelGlobalChat;
	private JPanel panelNeuerChat;
	private JList listActiveUser;
	private JList listChoosenUser;
	private JButton btnUserHinzufügen;
	private JButton btnUserEntfernen;
	private JButton btnNeuerChat;
	private JScrollPane scrollPaneUserList;
	private JTextField textFieldGruppenName;

	public Gui()
	{
		initialize();
	}

	private void initialize()
	{
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 389, 399);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		frmClient.getContentPane().add(getTabbedPane());

		frmClient.setAlwaysOnTop(true);

	}

	public void hide(Boolean hide)
	{
		if (hide)
		{
			frmClient.setVisible(false);
			if (userList)
			{
				showUserList();
			}
		}
		else
		{
			frmClient.setVisible(true);
		}
	}

	public void changeStatus(String status)
	{
		this.lblStatus.setText(status);
	}

	public void addEingabeListener(ActionListener l)
	{
		this.textFieldEingabe.addActionListener(l);
	}

	protected JTextField getTextFieldEingabe()
	{
		if (textFieldEingabe == null)
		{
			textFieldEingabe = new JTextField();
			textFieldEingabe.setBounds(10, 215, 305, 19);
			textFieldEingabe.setColumns(10);
		}
		return textFieldEingabe;
	}

	private JLabel getLblStatus()
	{
		if (lblStatus == null)
		{
			lblStatus = new JLabel("Satus");
			lblStatus.setBounds(10, 244, 190, 13);
			lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblStatus;
	}

	protected JList getList()
	{
		if (list == null)
		{
			list = new JList();
		}
		return list;
	}

	protected void addListListener(MouseMotionAdapter l)
	{
		getList().addMouseMotionListener(l);
	}

	protected JScrollPane getScrollPane()
	{
		if (scrollPane == null)
		{
			JList list = getList();
			scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setBounds(10, 10, 305, 194);
			scrollPane.getVerticalScrollBar()
					.addAdjustmentListener(al -> al.getAdjustable().setValue(al.getAdjustable().getMaximum()));
		}
		return scrollPane;
	}

	public void addBtnStopListener(ActionListener l)
	{
		this.getBtnStop().addActionListener(l);
	}

	protected JButton getBtnStop()
	{
		if (btnStop == null)
		{
			btnStop = new JButton("stoppen");
			btnStop.setBounds(203, 240, 112, 21);
			btnStop.setToolTipText("verbindung trennen");
			btnStop.setBackground(new Color(255, 102, 102));
		}
		return btnStop;
	}

	private JButton getBtnShowUserList()
	{
		if (btnShowUserList == null)
		{
			btnShowUserList = new JButton("");
			btnShowUserList.setBounds(325, 58, 11, 73);
			btnShowUserList.setBackground(SystemColor.scrollbar);
			btnShowUserList.addActionListener(l -> showUserList());
		}
		return btnShowUserList;
	}

	private void showUserList()
	{
		Rectangle r = frmClient.getBounds();
		if (userList)
		{
			getScrollPaneUserList().setVisible(false);
			frmClient.setBounds(r.x, r.y, 360, 335);
			tabbedPane.setBounds(0, 0, 346, 298);
			userList = false;
		}
		else
		{
			getScrollPaneUserList().setVisible(true);
			frmClient.setBounds(r.x, r.y, 490, 335);
			tabbedPane.setBounds(0, 0, 476, 298);
			userList = true;
		}
	}

	protected JList getListUser()
	{
		if (listUser == null)
		{
			listUser = new JList();
			listUser.setBounds(1, 1, 87, 191);
		}
		return listUser;
	}

	protected Rectangle getFrmBounds()
	{
		return frmClient.getBounds();
	}

	protected void setFrmBounds(Rectangle r)
	{
		frmClient.setBounds(r.x, r.y, 360, 335);

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

	private JTabbedPane getTabbedPane()
	{
		if (tabbedPane == null)
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBorder(null);
			tabbedPane.setBackground(new Color(255, 255, 255));
			tabbedPane.setBounds(0, 11, 346, 325);
			tabbedPane.addTab("Global Chat", null, getPanelGlobalChat(), null);
			tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
			tabbedPane.addTab("neuer Chat", null, getPanelNeuerChat(), null);
			tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
			tabbedPane.addChangeListener(l ->
			{
				if (userList)
				{
					showUserList();
				}
			});
		}
		return tabbedPane;
	}

	private JPanel getPanelGlobalChat()
	{
		if (panelGlobalChat == null)
		{
			panelGlobalChat = new JPanel();
			panelGlobalChat.setBorder(null);
			panelGlobalChat.add(getListUser());
			panelGlobalChat.add(getLblStatus());
			panelGlobalChat.add(getTextFieldEingabe());
			panelGlobalChat.add(getScrollPane());
			panelGlobalChat.add(getBtnShowUserList());
			panelGlobalChat.add(getBtnStop());
			panelGlobalChat.add(getScrollPaneUserList());
		}
		return panelGlobalChat;
	}

	private JPanel getPanelNeuerChat()
	{
		if (panelNeuerChat == null)
		{
			panelNeuerChat = new JPanel();
			panelNeuerChat.setLayout(null);
			panelNeuerChat.add(getListActiveUser());
			panelNeuerChat.add(getListChoosenUser());
			panelNeuerChat.add(getBtnUserHinzufügen());
			panelNeuerChat.add(getBtnUserEntfernen());
			panelNeuerChat.add(getBtnNeuerChat());
			panelNeuerChat.add(getTextFieldGruppenName());
		}
		return panelNeuerChat;
	}

	protected JList getListActiveUser()
	{
		if (listActiveUser == null)
		{
			listActiveUser = new JList();
			listActiveUser.setBounds(10, 10, 128, 210);
		}
		return listActiveUser;
	}

	protected JList getListChoosenUser()
	{
		if (listChoosenUser == null)
		{
			listChoosenUser = new JList();
			listChoosenUser.setBounds(203, 10, 128, 210);
		}
		return listChoosenUser;
	}

	private JButton getBtnUserHinzufügen()
	{
		if (btnUserHinzufügen == null)
		{
			btnUserHinzufügen = new JButton("\u25BA");
			btnUserHinzufügen.setBackground(new Color(255, 255, 255));
			btnUserHinzufügen.setToolTipText("hinzuf\u00FCgen");
			btnUserHinzufügen.setBounds(148, 69, 48, 21);
		}
		return btnUserHinzufügen;
	}

	public void addAddListner(ActionListener l)
	{
		this.getBtnUserHinzufügen().addActionListener(l);
	}

	private JButton getBtnUserEntfernen()
	{
		if (btnUserEntfernen == null)
		{
			btnUserEntfernen = new JButton("\u25C4");
			btnUserEntfernen.setBackground(new Color(255, 255, 255));
			btnUserEntfernen.setToolTipText("entfernen");
			btnUserEntfernen.setBounds(148, 133, 48, 21);
		}
		return btnUserEntfernen;
	}

	public void addEntfListner(ActionListener l)
	{
		this.getBtnUserEntfernen().addActionListener(l);
	}

	private JButton getBtnNeuerChat()
	{
		if (btnNeuerChat == null)
		{
			btnNeuerChat = new JButton("neuer Chat");
			btnNeuerChat.setBackground(new Color(102, 204, 51));
			btnNeuerChat.setBounds(197, 246, 134, 31);
		}
		return btnNeuerChat;
	}
	
	public void setBtnNeuerChatActionListener(ActionListener l)
	{
		this.getBtnNeuerChat().addActionListener(l);
	}

	private JScrollPane getScrollPaneUserList()
	{
		if (scrollPaneUserList == null)
		{
			getPanelGlobalChat().setLayout(null);
			JList listUser = getListUser();
			scrollPaneUserList = new JScrollPane(listUser, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneUserList.setBounds(341, 11, 106, 193);

		}
		return scrollPaneUserList;
	}

	protected JTextField getTextFieldGruppenName() 
	{
		if (textFieldGruppenName == null)
		{
			textFieldGruppenName = new JTextField();
			textFieldGruppenName.setForeground(Color.GRAY);
			textFieldGruppenName.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldGruppenName.setText("Gruppennamen eingeben");
			textFieldGruppenName.setToolTipText("");
			textFieldGruppenName.setBounds(10, 246, 134, 31);
			textFieldGruppenName.setColumns(10);
		}
		return textFieldGruppenName;
	}
	
	public void setTextFieldGruppenNamenListener(MouseListener l)
	{
		this.textFieldGruppenName.addMouseListener(l);
	}
}
