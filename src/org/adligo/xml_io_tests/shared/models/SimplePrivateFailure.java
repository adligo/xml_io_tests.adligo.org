package org.adligo.xml_io_tests.shared.models;

import java.io.Serializable;


public class SimplePrivateFailure implements Serializable {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private String dam;

public String getDam() {
	return dam;
}

public void setDam(String dam) {
	this.dam = dam;
}
 
}
