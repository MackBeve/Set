import java.awt.Color;
import java.util.ArrayList;

/**
 * An instance of the Set card game
 * 
 * @author mackbeve April 15, 2015
 */

public class Set {

	public final Color RED = new Color(237, 16, 0);
	public final Color GREEN = new Color(34, 207, 19);
	public final Color PURPLE = new Color(162, 18, 201);

	private SetModel model;
	private ArrayList<Card> selected;
	private int setsFound;

	public static void main(String[] args) {
		new Set().run();
	}

	/**
	 * Creates an instance of Set.
	 */
	public Set() {
		selected = new ArrayList<Card>();
		setsFound = 0;
	}

	/**
	 * Starts the game of Set.
	 */
	public void run() {
		drawMenu();
		while (!StdDraw.mousePressed()) {
		}
		if (StdDraw.mouseX() < 0.5) {
			runClassic();
		} else if (StdDraw.mouseX() > 0.5) {
			runSixSet();
		}
	}

	/**
	 * Starts a game of classic Set, where the player tries to find as many set
	 * as the can in the deck.
	 */
	public void runClassic() {
		model = new SetModel(false);
		while (model.getDeck().size() != 0) {
			while (selected.size() < 3) {
				reDraw();
				select(waitForClick());
			}
			if (model.isSet(selected.get(0), selected.get(1), selected.get(2))) {
				model.getBoard().removeAll(selected);
				setsFound++;
				if (model.getNumCards() == 9) {
					model.addCards();
				}
			}
			selected.clear();
		}
		StdDraw.clear();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(0.5, 0.55, "Congratulations!");
		StdDraw.text(0.5, 0.45, "You found all possible sets.");
	}

	/**
	 * Starts a game of 6-Set, where the player tries to find 6 sets among 12
	 * cards.
	 */
	public void runSixSet() {
		ArrayList<Card[]> found = new ArrayList<Card[]>();
		model = new SetModel(true);
		while (model.getNumSets() < 6) {
			model = new SetModel(true);
		}
		System.out.println(model.getNumSets());
		while (found.size() < 6) {
			while (selected.size() < 3) {
				reDrawSixSet(found);
				select(waitForClick());
			}
			if (model.isSet(selected.get(0), selected.get(1), selected.get(2))) {
				if (!duplicateSet(selected, found)) {
					Card[] temp = { selected.get(0), selected.get(1),
							selected.get(2) };
					found.add(temp);
				}
			}
			selected.clear();
		}
		StdDraw.clear();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(0.5, 0.55, "Congratulations!");
		StdDraw.text(0.5, 0.45, "You found all six sets.");
	}

	/**
	 * Draws the initial starting menu
	 */
	public void drawMenu() {
		StdDraw.line(0.5, 0, 0.5, 1);
		StdDraw.text(0.25, 0.55, "Classic Set");
		StdDraw.text(0.25, 0.45, "Find all the sets");
		StdDraw.text(0.25, 0.42, "in the deck");
		StdDraw.text(0.75, 0.55, "6-Set");
		StdDraw.text(0.75, 0.45, "Find 6 sets");
		StdDraw.text(0.75, 0.42, "among twelve cards");
	}

	/**
	 * Redraws the board for classic Set
	 */
	public void reDraw() {
		StdDraw.clear();
		drawBoard(model.getBoard());
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.rectangle(0.5, 0.05, 0.49, 0.04);
		StdDraw.text(0.5, 0.05, "Add Three More Cards");
		StdDraw.line(0.50, 0.1, 0.50, 1);
		StdDraw.line(0, 0.1, 1, 0.1);
		StdDraw.text(0.75, 0.95, "SetsFound:");
		StdDraw.text(0.75, 0.9, "" + setsFound);
		StdDraw.text(0.75, 0.85, "Cards Left:");
		StdDraw.text(0.75, 0.8, "" + model.getNumDeck());
	}

	/**
	 * Redraws the board for 6-Set.
	 * 
	 * @param found
	 *            The sets that have already been found in the game.
	 */
	public void reDrawSixSet(ArrayList<Card[]> found) {
		StdDraw.clear();
		drawBoard(model.getBoard());
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.line(0.505, 0, 0.505, 1);
		StdDraw.text(0.75, 0.95, "SetsFound:");
		for (int i = 0; i < found.size(); i++) {
			for (int j = 0; j < 3; j++) {
				int[] tempChars = found.get(i)[j].getChars();
				Card temp = new Card(tempChars[0], tempChars[1], tempChars[2],
						tempChars[3]);
				drawCard(temp, 0.6 + j * 0.15, 0.85 - i * 0.11);
			}
		}
	}

