package org.adligo.xml_io.client;

import java.util.HashMap;
import java.util.Map;

import org.adligo.models.params.client.I_XMLBuilder;
import org.adligo.models.params.client.TagInfo;
import org.adligo.xml_io.client.converters.Tags;

public class CustomComplexModelConverter implements I_Converter<CustomComplexModel>{
	private static final String TAG_NAME = "ccm";
	
	@Override
	public ObjectFromXml<CustomComplexModel> fromXml(String xml, TagInfo info,
			Xml_IOReaderContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toXml(CustomComplexModel p, Xml_IOWriterContext context) {
		I_XMLBuilder builder = context.getBuilder();
		builder.appendTagHeaderStart(TAG_NAME);
		
		String name = context.getNextTagNameAttribute();
		if (name != null) {
			builder.appendAttribute(Tags.NAME_ATTRIBUTE, name);
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
