package org.adligo.xml_io.tests.client;

import java.util.Map;

import org.adligo.tests.ATest;
import org.adligo.xml_io.client.I_AttributeConverter;
import org.adligo.xml_io.client.I_Converter;
import org.adligo.xml_io.client.NamespaceConvertersMutant;

public class NamespaceConvertersTests extends ATest {

	public void testGetsAndSets_ForImmutableMaps() {
		
		NamespaceConvertersMutant mutant = new NamespaceConvertersMutant();
		mutant.setNamespace("ns");
		assertEquals("ns", mutant.getNamespace());
		
		mutant.setPackageName("pk");
		assertEquals("pk", mutant.getPackageName());
		
		Map<Class<?>, I_AttributeConverter<?>> attrCons =  mutant.getAttributeConverters();
		Exception caught = null;
		try {
			attrCons.put(Exception.class, null);
		} catch (Exception g) {
			caught = g;
		}
		assertNotNull(caught);
		assertEquals(UnsupportedOperationException.class, caught.getClass());
		
		Map<Class<?>, I_Converter<?>> cons =  mutant.getObjectToXmlConverters();
		caught = null;
		try {
			cons.put(Exception.class, null);
		} catch (Exception g) {
			caught = g;
		}
		assertNotNull(caught);
		assertEquals(UnsupportedOperationException.class, caught.getClass());
		
		Map<String, I_Converter<?>> fromXmlCons =   mutant.getXmlToObjectConverters();
		caught = null;
		try {
			fromXmlCons.put("", null);
		} catch (Exception g) {
			caught = g;
		}
		assertNotNull(caught);
		assertEquals(UnsupportedOperationException.class, caught.getClass());
	}
}
