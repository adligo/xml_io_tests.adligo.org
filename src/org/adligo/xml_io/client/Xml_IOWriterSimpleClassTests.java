package org.adligo.xml_io.client;

import java.math.BigDecimal;
import java.math.BigInteger;

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
}
