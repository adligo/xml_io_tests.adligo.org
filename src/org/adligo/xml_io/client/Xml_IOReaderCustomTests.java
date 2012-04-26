package org.adligo.xml_io.client;

import java.math.BigDecimal;

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

}
