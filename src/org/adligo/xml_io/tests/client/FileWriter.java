package org.adligo.xml_io.tests.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

	public static void writeFile(String name, String content) throws IOException {
		File file = new File(name);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content.getBytes());
		fos.close();
		throw new IOException("test shouln't call this if there checked in");
	}
}
