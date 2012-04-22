package org.adligo.xml_io.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;
import org.adligo.xml_io.client.XmlOutputBuilder;

public class XmlOutputSimpleInstanceTests extends ATest {
	private static final Log log = LogFactory.getLog(XmlOutputSimpleInstanceTests.class);
	
	
	public void testString() {
		XmlOutputBuilder builder = new XmlOutputBuilder();
		
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
		XmlOutputBuilder builder = new XmlOutputBuilder();
		
		String result = builder.writeXml(true);
		assertEquals("<b>t</b>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml(false);
		assertEquals("<b>f</b>" + XMLBuilder.DOS_LINE_FEED, result);
	}
	
	public void testNumbers() {
		XmlOutputBuilder builder = new XmlOutputBuilder();
		
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
	
	public void testByte() {
		XmlOutputBuilder builder = new XmlOutputBuilder();
		
		String result = builder.writeXml((byte) 1);
		assertEquals("<B>AQ==</B>" + XMLBuilder.DOS_LINE_FEED, result);
		
		result = builder.writeXml((byte) 0);
		assertEquals("<B>AA==</B>" + XMLBuilder.DOS_LINE_FEED, result);
	}
}
