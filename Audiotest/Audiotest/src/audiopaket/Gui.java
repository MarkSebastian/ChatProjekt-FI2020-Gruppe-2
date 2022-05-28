package audiopaket;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Gui extends JFrame
{

	private JPanel contentPane;
	private JButton btnAufnehmen;
	private JButton btnAbspielen;
	private JLabel lblAudiotest;
	private JButton btnStop;

	public Gui()
	{
		initialize();
	}

	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnAufnehmen());
		contentPane.add(getBtnAbspielen());
		contentPane.add(getLblAudiotest());
		setVisible(true);
		contentPane.add(getBtnStop());
	}

	public void setBtnAufnehmen(ActionListener l)
	{
		this.btnAufnehmen.addActionListener(l);
	}
	
	protected JButton getBtnAufnehmen()
	{
		if (btnAufnehmen == null)
		{
			btnAufnehmen = new JButton("Aufnehmen");
			btnAufnehmen.setBounds(60, 177, 89, 23);
		}
		return btnAufnehmen;
	}

	public void setBtnAbspielen(ActionListener l)
	{
		this.btnAbspielen.addActionListener(l);
	}
	
	protected JButton getBtnAbspielen()
	{
		if (btnAbspielen == null)
		{
			btnAbspielen = new JButton("Abspielen");
			btnAbspielen.setBounds(276, 177, 89, 23);
		}
		return btnAbspielen;
	}

	private JLabel getLblAudiotest()
	{
		if (lblAudiotest == null)
		{
			lblAudiotest = new JLabel("Audiotest");
			lblAudiotest.setBounds(47, 31, 135, 14);
		}
		return lblAudiotest;
	}
	
	public void setBtnStop(ActionListener l)
	{
		this.btnStop.addActionListener(l);
	}
	
	protected JButton getBtnStop()
	{
		if (btnStop == null)
		{
			btnStop = new JButton("Stoppen");
			btnStop.setBounds(177, 177, 89, 23);
		}
		return btnStop;
	}
}