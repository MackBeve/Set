import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class SetModelTest {

	private SetModel model;

	@Before
	public void setUp() {
		model = new SetModel(false);
	}

	@Test
	public void testSetUp() throws Exception {
		assertEquals(12, model.getNumCards());
		assertEquals(69, model.getNumDeck());
	}

	@Test
	public void testConstructCards() {
		ArrayList<Card> deck = model.constructCards();
		for (int i = 0; i < 81; i++) {
			for (int j = 0; j < 81; j++) {
				if (i != j) {
					assertNotSame(deck.get(i), deck.get(j));
				}
			}
		}
	}

	@Test
	public void testIsSet() {
		Card a = new Card(0, 0, 0, 0);
		Card b = new Card(1, 1, 1, 1);
		Card c = new Card(2, 2, 2, 2);
		Card d = new Card(0, 1, 2, 0);
		assertTrue(model.isSet(a, a, a));
		assertTrue(model.isSet(a, b, c));
		assertFalse(model.isSet(a, b, d));
	}

	@Test
	public void testGetNumSets() {
		ArrayList<Card> boardA = new ArrayList<Card>();
		boardA.add(new Card(0, 0, 0, 0));
		assertEquals(0, model.getNumSets(boardA));
		ArrayList<Card> boardB = new ArrayList<Card>();
		boardB.add(new Card(0, 0, 0, 0));
		boardB.add(new Card(1, 1, 1, 1));
		boardB.add(new Card(2, 2, 2, 2));
		assertEquals(1, model.getNumSets(boardB));
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(0, 0, 0, 0));
		board.add(new Card(1, 1, 1, 1));
		board.add(new Card(2, 2, 2, 2));
		board.add(new Card(0, 1, 1, 1));
		board.add(new Card(1, 2, 2, 2));
		board.add(new Card(2, 0, 0, 0));
		board.add(new Card(1, 0, 1, 1));
		board.add(new Card(2, 1, 2, 2));
		board.add(new Card(0, 2, 0, 0));
		board.add(new Card(1, 1, 0, 1));
		board.add(new Card(2, 2, 1, 2));
		board.add(new Card(0, 0, 2, 0));
		assertEquals(4, model.getNumSets(board));
	}

	@Test
	public void testAddCards() {
		model.addCards();
		assertEquals(15, model.getNumCards());
		assertEquals(66, model.getNumDeck());
		for (int i = 0; i < model.getBoard().size(); i++) {
			for (int j = 0; j < model.getBoard().size(); j++) {
				if (i != j) {
					assertNotSame(model.getBoard().get(i), model.getBoard()
							.get(j));
				}
			}
		}
		for (int i = 0; i < model.getBoard().size(); i++) {
			for (int j = 0; j < model.getDeck().size(); j++) {
				assertNotSame(model.getBoard().get(i), model.getDeck().get(j));
			}
		}
	}

	@Test
	public void testFindCard() {
		SetModel set = new SetModel();
		assertEquals(81, set.getNumDeck());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						int[] cardOne = {i, j, k, l};
						assertEquals(Arrays.toString(set.findCard(cardOne).getChars()), 
								Arrays.toString(cardOne));
					}
				}
			}
		}
	}

	@Test
	public void testFindSet() {
		SetModel set = new SetModel();
		assertEquals(81, set.getNumDeck());
		ArrayList<Card> temp = set.findSet(set.getDeck().get(0));
		assertTrue(set.isSet(temp.get(0), temp.get(1), temp.get(2)));
	}

	@Test
	public void testSixSet() {
		SetModel set = new SetModel(true);
		assertEquals(69, set.getNumDeck());
		assertEquals(12, set.getNumCards());
		ArrayList<Card> temp = set.getDeck();
		assertEquals(12, set.getBoard().size());
		assertTrue(5<=set.getNumSets());
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (j != i) {
					assertNotSame(temp.get(i), temp.get(j));
				}
			}
		}
	}
}
