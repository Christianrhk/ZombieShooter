package common.src.main;

public class ShotCalculator { // THIS IS SHIT
	public ShotCalculator(Player p, int x, int y) {
		// int x and int y are coords of mouse pointer.
		
		int range = 400; // Get from players weapon later when implemented <------------------------
		
		// Calculate slope
			double deltaX = x - p.getX();
			double deltaY = y - p.getY();
			
			double slope = deltaY/deltaX;
			
			int slopeLength = (int) Math.sqrt((deltaY*deltaY)+(deltaX*deltaX));

	}
}
