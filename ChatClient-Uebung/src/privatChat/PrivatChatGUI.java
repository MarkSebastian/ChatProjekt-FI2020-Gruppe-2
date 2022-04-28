package privatChat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PrivatChatGUI extends JFrame
{

	private JPanel contentPane;
	private JList listChat;
	private JList listUser;
	private JScrollPane scrollPaneChat;
	private JScrollPane scrollPaneUser;
	private Label labelChatName;
	private JLabel lblTeilnehmer;
	private JTextField textFieldNachricht;
	private JButton btnVerlassen;
	

	public PrivatChatGUI()
	{
		
	}

	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 761, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPaneChat());
		contentPane.add(getScrollPaneUser());
		contentPane.add(getLabelChatName());
		contentPane.add(getLblTeilnehmer());
		contentPane.add(getTextFieldNachricht());
		contentPane.add(getBtnVerlassen());
		setVisible(true);
	}
	
	protected JList getListChat()
	{
		if (listChat == null)
		{
			listChat = new JList();
		}
		return listChat;
	}

	protected JScrollPane getScrollPaneChat()
	{
		if (scrollPaneChat == null)
		{
			JList listChat = getListChat();
			scrollPaneChat = new JScrollPane(listChat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneChat.setBounds(25, 56, 530, 285);
			scrollPaneChat.getVerticalScrollBar().addAdjustmentListener(al -> al.getAdjustable().setValue(al.getAdjustable().getMaximum()));			
		}
		return scrollPaneChat;
	}
	
	protected JList getListUser()
	{
		if (listUser == null)
		{
			listUser = new JList();
		}
		return listUser;
	}

	protected JScrollPane getScrollPaneUser()
	{
		if (scrollPaneUser == null)
		{
			JList listUser = getListUser();
			scrollPaneUser = new JScrollPane(listUser, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneUser.setBounds(585, 56, 138, 285);
			scrollPaneUser.getVerticalScrollBar().addAdjustmentListener(al -> al.getAdjustable().setValue(al.getAdjustable().getMaximum()));
		}
		return scrollPaneUser;
	}
	private Label getLabelChatName() {
		if (labelChatName == null) {
			labelChatName = new Label("New label");
			labelChatName.setFont(new Font("Tahoma", Font.BOLD, 20));
			labelChatName.setBounds(25, 10, 530, 35);
		}
		return labelChatName;
	}
	private JLabel getLblTeilnehmer() {
		if (lblTeilnehmer == null) {
			lblTeilnehmer = new JLabel("Teilnehmer");
			lblTeilnehmer.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblTeilnehmer.setBounds(585, 10, 138, 35);
		}
		return lblTeilnehmer;
	}
	private JTextField getTextFieldNachricht() {
		if (textFieldNachricht == null) {
			textFieldNachricht = new JTextField();
			textFieldNachricht.setBounds(25, 354, 530, 20);
			textFieldNachricht.setColumns(10);
		}
		return textFieldNachricht;
	}
	private JButton getBtnVerlassen() {
		if (btnVerlassen == null) {
			btnVerlassen = new JButton("Chat verlassen");
			btnVerlassen.setBounds(585, 353, 138, 23);
		}
		return btnVerlassen;
	}
}
