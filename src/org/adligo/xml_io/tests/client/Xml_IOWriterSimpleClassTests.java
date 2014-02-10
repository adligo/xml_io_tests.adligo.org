package org.adligo.xml_io.tests.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;
import org.adligo.xml_io.client.Xml_IOWriter;

public class Xml_IOWriterSimpleClassTests extends ATest {
	private static final Log log = LogFactory.getLog(Xml_IOWriterSimpleClassTests.class);
	
	public void testCharacter() {
		Xml_IOWriter writer = new Xml_IOWriter();
		
		String result = writer.writeXml('H');
		assertEquals(MockConstants .HEADER + "C" + MockConstants.HEADER_2 + "H</a:C>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = writer.writeXml('<');
		assertEquals(MockConstants .HEADER + "C" + MockConstants.HEADER_2 + "&lt;</a:C>" + XMLBuilder.DOS_LINE_FEED, result);
		
	}
	
	public void testString() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml("Hey");
		assertEquals(MockConstants .HEADER + "s" + MockConstants.HEADER_2 + "Hey</a:s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey<1");
		assertEquals(MockConstants .HEADER + "s" + MockConstants.HEADER_2 + "Hey&lt;1</a:s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey>1");
		assertEquals(MockConstants .HEADER + "s" + MockConstants.HEADER_2 + "Hey&gt;1</a:s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey&1");
		assertEquals(MockConstants .HEADER + "s" + MockConstants.HEADER_2 + "Hey&amp;1</a:s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey\"1");
		assertEquals(MockConstants .HEADER + "s" + MockConstants.HEADER_2 + "Hey&quot;1</a:s>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testBoolean() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml(true);
		assertEquals(MockConstants .HEADER + "b" + MockConstants.HEADER_2 + "t</a:b>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(false);
		assertEquals(MockConstants .HEADER + "b" + MockConstants.HEADER_2 + "f</a:b>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testPrimitiveNumbers() throws Exception {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml(new Integer(1));
		//FileWriter.writeFile("text.xml", result);
		assertEquals(MockConstants .HEADER + "i" + MockConstants.HEADER_2 + "1</a:i>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Long.MAX_VALUE);
		assertEquals(MockConstants .HEADER + "l" + MockConstants.HEADER_2 + "9223372036854775807</a:l>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Double.MIN_VALUE);
		assertEquals(MockConstants .HEADER + "d" + MockConstants.HEADER_2 + "4.9E-324</a:d>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Float.MAX_VALUE);
		assertEquals(MockConstants .HEADER + "f" + MockConstants.HEADER_2 + "3.4028235E38</a:f>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Short.MIN_VALUE);
		assertEquals(MockConstants .HEADER + "S" + MockConstants.HEADER_2 + "-32768</a:S>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testBigNumbers() {
		Xml_IOWriter writer = new Xml_IOWriter();
		
		String text = "100" + Integer.MAX_VALUE;
		Object result = writer.writeXml(new BigInteger(text));
		assertEquals(MockConstants .HEADER + "I" + MockConstants.HEADER_2 + text + "</a:I>"  + XMLBuilder.DOS_LINE_FEED, result);
		
		text = "1.0049E-321";
		result = writer.writeXml(new BigDecimal(text));
		assertEquals(MockConstants .HEADER + "D" + MockConstants.HEADER_2 +text + "</a:D>"  + XMLBuilder.DOS_LINE_FEED, result);
		
	}
	
	public void testByte() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml((byte) 1);
		assertEquals(MockConstants .HEADER + "B" + MockConstants.HEADER_2 + "AQ==</a:B>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml((byte) 0);
		assertEquals(MockConstants .HEADER + "B" + MockConstants.HEADER_2 + "AA==</a:B>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testCollections() {
		Xml_IOWriter builder = new Xml_IOWriter();
		//can't do enum with out a enum impl
		
		//set screws with ordering
		HashSet<String> set = new HashSet<String>() ;
		set.add("a");
		set.add("b");
		set.add("c");
		
		String result = builder.writeXml(set);
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2 + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
		
		//these all seem to keep order
		assertCollection(builder, new LinkedHashSet<String>());
		assertCollection(builder, new LinkedList<String>());
		assertCollection(builder, new ArrayList<String>());
		assertCollection(builder, new Stack<String>());
		assertCollection(builder, new PriorityQueue<String>());
		assertCollection(builder, new TreeSet<String>());
		assertCollection(builder, new Vector<String>());
	}

	private void assertCollection(Xml_IOWriter builder, Collection<String> list) {
		list.add("a");
		list.add("b");
		list.add("c");
		
		String result = builder.writeXml(list);
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2 + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testMaps() {
		Xml_IOWriter builder = new Xml_IOWriter();
		//can't do enum with out a enum impl
	
		
		//these all seem to keep order
		assertMapWithOrder(builder, new LinkedHashMap<String, Integer>());
		assertMapWithOrder(builder, new TreeMap<String, Integer>());
	}

	private void assertMapWithOrder(Xml_IOWriter builder, Map<String, Integer> map) {
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		String result = builder.writeXml(map);
		assertEquals(MockConstants .HEADER + "m" + MockConstants.HEADER_2 +  XMLBuilder.DOS_LINE_FEED
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
				+ "</a:m>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testHashMap() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		String result = builder.writeXml(map);
		assertEquals(MockConstants .HEADER + "m" + MockConstants.HEADER_2 +  XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>2</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>3</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>1</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:m>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testIdentityHashMap() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		IdentityHashMap<String, Integer> map = new IdentityHashMap<String, Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		//the identity hash map screws with order, it seems pretty random
		String result = builder.writeXml(map);
		assertTrue("The a tag should be included", result.indexOf(
				XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>1</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED) >= 1);
		assertTrue("The a tag should be included", result.indexOf(
				XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>3</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED) >= 1);
		assertTrue("The a tag should be included", result.indexOf(
				XMLBuilder.SPACE_INDENT + "<a:k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>2</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:k>" + XMLBuilder.DOS_LINE_FEED) >= 1);
		
	}
	
	public void testStringArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new String[] {"a","b","c"});
		
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:s>c</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}

	public void testCharArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new char[] {'h','b'});
		
		assertEquals(MockConstants .HEADER + "c" + MockConstants.HEADER_2  + "hb</a:c>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testBooleanArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new boolean[] {true, false});
		
		assertEquals(MockConstants .HEADER + "A" + MockConstants.HEADER_2  +"tf</a:A>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testShortArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new short[] {3, 2});
		
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2  +  XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:S>3</a:S>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:S>2</a:S>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testIntArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new int[] {3, 2});
		
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:i>3</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:i>2</a:i>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testLongArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new long[] {3, 2});
		
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:l>3</a:l>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:l>2</a:l>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testFloatArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new float[] {3, 2});
		
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2 + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:f>3.0</a:f>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:f>2.0</a:f>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testDoubleArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new double[] {3, 2});
		
		assertEquals(MockConstants .HEADER + "L" + MockConstants.HEADER_2 + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:d>3.0</a:d>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:d>2.0</a:d>" + XMLBuilder.DOS_LINE_FEED
				+ "</a:L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testByteArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new byte[] {3, 2});
		
		assertEquals(MockConstants .HEADER + "a" + MockConstants.HEADER_2  + "AwI=</a:a>" + XMLBuilder.DOS_LINE_FEED
				, result);
	}
}
