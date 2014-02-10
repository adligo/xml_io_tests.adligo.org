package org.adligo.xml_io.tests.client;

import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;
import org.adligo.xml_io.client.Xml_IOSettingsMutant;
import org.adligo.xml_io.client.Xml_IOWriter;

public class Xml_IOWriterCustomTests extends ATest {

	public void testCustomModelToXml() {
		CustomSimpleModel ctm = new CustomSimpleModel();
		
		Xml_IOSettingsMutant settings = new Xml_IOSettingsMutant();
		CustomSimpleModelConverter.setUp(settings);
		
		Xml_IOWriter writer = new Xml_IOWriter();
		String xml = writer.writeXml(ctm, settings);
		assertEquals(MockConstants .CUSTOM_HEADER + "ctm" + MockConstants.CUSTOM_HEADER_2_NOEND  +" a=\"1\" b=\"2\"/>" 
				+ XMLBuilder.DOS_LINE_FEED, xml);
	}
	
	
	public void testComplexCustomModelToXml() {
		CustomComplexModel ctm = new CustomComplexModel();
		
		Xml_IOSettingsMutant settings = new Xml_IOSettingsMutant();
		CustomComplexModelConverter.setUp(settings);
		
		Xml_IOWriter writer = new Xml_IOWriter();
		String xml = writer.writeXml(ctm, settings);
		assertEquals(MockConstants .CUSTOM_HEADER + "ccm" + MockConstants.CUSTOM_HEADER_2_NOEND +
				" id=\"1\" sid=\"2\" lid=\"3\" did=\"4.4\" fid=\"5.5\" chars=\"de\" bytes=\"AAEC\" bools=\"ft\">" 
				+ XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + "<a:L n=\"stringsList\">" + XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>ab</a:s>" + XMLBuilder.DOS_LINE_FEED 
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>ac</a:s>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "</a:L>" + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + "<a:L n=\"stringsArray\">"  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>ba</a:s>"  + XMLBuilder.DOS_LINE_FEED
				+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>bb</a:s>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "</a:L>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "<a:m n=\"keyValues\">"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:k>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>1</a:i>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>a</a:s>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "</a:k>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:k>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:i>2</a:i>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "<a:s>b</a:s>"  + XMLBuilder.DOS_LINE_FEED
		        + XMLBuilder.SPACE_INDENT + XMLBuilder.SPACE_INDENT + "</a:k>"  + XMLBuilder.DOS_LINE_FEED
		      	+ XMLBuilder.SPACE_INDENT + "</a:m>"  + XMLBuilder.DOS_LINE_FEED
		      	+ "</b:ccm>" + XMLBuilder.DOS_LINE_FEED, xml);
		//System.out.println(xml);
	}
}
