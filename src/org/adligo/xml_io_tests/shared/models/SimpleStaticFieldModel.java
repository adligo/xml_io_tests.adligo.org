package org.adligo.xml_io_tests.shared.models;

import java.io.Serializable;

public class SimpleStaticFieldModel implements Serializable {
	private static final SimpleFailureModel STATIC = new SimpleFailureModel();
	
	protected String field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