	/**
	 * Checks to see if a set has already been found.
	 * 
	 * @param selected
	 *            An ArrayList of three card, representing the set that must be
	 *            checked to see if it is a duplicate
	 * @param found
	 *            An ArrayList of Card arrays of length that are all the
	 *            previously found sets
	 * @return true is the set has been found before, false if it has not
	 */
	public boolean duplicateSet(ArrayList<Card> selected,
			ArrayList<Card[]> found) {
		int n = 0;
		for (int i = 0; i < found.size(); i++) {
			for (int j = 0; j < selected.size(); j++) {
				for (int k = 0; k < 3; k++) {
					if (found.get(i)[k] == selected.get(j)) {
						n++;
					}
				}
			}
			if (n >= 3) {
				return true;
			} else {
				n = 0;
			}
		}
		return false;
	}

	/**
	 * Draws all the Cards in a board.
	 * 
	 * @param board
	 *            The board of Cards to be drawn
	 */
	public void drawBoard(ArrayList<Card> board) {
		for (int i = 0; i < board.size() / (board.size() / 3); i++) {
			for (int j = 0; j < board.size() / 3; j++) {
				drawCard(board.get(i * (board.size() / 3) + j),
						0.095 + (0.15 * i), 1 - (0.08 + 0.11 * j));
			}
		}
	}

	/**
	 * Draws a single Card.
	 * 
	 * @param a
	 *            the Card to be drawn
	 * @param x
	 *            the x coordinate of the middle of the card
	 * @param y
	 *            the y coordinate of the middle of the card
	 */
	public void drawCard(Card a, double x, double y) {
		if (selected.contains(a)) {
			StdDraw.setPenColor(Color.ORANGE);
		} else {
			StdDraw.setPenColor(Color.BLACK);
		}
		StdDraw.rectangle(x, y, 0.07, 0.05);
		setColor(a.getColor());
		if (a.getShape() == model.OVAL) {
			for (int i = -1; i < a.getNumber(); i++) {
				drawOval(a.getShade(), x + (i * 0.04), y);
			}
		} else if (a.getShape() == model.DIAMOND) {
			for (int i = -1; i < a.getNumber(); i++) {
				drawDiamond(a.getShade(), x + (i * 0.04), y);
			}
		} else if (a.getShape() == model.SQUIGGLE) {
			for (int i = -1; i < a.getNumber(); i++) {
				drawSquiggle(a.getShade(), x + (i * 0.04), y);
			}
		}
	}

	/**
	 * Sets the Color of the pen to a specified color
	 * 
	 * @param color
	 *            an integer representing one of the three colors cards can be
	 */
	public void setColor(int color) {
		if (color == model.RED) {
			StdDraw.setPenColor(RED);
		} else if (color == model.GREEN) {
			StdDraw.setPenColor(GREEN);
		} else if (color == model.PURPLE) {
			StdDraw.setPenColor(PURPLE);
		}
	}

	/**
	 * Draws a diamond
	 * 
	 * @param shade
	 *            the shade of the diamond
	 * @param x
	 *            the x coordinate of the middle of the diamond
	 * @param y
	 *            the y coordinate of the middle of the diamond
	 */
	public void drawDiamond(int shade, double x, double y) {
		double[] xSet = { x - 0.01, x, x + 0.01, x };
		double[] ySet = { y, y + 0.03, y, y - 0.03 };
		if (shade == model.SOLID) {
			StdDraw.filledPolygon(xSet, ySet);
		} else if (shade == model.SHADED) {
			StdDraw.polygon(xSet, ySet);
			StdDraw.line(x - 0.01, y, x + 0.01, y);
			StdDraw.line(x - 0.005, y + 0.015, x + 0.005, y + 0.015);
			StdDraw.line(x - 0.005, y - 0.015, x + 0.005, y - 0.015);
		} else if (shade == model.CLEAR) {
			StdDraw.polygon(xSet, ySet);
		}
	}

