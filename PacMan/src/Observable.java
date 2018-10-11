
public interface Observable {

	public void enregistrerObservateur(Observateur observateur);
	public void supprimerObservateur(Observateur observateur);
	public void notifierObservateur();
	
}
