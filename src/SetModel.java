import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Contains all the pertinent information for a game of Set
 * 
 * @author Mack Beveridge April 15, 2015
 */

public class SetModel {

	public final int RED = 0;
	public final int GREEN = 1;
	public final int PURPLE = 2;
	public final int DIAMOND = 0;
	public final int OVAL = 1;
	public final int SQUIGGLE = 2;
	public final int SOLID = 0;
	public final int SHADED = 1;
	public final int CLEAR = 2;

	private ArrayList<Card> board;
	private ArrayList<Card> deck;

	/**
	 * Constructs an instance of the SetModel with a shuffled deck of 81
	 * distinct cards with 12 cards dealt as the initial board.
	 * 
	 * @param gameMode
	 *            True if it is Six-Set, false if it is normal
	 */
	public SetModel(boolean gameMode) {
		board = new ArrayList<Card>();
		deck = constructCards();
		if (!gameMode) {
			Collections.shuffle(deck);
			for (int i = 0; i < 12; i++) {
				board.add(deck.remove(0));
			}
		} else {
			board = sixSet();
		}
	}

	/**
	 * Creates and instance of set model with no board and a full deck. Only
	 * used in testing
	 */
	public SetModel() {
		deck = constructCards();
	}

	/**
	 * Creates all 81 individual cards that make up a Set deck.
	 * 
	 * @return An ArrayList, and of size 81, of Cards, all of which are
	 *         distinct.
	 */
	public ArrayList<Card> constructCards() {
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						temp.add(new Card(i, j, k, l));
					}
				}
			}
		}
		return temp;
	}

	/**
	 * Creates the board with 12 cards that have 6 or more set among them.
	 * 
	 * @return The 12 cards with at least 6 sets in them
	 */
	public ArrayList<Card> sixSet() {
		ArrayList<Card> six = new ArrayList<Card>();
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(findSet(deck.get((int) (Math.random() * deck.size()))));
		six.addAll(findSet(temp.get(0)));
		six.addAll(findSet(temp.get(1)));
		six.addAll(findSet(temp.get(2)));
		six.addAll(findSet(deck.get((int) (Math.random() * deck.size()))));
		for (int i = 0; i < six.size(); i++) {
			deck.remove(six.get(i));
			for (int j = 0; j < six.size(); j++) {
				if (i != j && six.get(i).equals(six.get(j))) {
					six.remove(j);
					j--;
				}
			}
		}
		Collections.shuffle(deck);
		int n = six.size();
		for (int i = 0; i < 12 - n; i++) {
			six.add(deck.remove(0));
		}
		return six;
	}

	/**
	 * Finds a set of three Cards including the given Card.
	 * 
	 * @param first
	 *            The first Card in the Set
	 * @return A set of three Cards
	 */
	public ArrayList<Card> findSet(Card first) {
		ArrayList<Card> set = new ArrayList<Card>();
		set.add(first);
		int[] cardOne = first.getChars();
		int[] cardTwo = new int[4];
		int[] cardThree = new int[4];
		for (int i = 0; i < 4; i++) {
			double n = Math.random();
			if (n < 0.33) {
				cardTwo[i] = cardOne[i];
				cardThree[i] = cardOne[i];
			} else if (n < 0.66) {
				cardTwo[i] = (cardOne[i] + 1) % 3;
				cardThree[i] = (cardOne[i] + 2) % 3;
			} else {
				cardTwo[i] = (cardOne[i] + 2) % 3;
				cardThree[i] = (cardOne[i] + 1) % 3;
			}
		}
		set.add(findCard(cardTwo));
		set.add(findCard(cardThree));
		return set;
	}

	/**
	 * Finds a Card in the deck with the specified characteristics.
	 * 
	 * @param chars
	 *            the characteristics of the Card
	 * @return the Card
	 */
	public Card findCard(int[] chars) {
		for (int i = 0; i < deck.size(); i++) {
			if (Arrays.equals(chars, deck.get(i).getChars())) {
				return deck.get(i);
			}
		}
		System.out.println("you fucked");
		return null;
	}

	/**
	 * Determines if three Cards are a Set.
	 * 
	 * @param a
	 *            A Card in the potential Set
	 * @param b
	 *            Another Card in the potential Set
	 * @param c
	 *            Yet another Card in the potential Set
	 * @return true if the three cards are a set and false if they are not
	 */
	public boolean isSet(Card a, Card b, Card c) {
		int n = 0;
		for (int i = 0; i < 4; i++) {
			if (a.getChars()[i] == b.getChars()[i]
					&& b.getChars()[i] == c.getChars()[i]) {
				n++;
			} else if (a.getChars()[i] != b.getChars()[i]
					&& b.getChars()[i] != c.getChars()[i]
					&& a.getChars()[i] != c.getChars()[i]) {
				n++;
			}
		}
		return n == 4;
	}

	/**
	 * Adds 3 cards to the board from the deck.
	 */
	public void addCards() {
		for (int i = 0; i < 3; i++) {
			board.add(deck.get(0));
			deck.remove(0);
		}
	}

	/**
	 * Calls getNumSets(ArrayList<Card> boardList) with the board asn the
	 * parameter.
	 * 
	 * @return
	 */
	public int getNumSets() {
		return getNumSets(board);
	}

	/**
	 * Gets the number of possible Sets that can be made out of the Cards
	 * currently on the board
	 * 
	 * @return the total number of possible Sets
	 */
	public int getNumSets(ArrayList<Card> boardList) {
		int n = 0;
		for (int i = 0; i < boardList.size(); i++) {
			for (int j = i + 1; j < boardList.size(); j++) {
				for (int k = +1; k < boardList.size(); k++) {
					if (i == j || i == k || j == k) {
						break;
					}
					if (isSet(boardList.get(i), boardList.get(j),
							boardList.get(k))) {
						n++;
					}
				}
			}
		}
		return n;
	}

	/**
	 * Returns a String representing all of the cards in the board
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < board.size() / (board.size() / 3); i++) {
			for (int j = 0; j < board.size() / 3; j++) {
				s += board.get(j + i * board.size() / 3).toString();
			}
			s += "\n";
		}
		return s;
	}

	/**
	 * Gets the number of Cards currently on the board
	 * 
	 * @return the number of Cards in the board field
	 */
	public int getNumCards() {
		int n = 0;
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i) != null) {
				n++;
			}
		}
		return n;
	}

	/**
	 * Gets the number of Cards left in the deck.
	 * 
	 * @return the size of the deck field
	 */
	public int getNumDeck() {
		return deck.size();
	}

	/**
	 * Gets the current board
	 * 
	 * @return an array containing all the Cards currently on the board.
	 */
	public ArrayList<Card> getBoard() {
		return board;
	}

	/**
	 * Gets the deck of Cards that haven't been in play
	 * 
	 * @return the deck field
	 */
	public ArrayList<Card> getDeck() {
		return deck;
	}
}