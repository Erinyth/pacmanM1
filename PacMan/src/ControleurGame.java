
public class ControleurGame implements ControleurAvance{

	private Game etatJeu;
	private View affichagePartie;

	public ControleurGame(Game gameState) {
		this.etatJeu = gameState;
		affichagePartie = new View(etatJeu, this);
	}
	//Run only one step
	public void step() {
		etatJeu.stop();
		etatJeu.step();
		affichagePartie.disablePause();
		affichagePartie.enableRun();
		affichagePartie.enableInit();
	}

	public void start() {
		etatJeu.launch();
		
		affichagePartie.disableRun();
		affichagePartie.enablePause();
		affichagePartie.enableStep();
		affichagePartie.enableInit();
		
	}

	public void init() throws Exception {
		etatJeu.stop();
		etatJeu.init();
		
		affichagePartie.disableInit();
		affichagePartie.enableRun();
		affichagePartie.enablePause();
		affichagePartie.enableStep();
	}
	
	public void pause(){
		etatJeu.stop();
		
		affichagePartie.disablePause();
		affichagePartie.enableRun();
		affichagePartie.enableInit();
		affichagePartie.enableStep();
		
	}
	
	public void receptionSlider(int valeurSlider)
	{
		etatJeu.setTPS(valeurSlider);
	}
	
	public void receptionNouveauLaby(String chemin)
	{
		try {
			etatJeu.setNomLaby(chemin);
			etatJeu.intializeGame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
