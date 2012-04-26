package org.adligo.xml_io.client;

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

public class Xml_IOReaderSimpleClassTests extends ATest {
	private static final Log log = LogFactory.getLog(Xml_IOReaderSimpleClassTests.class);
	
	
	public void testCharacter() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml("<C>H</C>");
		assertEquals('H', result);
		
		result = builder.readXml( "<C>&gt;</C>" );
		assertEquals('>', result);
		
	}
	
	public void testString() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml("<s>Hey</s>");
		assertEquals("Hey", result);
		
		result = builder.readXml( "<s>Hey&lt;1</s>" );
		assertEquals("Hey<1", result);
		
		result = builder.readXml( "<s>Hey&gt;1</s>" );
		assertEquals("Hey>1", result);
		
		result = builder.readXml("<s>Hey&amp;1</s>" );
		assertEquals("Hey&1", result);
		
		result = builder.readXml("<s>Hey&quot;1</s>");
		assertEquals( "Hey\"1", result);
		
	}
	
	public void testBoolean() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml("<b>t</b>");
		assertEquals(true ,result);
		
		result = builder.readXml("<b>f</b>");
		assertEquals(false, result);
	}
	
	public void testBigNumbers() {
		Xml_IOReader builder = new Xml_IOReader();
		
		String text = "100" + Integer.MAX_VALUE;
		Object result = builder.readXml("<I>" + text + "</I>");
		assertEquals(new BigInteger(text), result);
		
		text = "100" + Double.MIN_VALUE;
		result = builder.readXml("<D>" + text + "</D>");
		assertEquals(new BigDecimal(text), result);
		
	}
	

	public void testPrimitiveNumbers() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml("<i>1</i>");
		assertEquals(1, result);
		
		result = builder.readXml("<l>9223372036854775807</l>");
		assertEquals(Long.MAX_VALUE, result);
		
		result = builder.readXml("<d>4.9E-324</d>");
		assertEquals(Double.MIN_VALUE, result);
		
		result = builder.readXml("<f>3.4028235E38</f>");
		assertEquals(Float.MAX_VALUE, result);
		
		result = builder.readXml("<S>-32768</S>");
		assertEquals(Short.MIN_VALUE, result);
	}
	
	public void testByte() {
		Xml_IOReader builder = new Xml_IOReader();
		
		Object result = builder.readXml("<B>AQ==</B>");
		assertEquals((byte) 1, result);
		
		result = builder.readXml("<B>AA==</B>");
		assertEquals((byte) 0 , result);
	}
	
	@SuppressWarnings("unchecked")
	public void testCollections() {
		Xml_IOReader reader = new Xml_IOReader();
		String xml = "<L>" + XMLBuilder.DOS_LINE_FEED
					+ XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
					+ XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
					+ XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
					+ "</L>" + XMLBuilder.DOS_LINE_FEED;
		

		Object result = reader.readXml(xml);
		assertTrue(result instanceof Collection<?>);
		Collection<String> resultCollection = (Collection<String>) result;
		Iterator<String> it = resultCollection.iterator();
		
		assertEquals("a", it.next());
		assertEquals("b", it.next());
		assertEquals("c", it.next());
	}

	public void testMap() {
		String xml = "<m>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>1</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>2</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>3</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED
				+ "</m>" + XMLBuilder.DOS_LINE_FEED;
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
		Object result = reader.readXml("<a>AwI=</a>");
		assertTrue(result instanceof byte []);
		byte [] bytes = (byte []) result;
		assertTrue(bytes.length == 2);
		assertEquals((byte) 3, bytes[0]);
		assertEquals((byte) 2, bytes[1]);
	}
	
	public void testCharArray() {
		Xml_IOReader reader = new Xml_IOReader();
		Object result = reader.readXml("<c>hb</c>");
		assertTrue(result instanceof char []);
		char [] chars = (char []) result;
		assertTrue(chars.length == 2);
		assertEquals('h', chars[0]);
		assertEquals('b', chars[1]);
	}
	
	public void testBooleanArray() {
		Xml_IOReader reader = new Xml_IOReader();
		Object result = reader.readXml("<A>tf</A>");
		assertTrue(result instanceof boolean []);
		boolean [] bools = (boolean []) result;
		assertTrue(bools.length == 2);
		assertEquals(true, bools[0]);
		assertEquals(false, bools[1]);
		
	}
}
