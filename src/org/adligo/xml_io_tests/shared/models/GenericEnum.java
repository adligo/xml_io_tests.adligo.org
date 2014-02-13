package org.adligo.xml_io_tests.shared.models;

import java.io.Serializable;

public class GenericEnum<T extends Enum<?>> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
}
