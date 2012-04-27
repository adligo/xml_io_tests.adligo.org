package org.adligo.xml_io.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;

public class Xml_IOReaderCustomTests  extends ATest {

	public void testCustomModelToXml() {
		
		Xml_IOSettings settings = new Xml_IOSettings();
		CustomSimpleModelConverter.setUp(settings);
		
		
		Xml_IOReader reader = new Xml_IOReader();
		Object obj = reader.readXml("<ctm a=\"3\" b=\"4\"/>", settings);
		assertTrue(obj instanceof CustomSimpleModel);
		CustomSimpleModel mod = (CustomSimpleModel) obj;
		assertEquals(new BigDecimal(3), mod.getA());
		assertEquals(new BigDecimal(4), mod.getB());
		
	}

	
	public void testComplexCustomModelToXml() {
		Xml_IOSettings settings = new Xml_IOSettings();
		CustomComplexModelConverter.setUp(settings);
		
		
		Xml_IOReader reader = new Xml_IOReader();
		Object obj = reader.readXml(
				"<ccm id=\"9999\" sid=\"99\" lid=\"99999999\" did=\"99.99\" fid=\"9.9\" chars=\"ufda\" bytes=\"AAA=\" bools=\"t\">" 
				+ XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + "<L n=\"stringsList\">" + XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>1ab</s>" + XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>1ac</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<L n=\"stringsArray\">"  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>ba</s>"  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>bb</s>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "</L>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "<m n=\"keyValues\">"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<k>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>11</i>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>aa</s>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "</k>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<k>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>22</i>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>bb</s>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "</k>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "</m>"  + XMLBuilder.DOS_LINE_FEED
		      	+ "</ccm>" + XMLBuilder.DOS_LINE_FEED, settings);
		assertTrue(obj instanceof CustomComplexModel);
		CustomComplexModel mod = (CustomComplexModel) obj;
		assertEquals(9999, mod.getId());
		assertEquals((short) 99, mod.getSid());
		assertEquals(99999999L, mod.getLid());
		assertEquals(99.99, mod.getDid());
		assertEquals((float) 9.9, mod.getFid());
		
		byte [] bytes = mod.getBytes();
		assertEquals(2, bytes.length);
		assertEquals((byte) 0, bytes[0]);
		assertEquals((byte) 0, bytes[1]);
		
		char [] chars = mod.getChars();
		assertEquals(4, chars.length);
		assertEquals('u', chars[0]);
		assertEquals('f', chars[1]);
		assertEquals('d', chars[2]);
		assertEquals('a', chars[3]);
		
		boolean [] bools = mod.getBools();
		assertEquals(1, bools.length);
		assertTrue(bools[0]);
		
		String [] sa = mod.getStringsArray();
		assertTrue(sa.length == 2);
		assertEquals("ba", sa[0]);
		assertEquals("bb", sa[1]);
		
		List<String> stringList = mod.getStringsList();
		assertTrue(stringList.size() == 2);
		assertEquals("1ab", stringList.get(0));
		assertEquals("1ac", stringList.get(1));
		
		Map<Integer,String> kv = mod.getKeyValues();
		assertTrue(kv.size() == 2);
		assertEquals("aa", kv.get(11));
		assertEquals("bb", kv.get(22));
	}
}
