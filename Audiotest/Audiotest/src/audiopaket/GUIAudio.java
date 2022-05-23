package audiopaket;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;

public class GUIAudio extends JFrame
{

	private JPanel contentPane;
	private JButton btnStop;

	public GUIAudio()
	{
		initialize();
	}

	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 150, 107);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnStop());
	}

	protected JButton getBtnStop()
	{
		if (btnStop == null)
		{
			btnStop = new JButton("Stop");
			btnStop.setBackground(Color.PINK);
			btnStop.setBounds(22, 23, 89, 23);
		}
		return btnStop;
	}
}
