package org.adligo.xml_io.client;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.i.util.client.ClassUtils;
import org.adligo.i.util.client.models.ComplexCollections;
import org.adligo.i.util.client.models.GenericEnum;
import org.adligo.i.util.client.models.SimpleCollections;
import org.adligo.i.util.client.models.SimpleCollectionsWithComments;
import org.adligo.i.util.client.models.SimpleDefaultFailure;
import org.adligo.i.util.client.models.SimpleEnum;
import org.adligo.i.util.client.models.SimpleEnumContainer;
import org.adligo.i.util.client.models.SimpleFailureModel;
import org.adligo.i.util.client.models.SimplePrivateFailure;
import org.adligo.i.util.client.models.SimpleSerializable;
import org.adligo.i.util.client.models.SimpleSqlDateFailureModel;
import org.adligo.i.util.client.models.SimpleStaticFieldModel;
import org.adligo.i.util.client.models.SqlDateGeneicFailureModel;
import org.adligo.i.util.client.models.other_pkg.ComplexMaps;
import org.adligo.i.util.client.models.other_pkg.SimpleIsSerializable;
import org.adligo.i.util.client.models.other_pkg.SimpleMaps;
import org.adligo.tests.ATest;

/**
 * this actually tests test code
 * @author scott
 *
 */
public class IsXmlIOSerializableTests extends ATest {

	public void testSimpleClasses() throws Exception {
		
		IsXmlIoSerializable.isXmlIoSerializable(String.class);
		IsXmlIoSerializable.isXmlIoSerializable(Date.class);
		IsXmlIoSerializable.isXmlIoSerializable(Character.class);
		IsXmlIoSerializable.isXmlIoSerializable(Byte.class);
		IsXmlIoSerializable.isXmlIoSerializable(Short.class);
		IsXmlIoSerializable.isXmlIoSerializable(Integer.class);
		IsXmlIoSerializable.isXmlIoSerializable(Boolean.class);
		IsXmlIoSerializable.isXmlIoSerializable(Float.class);
		IsXmlIoSerializable.isXmlIoSerializable(Double.class);
		IsXmlIoSerializable.isXmlIoSerializable(Long.class);
	}
	
	public void testCollectionAndMap() throws Exception  {
		IsXmlIoSerializable.isXmlIoSerializable(Collection.class);
		IsXmlIoSerializable.isXmlIoSerializable(List.class);
		IsXmlIoSerializable.isXmlIoSerializable(Set.class);
		IsXmlIoSerializable.isXmlIoSerializable(Map.class);
	}
	
	public void testSimplePrimitives() throws Exception  {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleIsSerializable.class);
	}
	
	public void testSimpleSerializable() throws Exception  {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleSerializable.class);
	}
	
	public void testSimpleModelSuccess()  {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleFailureModel.class);
	}
	
	public void testJavaSqlDateFailure()   {
		IllegalStateException ex = null;
		try {
			IsXmlIoSerializable.isXmlIoSerializable(java.sql.Date.class);
		} catch (IllegalStateException x) {
			ex = x;
		}
		assertNotNull(ex);
		assertEquals("You can't have a java.sql.Date you need java.util.Date.",
				ex.getMessage());
	}
	
	public void testSimpleCollections() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleCollections.class);
	}
	
	public void testSimpleMaps() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleMaps.class);
	}
	
	public void testBuilder() throws Exception {
		IsXmlIoSerializableBuilder builder = new IsXmlIoSerializableBuilder();
		builder.getCurrentClassParents().add(SimpleCollections.class);
		
		IsXmlIoSerializableBuilder newBuilder = new IsXmlIoSerializableBuilder(builder);
		assertTrue(newBuilder.getCurrentClassParents().contains(SimpleCollections.class));
		assertEquals(1, newBuilder.getCurrentClassParents().size());
	}
	
	public void testStaticField() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleStaticFieldModel.class);
	}
	
	public void testComplexCollections() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(ComplexCollections.class);
	}
	
	public void testComplexMaps() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(ComplexMaps.class);
	}
	
	public void testSimpleSqlDateFailureModel() {
		IllegalStateException ex = null;
		try {
			IsXmlIoSerializable.isXmlIoSerializable(SimpleSqlDateFailureModel.class);
		} catch (IllegalStateException x) {
			ex = x;
		}
		assertNotNull(ex);
		assertEquals("You can't have a java.sql.Date you need java.util.Date  " +
				"in class class java.sql.Date with parents " +
				"[class org.adligo.i.util.client.models.SimpleSqlDateFailureModel]",
				ex.getMessage());
	}
	
	public void testSqlDateGenericFailureModel() {
		IllegalStateException ex = null;
		try {
			IsXmlIoSerializable.isXmlIoSerializable(SqlDateGeneicFailureModel.class);
		} catch (IllegalStateException x) {
			ex = x;
		}
		assertNotNull(ex);
		assertEquals("You can't have a java.sql.Date you need java.util.Date  " +
				"in class class java.sql.Date with parents " +
				"[interface java.util.Collection, class org.adligo.i.util.client.models.SqlDateGeneicFailureModel]",
				ex.getMessage());
	}
	
	public void testSimpleCollectionsWithComments() throws Exception {
		String resp = IsXmlIoSerializable.removeContent(SimpleCollectionsWithComments.class, 
				ClassUtils.getClassShortName(SimpleCollectionsWithComments.class) + ".java");
		
		assertFalse("shouldn't contain the test '* roles'", resp.contains("* roles"));
		assertFalse("shouldn't contain the test '<TESTS!> roles'", resp.contains("<TESTS!> roles"));
		IsXmlIoSerializable.isXmlIoSerializable(SimpleCollectionsWithComments.class);
	}
	
	public void testSimpleEnum() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleEnum.class);
	}
	
	public void testSimpleEnumContainer() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(SimpleEnumContainer.class);
	}
	
	@SuppressWarnings("unchecked")
	public void testSimpleGenericEnum() throws Exception {
		IsXmlIoSerializable.isXmlIoSerializable(GenericEnum.class);
		GenericEnum ge = new GenericEnum<SimpleEnum>();
		ge.setT(SimpleEnum.HEY_ENUMS_TOO);
		assertEquals(SimpleEnum.HEY_ENUMS_TOO, ge.getT());
	}
	
	public void testSimplePrivatePass() throws Exception  {
		IllegalStateException ex = null;
		try {
			IsXmlIoSerializable.isXmlIoSerializable(SimplePrivateFailure.class);
		} catch (IllegalStateException x) {
			ex = x;
		}
		assertNull(ex);
	}
	
	public void testSimpleDefaultPass() throws Exception  {
		IllegalStateException ex = null;
		try {
			IsXmlIoSerializable.isXmlIoSerializable(SimpleDefaultFailure.class);
		} catch (IllegalStateException x) {
			ex = x;
		}
		assertNull(ex);
	}
}
