package org.adligo.xml_io.client;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adligo.i.util.client.I_Iterator;
import org.adligo.models.params.client.I_XMLBuilder;
import org.adligo.models.params.client.Parser;
import org.adligo.models.params.client.TagAttribute;
import org.adligo.models.params.client.TagInfo;
import org.adligo.xml_io.client.converters.ClassMappings;

public class CustomComplexModelConverter implements I_Converter<CustomComplexModel>{
	private static final String TAG_NAME = "ccm";
	private static final Map<String, I_AttributeSetter<CustomComplexModel>> SETTERS = getSetters();
	private static final Map<String, I_FieldSetter<CustomComplexModel>> FIELD_SETTERS = getFieldSetters();
	
	public static Map<String, I_AttributeSetter<CustomComplexModel>> getSetters() {
		Map<String, I_AttributeSetter<CustomComplexModel>> toRet = 
			new HashMap<String, I_AttributeSetter<CustomComplexModel>>();
		
		toRet.put("id", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(Integer.class, value);
				obj.setId((Integer) toSet);
			}
		});
		
		toRet.put("sid", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(Short.class, value);
				obj.setSid((Short) toSet);
			}
		});
		
		toRet.put("lid", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(Long.class, value);
				obj.setLid((Long) toSet);
			}
		});
		
		toRet.put("did", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(Double.class, value);
				obj.setDid((Double) toSet);
			}
		});
		
		toRet.put("fid", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(Float.class, value);
				obj.setFid((Float) toSet);
			}
		});
		
		toRet.put("chars", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(ClassMappings.CHAR_ARRAY_CLASS, value);
				obj.setChars((char []) toSet);
			}
		});
		
		toRet.put("bytes", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(ClassMappings.BYTE_ARRAY_CLASS, value);
				obj.setBytes((byte []) toSet);
			}
		});
		
		toRet.put("bools", new I_AttributeSetter<CustomComplexModel>() {
			public void set(CustomComplexModel obj, String value, Xml_IOReaderContext context) {
				Object toSet = context.readAttribute(ClassMappings.BOOLEAN_ARRAY_CLASS, value);
				obj.setBools((boolean []) toSet);
			}
		});
		

		return Collections.unmodifiableMap(toRet);
	}
	
	
	public static Map<String, I_FieldSetter<CustomComplexModel>> getFieldSetters() {
		Map<String, I_FieldSetter<CustomComplexModel>> toRet = 
			new HashMap<String, I_FieldSetter<CustomComplexModel>>();

		toRet.put("stringsList", new I_FieldSetter<CustomComplexModel>() {
			@SuppressWarnings("unchecked")
			public void set(CustomComplexModel obj, Object value) {
				obj.setStringsList((List<String>) value);
			}
		});
		
		toRet.put("stringsArray", new I_FieldSetter<CustomComplexModel>() {
			@SuppressWarnings("unchecked")
			public void set(CustomComplexModel obj, Object value) {
				List<String> items = (List<String>) value;
				String [] itemsArray = new String [items.size()];
				items.toArray(itemsArray);
				obj.setStringsArray(itemsArray);
			}
		});
		
		toRet.put("keyValues", new I_FieldSetter<CustomComplexModel>() {
			@SuppressWarnings("unchecked")
			public void set(CustomComplexModel obj, Object value) {
				obj.setKeyValues((Map<Integer, String>) value);
			}
		});
		
		return Collections.unmodifiableMap(toRet);
	}
	@Override
	public ObjectFromXml<CustomComplexModel> fromXml(String xml, TagInfo info,
			Xml_IOReaderContext context) {
		
		//the CustomModel class is mutable so just call setters
		CustomComplexModel toRet = new CustomComplexModel();
		I_Iterator it = Parser.getAttributes(info, xml);
		while (it.hasNext()) {
			TagAttribute attrib = (TagAttribute) it.next();
			String name = attrib.getName();
			String value = attrib.getValue();
			
			//don't call a setter for name
			if ( !Xml_IOConstants.N_NAME_ATTRIBUTE.equals(name)){
				I_AttributeSetter<CustomComplexModel> setter = SETTERS.get(name);
				setter.set(toRet, value, context);
			}
		}
		it = info.getChildren();
		while (it.hasNext()) {
			TagInfo child = (TagInfo) it.next();
			String subTag = Parser.substring(xml, child);
			ObjectFromXml<?> obj =  context.readXml(subTag);
			String name = obj.getName();
			I_FieldSetter<CustomComplexModel> setter = FIELD_SETTERS.get(name);
			Object value = obj.getValue();
			setter.set(toRet, value);
		}
		
		return new ObjectFromXml<CustomComplexModel>(toRet);
	}

	@Override
	public void toXml(CustomComplexModel p, Xml_IOWriterContext context) {
		I_XMLBuilder builder = context.getBuilder();
		builder.appendTagHeaderStart(TAG_NAME);
		
		String name = context.getNextTagNameAttribute();
		if (name != null) {
			builder.appendAttribute(Xml_IOConstants.N_NAME_ATTRIBUTE, name);
			context.setNextTagNameAttribute(null);
		}
		//do attributes 
		context.writeXmlAttribute("id", p.getId());
		context.writeXmlAttribute("sid", p.getSid());
		context.writeXmlAttribute("lid", p.getLid());
		context.writeXmlAttribute("did", p.getDid());
		context.writeXmlAttribute("fid", p.getFid());
		context.writeXmlAttribute("chars", p.getChars());
		context.writeXmlAttribute("bytes", p.getBytes());
		context.writeXmlAttribute("bools", p.getBools());
		
		builder.appendTagHeaderEnd(true);
		builder.addIndentLevel();
		//do nested tags
		context.setNextTagNameAttribute("stringsList");
		context.writeXml(p.getStringsList());
		
		context.setNextTagNameAttribute("stringsArray");
		context.writeXml(p.getStringsArray());
		
		context.setNextTagNameAttribute("keyValues");
		context.writeXml(p.getKeyValues());
		
		builder.removeIndentLevel();
		builder.appendEndTag(TAG_NAME);
	}

	public static void setUp(Xml_IOSettings settings) {
		Map<String, I_Converter<?>> fromXml = new HashMap<String, I_Converter<?>>();
		fromXml.put(TAG_NAME, new CustomComplexModelConverter());
		settings.addFromXmlConverters(fromXml);
		
		Map<Class<?>, I_Converter<?>> toXml = new HashMap<Class<?>, I_Converter<?>>();
		toXml.put(CustomComplexModel.class, new CustomComplexModelConverter());
		settings.addToXmlConverters(toXml);
	}
}
