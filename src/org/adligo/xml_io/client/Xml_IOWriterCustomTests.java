package org.adligo.xml_io.client;

import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;

public class Xml_IOWriterCustomTests extends ATest {

	public void testCustomModelToXml() {
		CustomSimpleModel ctm = new CustomSimpleModel();
		
		Xml_IOSettings settings = new Xml_IOSettings();
		CustomSimpleModelConverter.setUp(settings);
		
		Xml_IOWriter writer = new Xml_IOWriter();
		String xml = writer.writeXml(ctm, settings);
		assertEquals("<ctm a=\"1\" b=\"2\"/>" 
				+ XMLBuilder.DOS_LINE_FEED, xml);
	}
	
	
	public void testComplexCustomModelToXml() {
		CustomComplexModel ctm = new CustomComplexModel();
		
		Xml_IOSettings settings = new Xml_IOSettings();
		CustomComplexModelConverter.setUp(settings);
		
		Xml_IOWriter writer = new Xml_IOWriter();
		String xml = writer.writeXml(ctm, settings);
		assertEquals(
				"<ccm id=\"1\" sid=\"2\" lid=\"3\" did=\"4.4\" fid=\"5.5\" chars=\"de\" bytes=\"AAEC\" bools=\"ft\">" 
				+ XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + "<L n=\"stringsList\">" + XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>ab</s>" + XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>ac</s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<L n=\"stringsArray\">"  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>ba</s>"  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>bb</s>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "</L>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "<m n=\"keyValues\">"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<k>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>1</i>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>a</s>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "</k>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<k>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<i>2</i>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<s>b</s>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "</k>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "</m>"  + XMLBuilder.DOS_LINE_FEED
		      	+ "</ccm>" + XMLBuilder.DOS_LINE_FEED, xml);
		//System.out.println(xml);
	}
}
