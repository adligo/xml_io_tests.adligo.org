package org.adligo.xml_io_tests.shared.models.other_pkg;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.adligo.xml_io_tests.shared.models.SimpleSerializable;

public class ComplexMaps implements Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	/**
	 * note the funny formatting!
	 * its part of the test!
	 */
	protected Map<String,SimpleIsSerializable> strings;
	protected Map<String,SimpleSerializable>
	dates;
	
	protected Map<String,	Map	<
		SimpleIsSerializable, SimpleSerializable >> char_objs;
	
	protected Map<String,		Collection<	SimpleIsSerializable >	> byte_objs;
	protected Map<String,
		Short> short_objs ;
	protected Map<	String,Integer> int_objs;
	protected Map < String,Boolean> boolean_objs	;
	protected Map<
		String,	Float > float_objs
	;
	public Map<String, SimpleIsSerializable> getStrings() {
		return strings;
	}
	public Map<String, SimpleSerializable> getDates() {
		return dates;
	}
	public Map<String, Map<SimpleIsSerializable, SimpleSerializable>> getChar_objs() {
		return char_objs;
	}
	public Map<String, Collection<SimpleIsSerializable>> getByte_objs() {
		return byte_objs;
	}
	public Map<String, Short> getShort_objs() {
		return short_objs;
	}
	public Map<String, Integer> getInt_objs() {
		return int_objs;
	}
	public Map<String, Boolean> getBoolean_objs() {
		return boolean_objs;
	}
	public Map<String, Float> getFloat_objs() {
		return float_objs;
	}
	public void setStrings(Map<String, SimpleIsSerializable> strings) {
		this.strings = strings;
	}
	public void setDates(Map<String, SimpleSerializable> dates) {
		this.dates = dates;
	}
	public void setChar_objs(
			Map<String, Map<SimpleIsSerializable, SimpleSerializable>> charObjs) {
		char_objs = charObjs;
	}
	public void setByte_objs(Map<String, Collection<SimpleIsSerializable>> byteObjs) {
		byte_objs = byteObjs;
	}
	public void setShort_objs(Map<String, Short> shortObjs) {
		short_objs = shortObjs;
	}
	public void setInt_objs(Map<String, Integer> intObjs) {
		int_objs = intObjs;
	}
	public void setBoolean_objs(Map<String, Boolean> booleanObjs) {
		boolean_objs = booleanObjs;
	}
	public void setFloat_objs(Map<String, Float> floatObjs) {
		float_objs = floatObjs;
	}
}
