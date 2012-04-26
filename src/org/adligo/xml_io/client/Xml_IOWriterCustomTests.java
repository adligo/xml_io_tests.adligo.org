package org.adligo.xml_io.client;

import org.adligo.models.params.client.XMLBuilder;
import org.adligo.tests.ATest;

public class Xml_IOWriterCustomTests extends ATest {

	public void testCustomModelToXml() {
		CustomTestModel ctm = new CustomTestModel();
		
		Xml_IOSettings settings = new Xml_IOSettings();
		CustomTestModelConverter.setUp(settings);
		
		Xml_IOWriter writer = new Xml_IOWriter();
		String xml = writer.writeXml(ctm, settings);
		assertEquals("<ctm a=\"1\" b=\"2\"/>" 
				+ XMLBuilder.DOS_LINE_FEED, xml);
	}
}