	/**
	 * Draws an oval
	 * 
	 * @param shade
	 *            the shade of the oval
	 * @param x
	 *            the x coordinate of the middle of the oval
	 * @param y
	 *            the y coordinate of the middle of the oval
	 */
	public void drawOval(int shade, double x, double y) {
		Color color = StdDraw.getPenColor();
		if (shade == model.SOLID) {
			StdDraw.filledRectangle(x, y, 0.01, 0.02);
			StdDraw.filledCircle(x, y + 0.02, 0.01);
			StdDraw.filledCircle(x, y - 0.02, 0.01);
		} else if (shade == model.SHADED) {
			drawClearOval(x, y);
			StdDraw.setPenColor(color);
			StdDraw.line(x - 0.01, y, x + 0.01, y);
			StdDraw.line(x - 0.01, y + 0.015, x + 0.01, y + 0.015);
			StdDraw.line(x - 0.01, y - 0.015, x + 0.01, y - 0.015);
		} else if (shade == model.CLEAR) {
			drawClearOval(x, y);
		}
	}

	/**
	 * Draws a squiggle
	 * 
	 * @param shade
	 *            the shade of the squiggle
	 * @param x
	 *            the x coordinate of the middle of the squiggle
	 * @param y
	 *            the x coordinate of the middle of the squiggle
	 */
	public void drawSquiggle(int shade, double x, double y) {
		double[] xSetMid = { x + 0.005, x - 0.003, x - 0.005, x + 0.003 };
		double[] ySetMid = { y + 0.015, y + 0.015, y - 0.015, y - 0.015 };
		double[] xSetTop = { x - 0.003, x + 0.005, x - 0.01 };
		double[] ySetTop = { y + 0.015, y + 0.015, y + 0.03 };
		double[] xSetBottom = { x - 0.005, x + 0.003, x + 0.01 };
		double[] ySetBottom = { y - 0.015, y - 0.015, y - 0.03 };
		if (shade == model.SOLID) {
			StdDraw.filledPolygon(xSetMid, ySetMid);
			StdDraw.filledPolygon(xSetTop, ySetTop);
			StdDraw.filledPolygon(xSetBottom, ySetBottom);
		} else if (shade == model.SHADED) {
			StdDraw.line(x - 0.005, y, x + 0.005, y);
			StdDraw.polygon(xSetMid, ySetMid);
			StdDraw.polygon(xSetTop, ySetTop);
			StdDraw.polygon(xSetBottom, ySetBottom);
		} else if (shade == model.CLEAR) {
			StdDraw.line(x - 0.005, y - 0.015, x - 0.003, y + 0.015);
			StdDraw.line(x + 0.003, y - 0.015, x + 0.005, y + 0.015);
			double[] xSet = { x - 0.01, x - 0.003, x - 0.005, x + 0.01,
					x + 0.003, x + 0.005 };
			double[] ySet = { y + 0.03, y + 0.015, y - 0.015, y - 0.03,
					y - 0.015, y + 0.015 };
			StdDraw.polygon(xSet, ySet);
		}
	}

	/**
	 * Draws an entirely clear oval, only used in the drawOval method
	 * 
	 * @param x
	 *            the x coordinate of the middle of the oval
	 * @param y
	 *            the y coordinate of the middle of the oval
	 */
	public void drawClearOval(double x, double y) {
		Color color = StdDraw.getPenColor();
		StdDraw.rectangle(x, y, 0.01, 0.02);
		StdDraw.circle(x, y + 0.02, 0.01);
		StdDraw.circle(x, y - 0.02, 0.01);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.filledRectangle(x, y, 0.009, 0.023);
		StdDraw.setPenColor(color);
	}

	/**
	 * Waits for the player to click in the board and returns the x and y
	 * coordinates of the click.
	 * 
	 * @return an array of doubles representing the coordinates of the click
	 */
	public double[] waitForClick() {
		while (!StdDraw.mousePressed()) {
		}
		while (StdDraw.mousePressed()) {

		}
		double[] coordinates = { StdDraw.mouseX(), StdDraw.mouseY() };
		return coordinates;
	}

	/**
	 * Selects a single card from the board based on the mouse's coordinates
	 * 
	 * @param coordinates
	 *            the coordinates of the Card that has been clicked on
	 */
	public void select(double[] coordinates) {
		int n = -1;
		double x = coordinates[0];
		double y = coordinates[1];
		if (y < 0.1) {
			model.addCards();
		} else if (x < 0.48 && x > 0.02 && y < 0.97
				&& y > 0.96 - (model.getBoard().size() / 3) * 0.11) {
			while (y < 0.97) {
				n++;
				y += 0.11;
			}
			while (x > 0.17) {
				n += model.getBoard().size() / 3;
				x -= 0.15;
			}
			if (n < model.getBoard().size()) {
				if (!selected.contains(model.getBoard().get(n))) {
					selected.add(model.getBoard().get(n));
				} else {
					selected.remove(model.getBoard().get(n));
				}
			}
		}
	}
}