package org.adligo.xml_io.client;

import org.adligo.models.params.client.I_XMLBuilder;

public class MockConstants {
	public static final String HEADER = Xml_IOConstants.HEADER + I_XMLBuilder.DOS_LINE_FEED + "<a:";
	
	public static final String HEADER_2_NOEND = " xmlns:a=\"http://www.adligo.org/xml_io\"" + I_XMLBuilder.DOS_LINE_FEED +
	" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
	public static final String HEADER_2 = HEADER_2_NOEND + ">";
	
	public static final String CUSTOM_HEADER = Xml_IOConstants.HEADER + I_XMLBuilder.DOS_LINE_FEED + "<b:";
	public static final String CUSTOM_HEADER_2_NOEND = 
	" xmlns:b=\"http://www.adligo.org/xml_io_tests\"" + I_XMLBuilder.DOS_LINE_FEED +
	" xmlns:a=\"http://www.adligo.org/xml_io\"" + I_XMLBuilder.DOS_LINE_FEED +
	" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
	
}	
