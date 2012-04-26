package org.adligo.xml_io.client;

import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;

public class Xml_IOWriterCustomTests extends ATest {

	public void testCustomModelToXml() {
		CustomModel ctm = new CustomModel();
		
		Xml_IOSettings settings = new Xml_IOSettings();
		CustomModelConverter.setUp(settings);
		
		Xml_IOWriter writer = new Xml_IOWriter();
		String xml = writer.writeXml(ctm, settings);
		assertEquals("<ctm a=\"1\" b=\"2\"/>" 
				+ XMLBuilder.DOS_LINE_FEED, xml);
	}
}
