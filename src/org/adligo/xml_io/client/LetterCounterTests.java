package org.adligo.xml_io.client;

import org.adligo.tests.ATest;

public class LetterCounterTests extends ATest {

	public void testLetterCounter() {
		LetterCounter lc = new LetterCounter();
		assertEquals("a", lc.getNextId());
		assertEquals("b", lc.getNextId());
		assertEquals("c", lc.getNextId());
		for (int i = 0; i < 22; i++) {
			lc.getNextId();
		}
		assertEquals("z", lc.getNextId());
		assertEquals("A", lc.getNextId());
		for (int i = 0; i < 24; i++) {
			lc.getNextId();
		}
		assertEquals("Z", lc.getNextId());
		assertEquals("aa", lc.getNextId());
		for (int i = 0; i < 50; i++) {
			lc.getNextId();
		}
		assertEquals("aZ", lc.getNextId());
		assertEquals("ba", lc.getNextId());
		for (int i = 0; i < 50; i++) {
			lc.getNextId();
		}
		assertEquals("bZ", lc.getNextId());
		assertEquals("ca", lc.getNextId());
	}
	
	public void testFindDigitToChange() {
		LetterCounter lc = new LetterCounter();
		assertEquals(0, lc.findDigitToChange("a".toCharArray()));
		assertEquals(0, lc.findDigitToChange("Z".toCharArray()));
		assertEquals(0, lc.findDigitToChange("aa".toCharArray()));
		assertEquals(0, lc.findDigitToChange("aA".toCharArray()));
		assertEquals(1, lc.findDigitToChange("aZ".toCharArray()));
	}
}
