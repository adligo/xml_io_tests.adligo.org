package org.adligo.xml_io.client;

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

public class Xml_IOWriterSimpleClassTests extends ATest {
	private static final Log log = LogFactory.getLog(Xml_IOWriterSimpleClassTests.class);
	
	public void testCharacter() {
		Xml_IOWriter writer = new Xml_IOWriter();
		
		String result = writer.writeXml('H');
		assertEquals("<C>H</C>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = writer.writeXml('<');
		assertEquals("<C>&lt;</C>" + XMLBuilder.DOS_LINE_FEED, result);
		
	}
	
	public void testString() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml("Hey");
		assertEquals("<s>Hey</s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey<1");
		assertEquals("<s>Hey&lt;1</s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey>1");
		assertEquals("<s>Hey&gt;1</s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey&1");
		assertEquals("<s>Hey&amp;1</s>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml("Hey\"1");
		assertEquals("<s>Hey&quot;1</s>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testBoolean() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml(true);
		assertEquals("<b>t</b>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(false);
		assertEquals("<b>f</b>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testPrimitiveNumbers() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml(new Integer(1));
		assertEquals("<i>1</i>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Long.MAX_VALUE);
		assertEquals("<l>9223372036854775807</l>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Double.MIN_VALUE);
		assertEquals("<d>4.9E-324</d>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Float.MAX_VALUE);
		assertEquals("<f>3.4028235E38</f>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(Short.MIN_VALUE);
		assertEquals("<S>-32768</S>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testBigNumbers() {
		Xml_IOWriter writer = new Xml_IOWriter();
		
		String text = "100" + Integer.MAX_VALUE;
		Object result = writer.writeXml(new BigInteger(text));
		assertEquals("<I>" + text + "</I>"  + XMLBuilder.DOS_LINE_FEED, result);
		
		text = "1.0049E-321";
		result = writer.writeXml(new BigDecimal(text));
		assertEquals("<D>" + text + "</D>"  + XMLBuilder.DOS_LINE_FEED, result);
		
	}
	
	public void testByte() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		String result = builder.writeXml((byte) 1);
		assertEquals("<B>AQ==</B>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml((byte) 0);
		assertEquals("<B>AA==</B>" + XMLBuilder.DOS_LINE_FEED, result);
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
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
		
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
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
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
		assertEquals("<m>" + XMLBuilder.DOS_LINE_FEED
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
				+ "</m>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testHashMap() {
		Xml_IOWriter builder = new Xml_IOWriter();
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		String result = builder.writeXml(map);
		assertEquals("<m>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>2</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>3</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>1</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED
				+ "</m>" + XMLBuilder.DOS_LINE_FEED, result);
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
				XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>1</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED) >= 1);
		assertTrue("The a tag should be included", result.indexOf(
				XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>3</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED) >= 1);
		assertTrue("The a tag should be included", result.indexOf(
				XMLBuilder.SPACE_INDENT + "<k>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>2</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</k>" + XMLBuilder.DOS_LINE_FEED) >= 1);
		
	}
	
	public void testStringArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new String[] {"a","b","c"});
		
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>a</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>b</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<s>c</s>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
	}

	public void testCharArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new char[] {'h','b'});
		
		assertEquals("<c>hb</c>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testBooleanArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new boolean[] {true, false});
		
		assertEquals("<A>tf</A>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testShortArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new short[] {3, 2});
		
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<S>3</S>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<S>2</S>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testIntArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new int[] {3, 2});
		
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<i>3</i>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<i>2</i>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testLongArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new long[] {3, 2});
		
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<l>3</l>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<l>2</l>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testFloatArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new float[] {3, 2});
		
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<f>3.0</f>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<f>2.0</f>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testDoubleArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new double[] {3, 2});
		
		assertEquals("<L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<d>3.0</d>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<d>2.0</d>" + XMLBuilder.DOS_LINE_FEED
				+ "</L>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testByteArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		String result = writer.writeXml(new byte[] {3, 2});
		
		assertEquals("<a>AwI=</a>" + XMLBuilder.DOS_LINE_FEED
				, result);
	}
}
