package vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Maze;
import model.Observateur;
import model.PacmanGame;

import controller.ControleurGame;


public class View implements Observateur{

	private PacmanGame jeu;
	private ControleurGame controleur;


	//Utilitaires
	private JMenuBar menuBar;
	private JMenu fichier, aPropos;
	private JMenuItem open, mi_aboutUs;	
	private JFileChooser fileChooser;
	private PopUpView aboutUs;

	//Fenêtre commande
	private JFrame AffichageCommandes;
	private JLabel compteurTours;
	private JButton boutonInit;
	private JButton boutonPause;
	private JButton boutonRun;
	private JButton boutonStep;

	//Interface jeu
	private Maze lab;
	private PanelPacmanGame ecranJeu;
	private JFrame AffichageJeu;


	public View(PacmanGame game, ControleurGame controler)
	{
		this.controleur = controler;		
		this.jeu = game;
		this.jeu.enregistrerObservateur(this);


		// Fenetre des commandes //////////////////////////////////////
		//
		AffichageCommandes = new JFrame();
		//Définit un titre pour notre fenêtre
		AffichageCommandes.setTitle("Commandes Pacman");
		//Définit sa taille : 400 pixels de large et 100 pixels de haut
		AffichageCommandes.setSize(500, 250);
		AffichageCommandes.setLocation(400,700);
		//Termine le processus lorsqu'on clique sur la croix rouge
		AffichageCommandes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       

		//Boutons
		Icon icon_restart = new ImageIcon("icon_restart.png");
		Icon icon_pause = new ImageIcon("icon_pause.png");
		Icon icon_run = new ImageIcon("icon_run.png");
		Icon icon_step = new ImageIcon("icon_step.png");

		boutonInit = new JButton(icon_restart);
		boutonInit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evenement){
				try {
					controleur.init();
					chargementLabyrinthe();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});		

