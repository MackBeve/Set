/**
 * A Card for the game Set
 * 
 * @author Mack Beveridge April 15, 2015
 */
public class Card {

	private int number;
	private int color;
	private int shape;
	private int shade;
	private int[] chars;

	/**
	 * Constructs an instance of Card with random values for number, color,
	 * shape, and shade.
	 */
	public Card() {
		number = (int) (Math.random() * 3);
		color = (int) (Math.random() * 3);
		shape = (int) (Math.random() * 3);
		shade = (int) (Math.random() * 3);
		chars = new int[4];
		chars[0] = number;
		chars[1] = color;
		chars[2] = shape;
		chars[3] = shade;
	}

	/**
	 * Constructs an instance of Card with given value for the various fields.
	 * 
	 * @param number
	 *            an integer representing the number of symbols on the card
	 * @param color
	 *            an integer representing the color of the card's symbols
	 * @param shape
	 *            an integer representing the shape of the card's symbols
	 * @param shade
	 *            an integer representing the shade of the card's symbols
	 */
	public Card(int number, int color, int shape, int shade) {
		this.number = number;
		this.color = color;
		this.shape = shape;
		this.shade = shade;
		chars = new int[4];
		chars[0] = number;
		chars[1] = color;
		chars[2] = shape;
		chars[3] = shade;
	}

	/**
	 * Returns a string representing the Card in the format [number, color,
	 * shape, shade].
	 */
	public String toString() {
		String s = "[";
		s += number + ", ";
		s += color + ", ";
		s += shape + ", ";
		s += shade + "]";
		return s;
	}

	/**
	 * Gets an array of integers representing the characteristics of the Card.
	 * 
	 * @return an array containing all the fields of the Card
	 */
	public int[] getChars() {
		return chars;
	}

	/**
	 * Gets the number of symbols on this Card.
	 * 
	 * @return the number field
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Gets the color of this Card.
	 * 
	 * @return the color field
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Gets the shape of this card.
	 * 
	 * @return the shape field
	 */
	public int getShape() {
		return shape;
	}

	/**
	 * Gets the shade of this Card.
	 * 
	 * @return the shade field
	 */
	public int getShade() {
		return shade;
	}
}
