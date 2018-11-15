package vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PopUpView extends JDialog
{
	private JPanel aboutUs;
	private GridBagConstraints c;
	private JLabel l_aboutUs;
	
	public PopUpView()
	{
		super();
		build();
	}

	public void build()
	{
		setAlwaysOnTop(true);
		setTitle("Nous concernant");
		setSize(450, 70);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane()
	{
		aboutUs = new JPanel();
		aboutUs.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		l_aboutUs = new JLabel("<html> Programme créé par MOHR Anaïs et PIGACHE Bastien "
                + "sous la direction de Monsieur GOUDET Olivier dans le "
                + "cadre du projet de design patterns de Master 1 informatique. </html>");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			aboutUs.add(l_aboutUs, c);

		
		return aboutUs;
	}
}