		boutonPause = new JButton(icon_pause);
		boutonPause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evenement){
				controleur.pause();
			}
		});
		boutonPause.setEnabled(false);


		boutonRun = new JButton(icon_run);
		boutonRun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evenement){
				controleur.start();
			}
		});
		boutonRun.setEnabled(false);


		boutonStep = new JButton(icon_step);
		boutonStep.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evenement){
				controleur.step();
			}
		});
		boutonStep.setEnabled(false);


		//Slider
		final JSlider tourParSeconde = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		tourParSeconde.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event)
			{
				int valeur = tourParSeconde.getValue();
				controleur.receptionSlider(valeur);
			}
		});
		tourParSeconde.setMajorTickSpacing(1);
		tourParSeconde.setPaintTicks(true);
		tourParSeconde.setPaintLabels(true);

		//Label
		compteurTours = new JLabel("Tour: 0");
		compteurTours.setHorizontalAlignment(JLabel.CENTER);

		//Séparation horizontale
		JPanel PanneauCommandes = new JPanel();	
		AffichageCommandes.setContentPane(PanneauCommandes);
		GridLayout LayoutCommandes = new GridLayout(2,1);
		PanneauCommandes.setLayout(LayoutCommandes);

		//Partie Boutons
		JPanel PanneauBoutons = new JPanel();
		GridLayout LayoutBoutons = new GridLayout(1,4);
		PanneauBoutons.setLayout(LayoutBoutons);
		PanneauBoutons.add(boutonInit);
		PanneauBoutons.add(boutonRun);
		PanneauBoutons.add(boutonStep);
		PanneauBoutons.add(boutonPause);
		PanneauCommandes.add(PanneauBoutons);	//Ajout de la partie bouton en haut	

		//Partie slider/label
		JPanel PanneauSliders = new JPanel();
		GridLayout LayoutSlider = new GridLayout(1,2);
		PanneauSliders.setLayout(LayoutSlider);
		PanneauSliders.add(tourParSeconde, "Tour par seconde");
		PanneauSliders.add(compteurTours);
		PanneauCommandes.add(PanneauSliders);	//Ajout du slider et label en bas

		//Et enfin, la rendre visible 
		AffichageCommandes.setAlwaysOnTop(true);
		AffichageCommandes.setVisible(true);

		//Fenetre du jeu //////////////////////////////////////////////
		//
		AffichageJeu = new JFrame();
		//Définit un titre pour notre fenêtre
		AffichageJeu.setTitle("Pacman by Proz & Erin");
		//Définit sa taille : 400 pixels de large et 100 pixels de haut
		AffichageJeu.setSize(500, 500);
		AffichageJeu.setLocation(400,200);
		//Termine le processus lorsqu'on clique sur la croix rouge
		AffichageJeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//Et enfin, la rendre visible   
		AffichageJeu.setAlwaysOnTop(true);
		AffichageJeu.setVisible(true);

		// Barre de menus et sélection des fichiers
		menuBar = new JMenuBar();

		fichier = new JMenu("Fichier");
		menuBar.add(fichier);

		open = new JMenuItem("Ouvrir...");
		open.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				fileChooser = new JFileChooser("./layouts");
				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					String filePath = file.getAbsolutePath();	
					System.out.println(filePath);
					controleur.receptionNouveauLaby(filePath);
				}
			}
		});
		fichier.add(open);

		aPropos = new JMenu("A propos");
		menuBar.add(aPropos);

		mi_aboutUs = new JMenuItem("Nous concernant...");
		mi_aboutUs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == mi_aboutUs)
				{
					aboutUs = new PopUpView();
					aboutUs.setModal(true);
					aboutUs.setVisible(true);
				}
			}
		});
		aPropos.add(mi_aboutUs);

		AffichageJeu.setJMenuBar(menuBar);
	}

	/**
	 * Methode d'interface
	 */

	@Override
	public void actualiser() {
		for (int i=0; i< this.ecranJeu.getPacmans_pos().size();i++)
		{
			for (int j=0; j<this.jeu.getListePacman().size();j++)
			{
				this.ecranJeu.getPacmans_pos().clear();
				this.ecranJeu.getPacmans_pos().add(this.jeu.getListePacman().get(j).getPositionAgent());
			}
		}
		for (int i=0; i< this.ecranJeu.getGhosts_pos().size();i++)
		{
			for (int j=0; j<this.jeu.getListeFantomes().size();j++)
			{
				this.ecranJeu.getGhosts_pos().clear();
				this.ecranJeu.getGhosts_pos().add(this.jeu.getListeFantomes().get(j).getPositionAgent());
			}
		}

		System.out.println("DEBUG: " + ecranJeu.getPacmans_pos().size());
		
		lab = jeu.getLaby();
		ecranJeu = new PanelPacmanGame(lab);
		AffichageJeu.setContentPane(ecranJeu);
		AffichageJeu.repaint();
		AffichageJeu.setVisible(true);
	}

	public void chargementLabyrinthe()
	{
		try {
			lab = jeu.getLaby();
			ecranJeu = new PanelPacmanGame(lab);
			AffichageJeu.setContentPane(ecranJeu);
			AffichageJeu.setSize(lab.getSizeX()*20, lab.getSizeY()*20);
			AffichageJeu.repaint();
			AffichageJeu.setVisible(true);
		} catch (Exception e1) {
			System.out.println("Impossible de charger le labyrinthe");
			e1.printStackTrace();
		}		
	}

	public void disableInit()
	{
		boutonInit.setEnabled(false);
	}

	public void enableInit()
	{
		boutonInit.setEnabled(true);
	}

	public void disablePause()
	{
		boutonPause.setEnabled(false);
	}

	public void enablePause()
	{
		boutonPause.setEnabled(true);
	}

	public void disableStep()
	{
		boutonStep.setEnabled(false);
	}

	public void enableStep()
	{
		boutonStep.setEnabled(true);
	}

	public void disableRun()
	{
		boutonRun.setEnabled(false);
	}

	public void enableRun()
	{
		boutonRun.setEnabled(true);
	}	
}
