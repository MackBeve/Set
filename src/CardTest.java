import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CardTest {
	
	private Card a;
	private Card b;
	private Card c;
	private Card d;
	
	@BeforeEach
	public void setUp(){
		a = new Card(0, 0, 0, 0);
		b = new Card(1, 1, 1, 1);
		c = new Card(0, 1, 2, 0);
		d = new Card();
	}
	
	@Test
	public void testSetUp(){
		assertTrue(d.getNumber()<3);
		assertTrue(d.getNumber()>=0);
		assertTrue(d.getColor()<3);
		assertTrue(d.getColor()>=0);
		assertTrue(d.getShape()<3);
		assertTrue(d.getShape()>=0);
		assertTrue(d.getShade()<3);
		assertTrue(d.getShade()>=0);
	}
	
	@Test
	public void testToString(){
		assertEquals("[0, 0, 0, 0]", a.toString());
		assertEquals("[1, 1, 1, 1]", b.toString());
		assertEquals("[0, 1, 2, 0]", c.toString());
	}
}
