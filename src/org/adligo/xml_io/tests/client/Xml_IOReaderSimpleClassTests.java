package org.adligo.xml_io.tests.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;
import org.adligo.xml_io.client.Xml_IOReader;

public class Xml_IOReaderSimpleClassTests extends ATest {
	private static final Log log = LogFactory.getLog(Xml_IOReaderSimpleClassTests.class);
	
	
	public void testCharacter() {
		Xml_IOReader reader = new Xml_IOReader();
		
		Object result = reader.readXml(MockConstants.HEADER + "C" + MockConstants.HEADER_2 +  "H</a:C>");
		assertEquals('H', result);
		
		result = reader.readXml(MockConstants.HEADER + "C" + MockConstants.HEADER_2 +  "&gt;</a:C>" );
		assertEquals('>', result);
		
	}
	
	public void testString() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml(MockConstants.HEADER + "s" + MockConstants.HEADER_2 +  "Hey</a:s>");
		assertEquals("Hey", result);
		
		result = builder.readXml(MockConstants.HEADER + "s" + MockConstants.HEADER_2 +  "Hey&lt;1</a:s>" );
		assertEquals("Hey<1", result);
		
		result = builder.readXml(MockConstants.HEADER + "s" + MockConstants.HEADER_2 +  "Hey&gt;1</a:s>" );
		assertEquals("Hey>1", result);
		
		result = builder.readXml(MockConstants.HEADER + "s" + MockConstants.HEADER_2 +  "Hey&amp;1</a:s>" );
		assertEquals("Hey&1", result);
		
		result = builder.readXml(MockConstants.HEADER + "s" + MockConstants.HEADER_2 +  "Hey&quot;1</a:s>");
		assertEquals( "Hey\"1", result);
		
	}
	
	public void testBoolean() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml(MockConstants.HEADER + "b" + MockConstants.HEADER_2 +  "t</a:b>");
		assertEquals(true ,result);
		
		result = builder.readXml(MockConstants.HEADER + "b" + MockConstants.HEADER_2 +  "f</a:b>");
		assertEquals(false, result);
	}
	
	public void testBigNumbers() {
		Xml_IOReader builder = new Xml_IOReader();
		
		String text = "100" + Integer.MAX_VALUE;
		Object result = builder.readXml(MockConstants.HEADER + "I" + MockConstants.HEADER_2 + text + "</a:I>");
		assertEquals(new BigInteger(text), result);
		
		text = "100" + Double.MIN_VALUE;
		result = builder.readXml(MockConstants.HEADER + "D" + MockConstants.HEADER_2 + text + "</a:D>");
		assertEquals(new BigDecimal(text), result);
		
	}
	

	public void testPrimitiveNumbers() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml(MockConstants.HEADER + "i" + MockConstants.HEADER_2 +  "1</a:i>");
		assertEquals(1, result);
		
		result = builder.readXml(MockConstants.HEADER + "l" + MockConstants.HEADER_2 +  "9223372036854775807</a:l>");
		assertEquals(Long.MAX_VALUE, result);
		
		result = builder.readXml(MockConstants.HEADER + "d" + MockConstants.HEADER_2 +  "4.9E-324</a:d>");
		assertEquals(Double.MIN_VALUE, result);
		
		result = builder.readXml(MockConstants.HEADER + "f" + MockConstants.HEADER_2 +  "3.4028235E38</a:f>");
		assertEquals(Float.MAX_VALUE, result);
		
		result = builder.readXml(MockConstants.HEADER + "S" + MockConstants.HEADER_2 +  "-32768</a:S>");
		assertEquals(Short.MIN_VALUE, result);
	}
	
	public void testByte() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml(MockConstants.HEADER + "B" + MockConstants.HEADER_2 + "AQ==</a:B>");
		assertEquals((byte) 1, result);
		
		result = builder.readXml(MockConstants.HEADER + "B" + MockConstants.HEADER_2 + "AA==</a:B>");
		assertEquals((byte) 0 , result);
	}
	
	@SuppressWarnings("unchecked")
	public void testCollections() {
		Xml_IOReader reader = new Xml_IOReader();
		String xml = MockConstants.HEADER + "L" + MockConstants.HEADER_2 + XMLBuilder.DOS_LINE_FEED
					+ XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
					+ XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
					+ XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
					+ "</a:L>" + XMLBuilder.DOS_LINE_FEED;
		

		Object result = reader.readXml(xml);
		assertTrue(result instanceof Collection<?>);
		Collection<String> resultCollection = (Collection<String>) result;
		Iterator<String> it = resultCollection.iterator();
		
		assertEquals("a", it.next());
		assertEquals("b", it.next());
		assertEquals("c", it.next());
	}

	public void testMap() {
		String xml = MockConstants.HEADER + "m" + MockConstants.HEADER_2 + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>1</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>2</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>3</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:m>" + XMLBuilder.DOS_LINE_FEED;
		Xml_IOReader reader = new Xml_IOReader();
		Object result = reader.readXml(xml);
		assertTrue(result instanceof Map<?,?>);
		Map<String, Integer> map = (Map<String, Integer>) result;
		
		assertEquals(new Integer(1), map.get("a"));
		assertEquals(new Integer(2), map.get("b"));
		assertEquals(new Integer(3), map.get("c"));
		assertEquals(3, map.size());
	}
	
	
	public void testByteArray() {
		Xml_IOReader reader = new Xml_IOReader();
		Object result = reader.readXml(MockConstants.HEADER + "a" + MockConstants.HEADER_2 + "AwI=</a:a>");
		assertTrue(result instanceof byte []);
		byte [] bytes = (byte []) result;
		assertTrue(bytes.length == 2);
		assertEquals((byte) 3, bytes[0]);
		assertEquals((byte) 2, bytes[1]);
	}
	
	public void testCharArray() {
		Xml_IOReader reader = new Xml_IOReader();
		Object result = reader.readXml(MockConstants.HEADER + "c" + MockConstants.HEADER_2 + "hb</a:c>");
		assertTrue(result instanceof char []);
		char [] chars = (char []) result;
		assertTrue(chars.length == 2);
		assertEquals('h', chars[0]);
		assertEquals('b', chars[1]);
	}
	
	public void testBooleanArray() {
		Xml_IOReader reader = new Xml_IOReader();
		Object result = reader.readXml(MockConstants.HEADER + "A" + MockConstants.HEADER_2 + "tf</a:A>");
		assertTrue(result instanceof boolean []);
		boolean [] bools = (boolean []) result;
		assertTrue(bools.length == 2);
		assertEquals(true, bools[0]);
		assertEquals(false, bools[1]);
		
	}
}
