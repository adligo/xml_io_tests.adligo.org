package org.adligo.xml_io.client.schema;

import org.adligo.tests.ATest;

public class Xml_IOSchemaTests extends ATest {

	public void testSchemaString() {
		String result = Xml_IOSchema.SCHEMA.getXmlString();
		System.out.println(result);
		
	}
}
