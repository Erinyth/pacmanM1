package model;


public class AgentAction
{

    //Vecteur de déplacement qui sera utile pour réaliser l'action dans le jeu
    private int vx;
    private int vy;

    //Direction
    private int direction;
    

    public AgentAction(int direction){

        this.direction=direction;

        //Calcul le vecteur de déplacement associé

        if (direction==Maze.NORTH)
        {
            vx=0;
            vy=-1;
        } 
        else if(direction==Maze.SOUTH)
        {
        	vx=0;
            vy=1;
        }
        else if(direction==Maze.WEST)
        {
        	vx=-1;
            vy=0;
        }
        else if(direction==Maze.EAST)
        {
        	vx=1;
            vy=0;
        }
    }
    
    public int getVX()
    {
    	return vx;
    }
    
    public int getVY()
    {
    	return vy;
    }
    
    public int getDir()
    {
    	return direction;
    }
}