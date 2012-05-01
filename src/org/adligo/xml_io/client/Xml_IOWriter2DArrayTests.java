package org.adligo.xml_io.client;

import org.adligo.models.params.client.I_XMLBuilder;
import org.adligo.tests.ATest;

public class Xml_IOWriter2DArrayTests extends ATest {

	public void test2DArray() {
		Xml_IOWriter writer = new Xml_IOWriter();
		int [][] ia = new int [2][3];
		ia[0][0] = 0;
		ia[0][1] = 1;
		ia[0][2] = 2;
		
		ia[1][0] = 3;
		ia[1][1] = 4;
		ia[1][2] = 5;
		String xml = writer.writeXml(ia);
		assertEquals(MockConstants.HEADER + "L" + MockConstants.HEADER_2 + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + "<a:L>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + I_XMLBuilder.SPACE_INDENT + "<a:i>0</a:i>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + I_XMLBuilder.SPACE_INDENT + "<a:i>1</a:i>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + I_XMLBuilder.SPACE_INDENT + "<a:i>2</a:i>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + "</a:L>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + "<a:L>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + I_XMLBuilder.SPACE_INDENT + "<a:i>3</a:i>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + I_XMLBuilder.SPACE_INDENT + "<a:i>4</a:i>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + I_XMLBuilder.SPACE_INDENT + "<a:i>5</a:i>" + I_XMLBuilder.DOS_LINE_FEED +
				I_XMLBuilder.SPACE_INDENT + "</a:L>" + I_XMLBuilder.DOS_LINE_FEED +
				 "</a:L>" + I_XMLBuilder.DOS_LINE_FEED , xml);
		
	}
}
