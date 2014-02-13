package org.adligo.xml_io_tests.shared;

import java.math.BigDecimal;

public class CustomSimpleModel {
	private BigDecimal a = new BigDecimal(1.0);
	private BigDecimal b = new BigDecimal(2.0);
	
	public BigDecimal getA() {
		return a;
	}
	public void setA(BigDecimal a) {
		this.a = a;
	}
	public BigDecimal getB() {
		return b;
	}
	public void setB(BigDecimal b) {
		this.b = b;
	}
	
}
