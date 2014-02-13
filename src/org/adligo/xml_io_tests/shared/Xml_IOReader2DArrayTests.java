package org.adligo.xml_io_tests.shared;

import java.util.List;

import org.adligo.models.params.shared.I_XMLBuilder;
import org.adligo.tests.ATest;
import org.adligo.xml_io.shared.Xml_IOReader;

public class Xml_IOReader2DArrayTests extends ATest {

	public void test2DArray() {
		Xml_IOReader reader = new Xml_IOReader();
		
		Object result = reader.readXml(
				MockConstants.HEADER + "L" + MockConstants.HEADER_2 + I_XMLBuilder.DOS_LINE_FEED +
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
				 "</a:L>" + I_XMLBuilder.DOS_LINE_FEED);
		assertTrue(result instanceof List<?>);
		@SuppressWarnings("unchecked")
		List<List<?>> first = (List<List<?>>) result;
		List<?> second = first.get(0);
		
		//custom 2+d array reads will be complex generated code...
		int [][] ia = new int [first.size()][second.size()];
		for (int i = 0; i < first.size(); i++) {
			@SuppressWarnings("unchecked")
			List<Integer> secondA = (List<Integer> ) first.get(i);
			for (int j = 0; j < secondA.size(); j++) {
				ia[i][j] = secondA.get(j);
			}
		}
		assertEquals(0, ia[0][0]);
		assertEquals(1, ia[0][1]);
		assertEquals(2, ia[0][2]);
		assertEquals(3, ia[1][0]);
		assertEquals(4, ia[1][1]);
		assertEquals(5, ia[1][2]);
	}
}
