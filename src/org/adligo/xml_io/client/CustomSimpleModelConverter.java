package org.adligo.xml_io.client;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.i.util.client.I_Iterator;
import org.adligo.models.params.client.I_XMLBuilder;
import org.adligo.models.params.client.Parser;
import org.adligo.models.params.client.TagAttribute;
import org.adligo.models.params.client.TagInfo;

/**
 * this kind of code will be autogenerated,
 * it is just a simple example
 * 
 * @author scott
 *
 */
public class CustomSimpleModelConverter implements I_Converter<CustomSimpleModel> {
	public static final String CUSTOM_NAMESPACE = "http://www.adligo.org/xml_io_tests";
	private static final String TAG_NAME = "ctm";
	private static final Map<String, I_AttributeSetter<CustomSimpleModel>> SETTERS = getSetters();
	
	public static Map<String, I_AttributeSetter<CustomSimpleModel>> getSetters() {
		Map<String, I_AttributeSetter<CustomSimpleModel>> toRet = 
			new HashMap<String, I_AttributeSetter<CustomSimpleModel>>();
		
		toRet.put("a", new I_AttributeSetter<CustomSimpleModel>() {
			public void set(CustomSimpleModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(BigDecimal.class, value);
				obj.setA((BigDecimal) toSet);
			}
		});
		
		toRet.put("b", new I_AttributeSetter<CustomSimpleModel>() {
			public void set(CustomSimpleModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(BigDecimal.class, value);
				obj.setB((BigDecimal) toSet);
			}
		});
		
		return Collections.unmodifiableMap(toRet);
	}
	
	@Override
	public ObjectFromXml<CustomSimpleModel> fromXml(String xml, TagInfo info,
			Xml_IOReaderContext context) {
		
		//the CustomModel class is mutable so just call setters
		CustomSimpleModel toRet = new CustomSimpleModel();
		I_Iterator it = Parser.getAttributes(info, xml);
		while (it.hasNext()) {
			TagAttribute attrib = (TagAttribute) it.next();
			String name = attrib.getName();
			String value = attrib.getValue();
			
			I_AttributeSetter<CustomSimpleModel> setter = SETTERS.get(name);
			if (setter != null) {
				setter.set(toRet, value, context);
			}
		}
		return new ObjectFromXml<CustomSimpleModel>(toRet);
	}

	@Override
	public void toXml(CustomSimpleModel p, Xml_IOWriterContext context) {
		I_XMLBuilder builder = context.getBuilder();
		context.appendTagHeaderStart(CUSTOM_NAMESPACE, TAG_NAME);
		context.appendSchemaInfoToFirstTag();
		
		String name = context.getNextTagNameAttribute();
		if (name != null) {
			builder.appendAttribute(Xml_IOConstants.N_NAME_ATTRIBUTE, name);
		}
		
		context.writeXmlAttribute("a", p.getA());
		context.writeXmlAttribute("b", p.getB());
		
		builder.appendTagHeaderEndNoChildren();
		
	}

	public static void setUp(Xml_IOSettings settings) {
		
		NamespaceConverters converters = new NamespaceConverters();
		converters.setNamespace(CUSTOM_NAMESPACE);
		converters.addXmlToObjectConverter(TAG_NAME, new CustomSimpleModelConverter());
		converters.addObjectToXmlConverter(CustomSimpleModel.class, new CustomSimpleModelConverter());
		settings.addNamespaceConverter(converters);
	}
}
