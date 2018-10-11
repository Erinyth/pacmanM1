import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

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


public class View implements Observateur{

	//temporaire
	JLabel affichageInfoJeu;
	
	JLabel compteurTours;
	
	JMenuBar menuBar;
	JMenu fichier, aPropos;
	JMenuItem open;
	
	JFileChooser fileChooser;

	private Game jeu;
	private ControleurGame controleur;

	JFrame AffichageCommandes;
	JFrame AffichageJeu;

	JButton boutonInit;
	JButton boutonPause;
	JButton boutonRun;
	JButton boutonStep;
	

	public View(Game game, ControleurGame controler)
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


		//Label		
		// TOUT BOUGER POUR PERMETTRE DE LES CHANGER
		Maze lab;
		PanelPacmanGame ecranJeu;
		try {
			lab = new Maze("./layouts/bigCorners.lay");
			ecranJeu = new PanelPacmanGame(lab);
			JPanel PanneauJeu = new JPanel();
			AffichageJeu.setContentPane(ecranJeu);
			GridLayout LayoutJeu = new GridLayout(1,1);
			PanneauJeu.setLayout(LayoutJeu);
			PanneauJeu.add(affichageInfoJeu);
		} catch (Exception e1) {
			System.out.println("Impossible de charger le premier laby, du coup tout est cassé");
			e1.printStackTrace();
		}				

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
						fileChooser = new JFileChooser();
						int returnVal = fileChooser.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION)
						{
							File file = fileChooser.getSelectedFile();
							String filePath = file.getAbsolutePath();	
							controleur.receptionNouveauLaby(filePath);
						}
					}
				});
				fichier.add(open);
			
			aPropos = new JMenu("A propos");
			menuBar.add(aPropos);
			
			AffichageJeu.setJMenuBar(menuBar);
	}

	/**
	 * Methode d'interface
	 */
	public void actualiser()
	{
		System.out.println("Actualisation de l'affichage");
		compteurTours.setText("Tour: " + jeu.nbTours);		
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
