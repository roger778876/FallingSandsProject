
import java.awt.*;
import java.util.*;

public class SandLab
{
    public static void main(String[] args)
    {
	SandLab lab = new SandLab(200,200);
	lab.run();
    }
  
    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int SAND = 2;
    public static final int WATER = 3;
    public static final int LAVA = 4;
    public static final int GAS = 5;
    public static final int VINE = 6;
    public static final int FIRE = 7;
    
    //do not add any more fields
    private int[][] grid;
    private SandDisplay display;
    public SandLab(int numRows, int numCols)
    {
	String[] names;
	grid = new int[numRows][numCols];
	names = new String[8];
	names[EMPTY] = "Empty";
	names[METAL] = "Metal";
	names[SAND] = "Sand";
	names[WATER] = "Water";
	names[LAVA] = "Lava";
	names[GAS] = "Gas";
	names[VINE]="Vine";
	names[FIRE]="Fire";
	
       
	display = new SandDisplay("Falling Sand", numRows, numCols, names);
    }
  
    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool,int brushSize)
    {
	int dupe = 0;     
    
	
	for(int i = Math.abs(row - brushSize); i <= (row + brushSize)%200; i++){
	    for(int c = Math.abs(col - brushSize); c <= (col + brushSize)%200; c++){

		//if water and placing fire stop
		if (tool==FIRE && grid[i][c]==WATER) {
		}
		else
		    grid[i][c] = tool;
	    }
        
        
	}
    }

    //copies each element of grid into the display
    public void updateDisplay()
    {
	for(int r = 0; r < grid.length ;r++){
	    for(int c = 0; c < grid[r].length; c++){
		if (grid[r][c] == METAL)
		    display.setColor(r,c, new Color(120,120,120));
		if(grid[r][c] == EMPTY)
		    display.setColor(r,c, new Color(0,0,0));
		if(grid[r][c] == SAND)
		    display.setColor(r,c, new Color(255,255,0));
		if(grid[r][c] == WATER)
		    display.setColor(r,c, new Color(0,0,255));
		if(grid[r][c] == LAVA)
		    display.setColor(r,c, new Color(244,58,7));
		if(grid[r][c] == GAS)
		    display.setColor(r,c, new Color(188,198,204));
		if(grid[r][c]==VINE)
		    display.setColor(r,c,new Color(4,201,70));
		if (grid[r][c]==FIRE)
		    display.setColor(r,c,new Color(255,140,0));

	    } 
	    
	    
	}


	    
	
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {
	int r = (int)(Math.random() * (grid.length));
	int c = (int)(Math.random() * (grid[r].length));
	if (grid[r][c] == SAND){
	    if(r < grid.length-1 && grid[r + 1][c] == EMPTY){
		grid[r][c] = EMPTY;
		grid[r+1][c] = SAND;
	    }
	    if(r < grid.length-1 && grid[r + 1][c] == WATER){
		grid[r][c] = WATER;
		grid[r+1][c] = SAND;
	    }
	    if (r<grid.length-1&&grid[r+1][c]==LAVA) {
		grid[r][c]=FIRE;
	    }
	    if (c>0 && grid[r][c-1]==LAVA) {
		grid[r][c]=FIRE;
	    }
	    if (c<grid[r].length-1 && grid[r][c+1]==LAVA){
		grid[r][c]=FIRE;
	    }
	    if (grid[r-1][c]==LAVA) {
		grid[r][c]=FIRE;
	    }
	    
	}
	if (grid[r][c] == GAS){
	    
	    int direction = (int)(Math.random() * 3);
	    if (r == 0){
		grid[r][c] = EMPTY;
	    }
	    else{
		if (direction == 0){
		    if(c > 0 && grid[r][c - 1] == EMPTY){
			grid[r][c] = EMPTY;
			grid[r][c-1] = GAS;
		    }
		    else{
			if (c>0&& grid[r][c-1]==LAVA) {
			    grid[r][c]=FIRE;
			}
			else if(c > 0 && grid[r][c - 1] != METAL && grid[r][c-1] != SAND && grid[r][c-1]!=VINE){
			    grid[r][c] = grid[r][c-1];
			    grid[r][c-1] = GAS;
			}
		    }
		}
		else if (direction == 1){
		    if(grid[r - 1][c] == EMPTY){
		     	grid[r][c] = EMPTY;
			grid[r-1][c] = GAS;
		    }
		    else{
			if (grid[r-1][c]==LAVA) {
			    grid[r][c]=FIRE;
			}
			else if(grid[r-1][c] != METAL && grid[r-1][c] != SAND&&grid[r-1][c]!=VINE){
			    grid[r][c] = grid[r-1][c];
			    grid[r-1][c] = GAS;
			}
		    }
		}
		else {
		    if(c < grid[r].length - 1 && grid[r][c + 1] == EMPTY){
		     	grid[r][c] = EMPTY;
			grid[r][c+1] = GAS;
			
		    }
		    else{
			if (c<grid[r].length-1&& grid[r][c+1]==LAVA) {
			    grid[r][c]=FIRE;
			}
			else if(c < grid[r].length - 1 && grid[r][c + 1] != METAL && grid[r][c+1] != SAND&&grid[r][c+1]!=VINE){
			    grid[r][c] = grid[r][c+1];
			    grid[r][c+1] = GAS;
			}
		    }
		}
	    }

	}
	
	
	if (grid[r][c] == WATER){
	    int direction = (int)(Math.random() * 3);
	    if (direction == 0){
		if(c > 0 && grid[r][c - 1] == EMPTY){
		    grid[r][c] = EMPTY;
		    grid[r][c-1] = WATER;
		}
		if(c > 0 &&  (grid[r][c-1] == LAVA || grid[r][c-1]==FIRE)){
		    grid[r][c] = EMPTY;
		    grid[r][c-1] = GAS;
		}
		if(c > 0 &&  grid[r][c-1]==FIRE){
		    grid[r][c-1] = GAS;
		}
		
	    }
	    else if (direction == 1){
		if(r < grid.length - 1 && grid[r + 1][c] == EMPTY){
		    grid[r][c] = EMPTY;
		    grid[r+1][c] = WATER;
		}
		if(r < grid.length - 1 && grid[r+1][c] == LAVA ){
		    grid[r][c] = EMPTY;
		    grid[r+1][c] = GAS;
		    
		}
		if(r < grid.length - 1 && grid[r+1][c]==FIRE){
		    grid[r+1][c] = GAS;
		}
	    }
	    else {
		if(c < grid[r].length - 1 && grid[r][c + 1] == EMPTY){
		    grid[r][c] = EMPTY;
		    grid[r][c+1] = WATER;
		}
	    	if(c < grid[r].length - 1 &&  (grid[r][c+1] == LAVA || grid[r][c+1]==FIRE)){
		    grid[r][c] = EMPTY;
		    grid[r][c+1] = GAS;
		}
		if(c < grid[r].length - 1 &&  grid[r][c+1]==FIRE){
		    grid[r][c+1] = GAS;
		}
	    }
	}
	if (grid[r][c] == LAVA){
	    int direction = (int)(Math.random() * 4);
	    if (direction == 0){
		if(c > 0 && grid[r][c - 1] == EMPTY){
		    grid[r][c] = EMPTY;
		    grid[r][c-1] = LAVA;
		}
		if(c > 0 && grid[r][c-1] == WATER){
		    grid[r][c] = EMPTY;
		    grid[r][c-1] = GAS;
		}
		if(c>0&&(grid[r][c-1]==VINE || grid[r][c-1]==GAS || grid[r][c-1]==SAND)) {
		    grid[r][c-1]=FIRE;
		}
	    }
	    else if (direction == 1 || direction == 3){
		if(r < grid.length-1 && grid[r + 1][c] == EMPTY){
		    grid[r][c] = EMPTY;
		    grid[r+1][c] = LAVA;
		}
		if(r < grid.length-1 && grid[r+1][c] == WATER){
		    grid[r][c] = EMPTY;
		    grid[r+1][c] = GAS;
		}
		if (r<grid.length-1&&(grid[r+1][c]==VINE || grid[r+1][c]==GAS || grid[r+1][c]==SAND)){
		    grid[r+1][c]=FIRE;
		}
	    }
	    else {
		if(c < grid[r].length - 1 && grid[r][c + 1] == EMPTY){
		    grid[r][c] = EMPTY;
		    grid[r][c+1] = LAVA;
		}
		if(c < grid[r].length - 1 &&  grid[r][c+1] == WATER){
		    grid[r][c] = EMPTY;
		    grid[r][c+1] = GAS;
		}
		if (c<grid[r].length-1 && (grid[r][c+1]==VINE || grid[r][c+1]==GAS || grid[r][c+1]==SAND)) {
		    grid[r][c+1]=FIRE;
		}
		
	    }
	}
	if (grid[r][c]==VINE) {
	    if (Math.random()>.95) {
		if ((r<grid.length-1 && c>0 && c<grid[r].length-1)&& (grid[r+1][c+1]!=VINE || grid[r+1][c-1]!=VINE))
		    grid[r+1][c]=VINE;
	    }
	    if (r>0 && (grid[r-1][c]==LAVA || grid[r-1][c]==FIRE)){
		grid[r][c]=FIRE;
	    }
	    if (c>0 && (grid[r][c-1]==FIRE || grid[r][c-1]==LAVA)) {
		grid[r][c]=FIRE;
	    }
	    if (c<grid[r].length-1 && (grid[r][c+1]==FIRE || grid[r][c+1]==LAVA)) {
		grid[r][c]=FIRE;
	    }
	    if (r<grid.length-1 && (grid[r+1][c]==FIRE)) {
		grid[r][c]=FIRE;
	    }

	}
	if (grid[r][c]==FIRE) {
	    int direction = (int)(Math.random() * 3);
	    if (Math.random()>.9) {
		grid[r][c]=EMPTY;
		return;
	    }
	    if (r == 0){
		grid[r][c] = EMPTY;
	    }
	    if (c>0&& grid[r][c-1]==WATER) {
		grid[r][c]=EMPTY;
		return;
	    }
	    if (c<grid[r].length-1&&grid[r][c+1]==WATER) {
		grid[r][c]=EMPTY;
		return;
	    }
	    if (r>0 && grid[r-1][c]==WATER) {
		grid[r][c]=EMPTY;
		return;
	    }
	    if (r<grid.length-1 && grid[r+1][c]==WATER) {
		grid[r][c]=EMPTY;
		return;
	    }

	    else{
		if (direction == 0){
		    if(c > 0 && (grid[r][c - 1] == EMPTY || grid[r][c-1]==VINE)){
			grid[r][c] = EMPTY;
			grid[r][c-1] = FIRE;
		    }
		    if (c>0 && grid[r][c-1]==GAS) {
			if (c>0&&c<grid[r].length-1) {
			    grid[r][c-1]=FIRE;
			    grid[r][c+1]=FIRE;
			    grid[r-1][c-1]=FIRE;
			    grid[r-1][c]=FIRE;
			    grid[r-1][c-1]=FIRE;
			    if (r-1>0) {
				grid[r-2][c-1]=FIRE;
				grid[r-2][c]=FIRE;
				grid[r-2][c+1]=FIRE;
			    }
			    if (r-2>0) {
				grid[r-3][c]=FIRE;
				grid[r-3][c-1]=FIRE;
				grid[r-3][c+1]=FIRE;
			    }
			}
		    }
		}
		else if (direction == 1){
		    if(r>0&& (grid[r - 1][c] == EMPTY || grid[r-1][c]==VINE)){
		     	grid[r][c] = EMPTY;
			grid[r-1][c] = FIRE;
		    }
		    
		    if (r>0 && grid[r-1][c]==GAS) {
			if (c>0&&c<grid[r].length-1) {
			    grid[r][c-1]=FIRE;
			    grid[r][c+1]=FIRE;
			    grid[r-1][c-1]=FIRE;
			    grid[r-1][c]=FIRE;
			    grid[r-1][c-1]=FIRE;
			    if (r-1>0) {
				grid[r-2][c-1]=FIRE;
				grid[r-2][c]=FIRE;
				grid[r-2][c+1]=FIRE;
			    }
			    if (r-2>0) {
				grid[r-3][c]=FIRE;
				grid[r-3][c-1]=FIRE;
				grid[r-3][c+1]=FIRE;
			    }
			}
		    }
		   
		}
		else {
		    if(c < grid[r].length - 1 && (grid[r][c + 1] == EMPTY || grid[r][c+1]==VINE)){
		     	grid[r][c] = EMPTY;
			grid[r][c+1] = FIRE;
			
		    }
		    
		    if (c<grid[r].length-1&&grid[r][c+1]==GAS) {
			if (c>0&&c<grid[r].length-1) {
			    grid[r][c-1]=FIRE;
			    grid[r][c+1]=FIRE;
			    grid[r-1][c-1]=FIRE;
			    grid[r-1][c]=FIRE;
			    grid[r-1][c-1]=FIRE;
			    if (r-1>0) {
				grid[r-2][c-1]=FIRE;
				grid[r-2][c]=FIRE;
				grid[r-2][c+1]=FIRE;
			    }
			    if (r-2>0) {
				grid[r-3][c]=FIRE;
				grid[r-3][c-1]=FIRE;
				grid[r-3][c+1]=FIRE;
			    }
			}
		    }
		}
	    }


	}	
    }
  
    //do not modify
    public void run()
    {
	while (true)
	    {
		for (int i = 0; i < display.getSpeed(); i++)
		    step();
		updateDisplay();
		
		display.repaint();
		display.pause(1);  //wait for redrawing and for mouse
		int[] mouseLoc = display.getMouseLocation();
		if (mouseLoc != null)  //test if mouse clicked
		    locationClicked(mouseLoc[0], mouseLoc[1], display.getTool(),display.getBrushSize());
	    }
    }
}
