package org.adligo.xml_io.client.schema;

import org.adligo.models.params.client.I_XMLBuilder;
import org.adligo.tests.ATest;

public class Xml_IOSchemaTests extends ATest {

	public void testSchemaString() throws Exception {
		SchemaXmlBuilder builder = new SchemaXmlBuilder();
		String result = builder.build(Xml_IOSchema.SCHEMA);
		//the following is the real test, then open in a xml schema editor and make sure it looks ok;
		/*
		FileWriter.write("xml_io.xsd", result);
		*/
		assertEqualsInternal(new String(new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + I_XMLBuilder.DOS_LINE_FEED +
				"<schema xmlns=\"http://www.w3.org/2001/XMLSchema\"" + I_XMLBuilder.DOS_LINE_FEED +
				" targetNamespace=\"http://www.adligo.org/xml_io\"" + I_XMLBuilder.DOS_LINE_FEED +
				" xmlns:a=\"http://www.adligo.org/xml_io\"" + I_XMLBuilder.DOS_LINE_FEED +
				" elementFormDefault=\"qualified\">" + I_XMLBuilder.DOS_LINE_FEED +
				"" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"f\" type=\"float\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"D\" type=\"decimal\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"d\" type=\"double\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"b\" type=\"a:Boolean\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"c\" type=\"string\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"A\" type=\"a:BooleanArray\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"B\" type=\"base64Binary\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"a\" type=\"base64Binary\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"C\" type=\"a:Character\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				
				"   <element name=\"L\" type=\"a:Collection\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"l\" type=\"long\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"m\" type=\"a:Map\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"I\" type=\"integer\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"k\" type=\"a:KeyValue\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"i\" type=\"int\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"s\" type=\"string\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <element name=\"S\" type=\"short\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"" + I_XMLBuilder.DOS_LINE_FEED +
				"   <simpleType name=\"BooleanArray\">" + I_XMLBuilder.DOS_LINE_FEED +
				"      <restriction base=\"string\">" + I_XMLBuilder.DOS_LINE_FEED +
				"         <pattern value=\"string\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"      </restriction>" + I_XMLBuilder.DOS_LINE_FEED +
				"   </simpleType>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <simpleType name=\"Boolean\">" + I_XMLBuilder.DOS_LINE_FEED +
				"      <restriction base=\"string\">" + I_XMLBuilder.DOS_LINE_FEED +
				"         <pattern value=\"string\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"         <maxLength value=\"1\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"      </restriction>" + I_XMLBuilder.DOS_LINE_FEED +
				"   </simpleType>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <simpleType name=\"Character\">" + I_XMLBuilder.DOS_LINE_FEED +
				"      <restriction base=\"string\">" + I_XMLBuilder.DOS_LINE_FEED +
				"         <maxLength value=\"1\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"      </restriction>" + I_XMLBuilder.DOS_LINE_FEED +
				"   </simpleType>" + I_XMLBuilder.DOS_LINE_FEED +
				"" + I_XMLBuilder.DOS_LINE_FEED +
				"   <complexType name=\"Collection\">" + I_XMLBuilder.DOS_LINE_FEED +
				"      <sequence>" + I_XMLBuilder.DOS_LINE_FEED +
				"         <any minOccurs=\"1\" maxOccurs=\"unbounded\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"      </sequence>" + I_XMLBuilder.DOS_LINE_FEED +
				"   </complexType>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <complexType name=\"Map\">" + I_XMLBuilder.DOS_LINE_FEED +
				"      <sequence>" + I_XMLBuilder.DOS_LINE_FEED +
				"         <element name=\"k\" type=\"a:KeyValue\" minOccurs=\"1\" maxOccurs=\"unbounded\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"      </sequence>" + I_XMLBuilder.DOS_LINE_FEED +
				"   </complexType>" + I_XMLBuilder.DOS_LINE_FEED +
				"   <complexType name=\"KeyValue\">" + I_XMLBuilder.DOS_LINE_FEED +
				"      <sequence>" + I_XMLBuilder.DOS_LINE_FEED +
				"         <any minOccurs=\"1\" maxOccurs=\"2\"/>" + I_XMLBuilder.DOS_LINE_FEED +
				"      </sequence>" + I_XMLBuilder.DOS_LINE_FEED +
				"   </complexType>" + I_XMLBuilder.DOS_LINE_FEED +
				"" + I_XMLBuilder.DOS_LINE_FEED +
				"</schema>" + I_XMLBuilder.DOS_LINE_FEED).getBytes("UTF-8")), result);
		
	}
	
	private void assertEqualsInternal(String a, String b) {
		assertEquals(a, b);
		/*
		assertEquals(a.length(), b.length());
		char [] aChars = a.toCharArray();
		char [] bChars = b.toCharArray();
		for (int i = 0; i < bChars.length; i++) {
			char aC = aChars[i];
			char bC = bChars[i];
			assertEquals("The characters should match at character " + i, aC, bC);
		}
		*/
	}
}
