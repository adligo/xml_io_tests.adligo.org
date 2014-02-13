package org.adligo.xml_io_tests.shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;
import org.adligo.i.util.shared.ClassUtils;
import org.adligo.i.util.shared.StringUtils;
import org.adligo.i.util_tests.JavaFileReadException;
import org.adligo.xml_io.shared.NamespaceConverters;
import org.adligo.xml_io.shared.converters.DefaultNamespaceConverters;

public class IsXmlIoSerializable  {
	private static final Log log = LogFactory.getLog(IsXmlIoSerializable.class);
	private static Set<Character> WHITE_SPACE_CHARACTERS = getWhiteSpaceChars();
	private static Map<String,Class<?>> COMMON_CLASSES = getCommonClasses();
	private static Set<Class<?>> SPECIAL_COMMON_CLASSES = getSpecialCommonClasses();
	public enum VISIBILITY {
		PUBLIC, PROTECTED, DEFAULT, PRIVATE
	}
	
	private static Set<Character> getWhiteSpaceChars() {
		Set<Character> toRet = new HashSet<Character>();
		toRet.add(' ');
		toRet.add('\t');
		toRet.add('\n');
		toRet.add('\r');
		return toRet;
	}
	
	private static Set<Class<?>> getSpecialCommonClasses() {
		Set<Class<?>> toRet = new HashSet<Class<?>>();
		toRet.add(java.util.Date.class);
		return toRet;
	}
	
	/**
	 * note Date is not in here so that java.util.Date and java.sql.Date 
	 * can be idenitified all the time
	 * @return
	 */
	private static Map<String,Class<?>>  getCommonClasses() {
		Map<String,Class<?>> toRet = new HashMap<String,Class<?>>();
		toRet.put("String",String.class);
		toRet.put("Short",Short.class);
		toRet.put("Integer",Integer.class);
		toRet.put("Long",Long.class);
		toRet.put("Double",Double.class);
		toRet.put("Float",Float.class);
		toRet.put("Character",Character.class);
		toRet.put("Byte",Byte.class);
		toRet.put("Boolean",Boolean.class);
		return toRet;
	}
	/**
	 * This class should tell you in a nice way if Xml_IO will work with your models
	 * you will still need to run a xml_io_generator on your code
	 * to create the source code for the actual io part
	 * 
	 * import foo.bar.MyClass;
	 * NOT
	 * import foo.bar.*;
	 * 
	 * I could probably get .* to work, but I think its a good convention 
	 * to have explicit imports, so I wouln't do it.  Classes in the same
	 * package will get found.
	 * 
	 * @param clazz
	 */
	public static void isXmlIoSerializable(Class<?> clazz) {
		isXmlIoSerializable(clazz, DefaultNamespaceConverters.getDefaultNamespaceConverters());
	}
	public static void isXmlIoSerializable(Class<?> clazz, NamespaceConverters converters) {
		
		Class<?> currentParentClazz = clazz;
		List<Class<?>> parents = new ArrayList<Class<?>>();
		while (currentParentClazz != null) {
			if (currentParentClazz.isInterface() || currentParentClazz.isPrimitive() 
				|| currentParentClazz.isEnum()) {
				break;
			}
			if (Object.class.isAssignableFrom(currentParentClazz)) {
				break;
			} else {
				parents.add(currentParentClazz);
				currentParentClazz = currentParentClazz.getSuperclass();
			}
		}
		
		if (parents.size() > 1) {
			Class<?> [] parentsArray = parents.toArray(new Class[parents.size()]);
			for (int i = parentsArray.length -1; i > 0; i--) {
				IsXmlIoSerializableBuilder builder = new IsXmlIoSerializableBuilder();
				Class<?> parent = parentsArray[i];
				builder.setCurrentClass(parent);
				builder.setRootClass(parent);
				builder.setNamespaceConverters(converters);
				isXmlIoSerializable(parent, converters);
			}
		}
		
		Collection<Class<?>> commonClasses = COMMON_CLASSES.values();
		if (commonClasses.contains(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug(clazz + " is a A common class which is ok. ");
			}
			return;
		}
		if (java.sql.Date.class.isAssignableFrom(clazz)) {
			throw new IllegalStateException("You can't have a java.sql.Date you need java.util.Date.");
		}
		if (SPECIAL_COMMON_CLASSES.contains(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug(clazz + " is a A special common class which is ok. ");
			}
			return;
		}

		
		IsXmlIoSerializableBuilder builder = new IsXmlIoSerializableBuilder();
		builder.setCurrentClass(clazz);
		builder.setRootClass(clazz);
		builder.setNamespaceConverters(converters);
		if (isCollection(builder)) {
			return;
		}
		isXmlIoSerializable(builder, converters);
	}

	private static boolean isCollection(Class<?> clazz) {
		if (Map.class.isAssignableFrom(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug(clazz + " is a A Map class which is ok, however your runtime items may not be. ");
			}
			return true;
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug(clazz + " is a A Collection class which is ok, however your runtime items may not be. ");
			}
			return true;
		}
		return false;
	}
	
	private static void isXmlIoSerializable( IsXmlIoSerializableBuilder builder) throws IllegalStateException {
		isXmlIoSerializable(builder, builder.getNamespaceConverters());
	}
	private static void isXmlIoSerializable( IsXmlIoSerializableBuilder builder, NamespaceConverters converters) throws IllegalStateException {
			Class<?> clazz = builder.getCurrentClass();
			
			if (clazz.isEnum()) {
				if (log.isDebugEnabled()) {
					log.debug(clazz + " is a enum which is ok. ");
				}
				return;
			}
			if (SPECIAL_COMMON_CLASSES.contains(clazz)) {
				if (log.isDebugEnabled()) {
					log.debug(clazz + " is a A special common class which is ok. ");
				}
				return;
			}
			if (builder.isCheckingGeneric()) {
				if (log.isDebugEnabled()) {
					log.debug(" checking generic ");
				}
				if (isCollection(builder)) {
					return;
				}
			}
			Collection<Class<?>> commonClasses = COMMON_CLASSES.values();
			if (commonClasses.contains(clazz)) {
				if (log.isDebugEnabled()) {
					log.debug(clazz + " is a A common class which is ok. ");
				}
				return;
			}
			
			if (java.sql.Date.class == clazz) {
				throw new IllegalStateException("You can't have a java.sql.Date you need java.util.Date "
						+ " in class " + builder.getCurrentClass() + 
						" with parents " + builder.getCurrentClassParents());
			}
			List<Class<?>> parents = builder.getCurrentClassParents();
		
			assertFields(builder);
			assertConstructors(clazz, parents);
	}


	private static boolean isCollection(IsXmlIoSerializableBuilder builder) throws IllegalStateException {
		
		Class<?> clazz = builder.getCurrentClass();
		String fieldName = builder.getCurrentField();
		List<Class<?>> parents = builder.getCurrentClassParents();
		
		if (Map.class.isAssignableFrom(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug("class " + clazz + " with parent " + parents +
						" is a Map which is ok. Parsing java file looking for generic types " +
						"of field " + fieldName + ". ");
			}
			return parseJavaFileForCollection(builder, parents);
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug("class " + clazz + " with parent " + parents +
						" is a Collection which is ok. Checking .java file. ");
			}
			return parseJavaFileForCollection(builder, parents);
		}
		return false;
	}

	private static boolean parseJavaFileForCollection(IsXmlIoSerializableBuilder builder,
			List<Class<?>> parents) throws IllegalStateException {
		if (parents.size() == 0) {
			return true;
		}
		Class<?> parent = parents.get(0);
		if (isCollection(parent)) {
			return true;
		}
		String classJavaFileName = ClassUtils.getClassShortName(parent) + ".java";
		
		
		try {
			if (classJavaFileName.equals(builder.getCurrentClassJavaFileName())) {
				if (log.isDebugEnabled()) {
					log.debug("already parsed file " + classJavaFileName );
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Parseing file " + classJavaFileName + " last file was " +
							builder.getCurrentClassJavaFileName());
				}
				String memberContent = removeContent(parent, classJavaFileName);
				builder.setCurrentClassJavaFileName(classJavaFileName);
				builder.setCurrentClassMemberContent(memberContent);
				if (log.isDebugEnabled()) {
					log.debug("memberContent is " +memberContent);
				}
			}
			//keep state which may change as the result of a Serializable class
			// in the content itself
			String currentJavaFile = builder.getCurrentClassJavaFileName();
			String currentMemberContent = builder.getCurrentClassMemberContent();
			
			//check file content for field
			checkFileContentForFieldGenerics(builder, -1);
			//replace state
			builder.setCurrentClassJavaFileName(currentJavaFile);
			builder.setCurrentClassMemberContent(currentMemberContent);
			
		} catch (IOException x) {
			IllegalStateException toThrow = new IllegalStateException("problem reading file " + classJavaFileName +
					" you may need to add the source folder to your classpath of your test run.");
			toThrow.initCause(x);
			throw toThrow;
		} catch (JavaFileReadException j) {
			IllegalStateException toThrow = new IllegalStateException("problem parsing file " + classJavaFileName +
			" you may need to alter your java source code to be compatible with this test. " +
			" Add explicit imports (ie import foo.bar.YourClass;) and possibly submit a bug report " +
			" for the IsXmlIOSerializable class, support@adligo.com. ");
			toThrow.initCause(j);
			throw toThrow;
		}
		return true;
	}

	/**
	 * digs through file for a specific field
	 * @param builder
	 * @param lastIndex
	 * @param parentFile
	 * @param parentContent
	 * @throws Exception
	 */
	private static void checkFileContentForFieldGenerics(IsXmlIoSerializableBuilder builder, int lastIndex) 
	throws IOException, JavaFileReadException, IllegalStateException {
		
		if (log.isDebugEnabled()) {
			log.debug("checkFileContentForFieldGenerics with file " + 
					builder.getCurrentClassJavaFileName() + " looking for field " +
					builder.getCurrentField());
		}
		String fieldName = builder.getCurrentField();
		String classJavaFileName = builder.getCurrentClassJavaFileName();
		String memberContent = builder.getCurrentClassMemberContent();
		
		int index = memberContent.indexOf(fieldName, lastIndex);
		if (index < 1) {
			JavaFileReadException ex = new JavaFileReadException(
					"Unable to find field " + fieldName + " in " +
					classJavaFileName);
			ex.fillInStackTrace();
			throw ex;
		} else if (index + fieldName.length() + 1 > memberContent.length()){
			JavaFileReadException ex = new JavaFileReadException(
					"Unable to find field " + fieldName + " in " +
					classJavaFileName);
			ex.fillInStackTrace();
			throw ex;
		} else {
			String surroundingField = memberContent.substring(index -1, index + fieldName.length() + 1);
			if (log.isDebugEnabled()) {
				log.debug("surrounding content is '" + surroundingField + "'");
			}
			char s = surroundingField.charAt(0);
			char e = surroundingField.charAt(surroundingField.length() -1);

			if (!WHITE_SPACE_CHARACTERS.contains(s)) {
				if (log.isDebugEnabled()) {
					log.debug("false positive search again first surrounding content character is '" + 
							s + "'");
				}
				checkFileContentForFieldGenerics(builder, index + 1);
			}
			//special semicolon case in end char
			if (!WHITE_SPACE_CHARACTERS.contains(e) && e != ';') {
				if (log.isDebugEnabled()) {
					log.debug("false positive search again last surrounding content character is '" + 
							e + "'");
				}
				checkFileContentForFieldGenerics(builder, index + 1);
			}
			if (log.isDebugEnabled()) {
				log.debug("it's a match! ");
			}
			StringBuilder sb = new StringBuilder();
			boolean inCarrot = false;
			int nestedCarrots = 0;
			
			for (int i = index - 2; i >= 0; i--) {
				char c = memberContent.charAt(i);
				if (c == '<') {
					if (nestedCarrots == 0) {
						break;
					} else {
						nestedCarrots--;
						sb.insert(0, ',');
					}
				} else if (c == '>') {
					if (!inCarrot) {
						inCarrot = true;
					} else {
						nestedCarrots++;
					}
				} else {
					if (inCarrot) {
						sb.insert(0, c);
					}
				}
			}
			String genericContent = sb.toString();
			if (log.isDebugEnabled()) {
				log.debug("got generic content '" + genericContent + "'");
			}
			
			
			
			sb = new StringBuilder();
			char [] genericChars = genericContent.toCharArray();
			for (int i = 0; i < genericChars.length; i++) {
				char c = genericChars[i];
				if (c == ',') {
					
					builder.setCheckingGeneric(true);
					findAndAssertClassFromGenericJavaCodeName(sb.toString(), builder);
					
					sb = new StringBuilder();
				} else {
					sb.append(c);
				}
			}
			builder.setCheckingGeneric(true);
			findAndAssertClassFromGenericJavaCodeName(sb.toString(), builder);
			builder.setCheckingGeneric(false);
			
		}
		if (log.isDebugEnabled()) {
			log.debug(" exit checkFileContentForFieldGenerics with file " + builder.getCurrentClassJavaFileName() 
					+ " looking for field " +
					builder.getCurrentField());
		}
	}
	
	
	private static void findAndAssertClassFromGenericJavaCodeName(String className, IsXmlIoSerializableBuilder builder) 
		throws IllegalStateException {
		
		if (StringUtils.isEmpty(className)) {
			return;
		}
		
		className = className.trim();
		if (log.isDebugEnabled()) {
			log.debug("enter with findAndAssertClassFromGenericJavaCodeName '" + className + "'");
		}
		try  {
			if (className.indexOf('.') != -1) {
				if (log.isDebugEnabled()) {
					log.debug("class " + className + " is a full name");
				}
				Class<?> clazz = Class.forName(className.trim());
				IsXmlIoSerializableBuilder newBuilder = new IsXmlIoSerializableBuilder(builder);
				newBuilder.setCurrentClass(clazz);
				newBuilder.getCurrentClassParents().add(0, builder.getCurrentClass());
				isXmlIoSerializable(newBuilder, builder.getNamespaceConverters());
			} else {
				Class<?> clazz = COMMON_CLASSES.get(className);
				if (clazz == null) {
					clazz = obtainClassFromImports(className, builder);
				} 
				IsXmlIoSerializableBuilder newBuilder = new IsXmlIoSerializableBuilder(builder);
				newBuilder.setCurrentClass(clazz);
				newBuilder.getCurrentClassParents().add(0, builder.getCurrentClass());
				if (log.isDebugEnabled()) {
					log.debug("checking class "+ clazz + " from name '" + className + "'");
				}
				isXmlIoSerializable(newBuilder);
			}
		} catch (ClassNotFoundException x) {
			IllegalStateException ex = new IllegalStateException("Unable to load locate class with name " +
					className + " in java source code file " + builder.getCurrentClassJavaFileName() + 
					" with parents " + builder.getCurrentClassParents());
			ex.initCause(x);
			throw ex;
		}
	}
	
	private static Class<?> obtainClassFromImports(String classShortName,IsXmlIoSerializableBuilder builder) 
		throws ClassNotFoundException {
		
		
		String content = builder.getCurrentClassMemberContent();
		String packageName = content.substring(0, content.indexOf(";"));
		packageName = packageName.replace("package", "");
		packageName = packageName.trim();
		String imports = content.substring(content.indexOf(";") + 1, content.indexOf("{"));
		if (log.isDebugEnabled()) {
			log.debug("package '" + packageName + "'");
			log.debug("imports are " + imports);
		}
		
		int index = imports.indexOf(";");
		int lastIndex = 0;
		
		while (index != -1) {
			String nextImport = imports.substring(lastIndex, index);
			nextImport = nextImport.substring(nextImport.indexOf("import") + 6, nextImport.length());
			nextImport = nextImport.trim();
			
			
			if (nextImport.contains(classShortName)) {
				if (log.isDebugEnabled()) {
					log.debug("Class.forName " + nextImport + " from import?");
				}
				return Class.forName(nextImport);
			}
			lastIndex = index + 1;
			index = imports.indexOf(";", index + 1);
		}
		String className = packageName + "." + classShortName;
		//in same package?
		if (log.isDebugEnabled()) {
			log.debug("Class.forName '" + className + "' ?");
		}
		return Class.forName(className);
	}
	/**
	 * this removes all content which is 
	 * text curly braces {}
	 * except the first and last curly brace
	 * @param in
	 * @return
	 */
	protected static String removeContent(Class<?> parent, String classJavaFileName) throws IOException, IllegalStateException {
		
		StringBuffer sb = new StringBuffer();
		boolean inCBrace = false;
		int nestedCBrase = 0;
		boolean inBrace = false;
		int nestedBrase = 0;
		
		boolean foundFirstCBrase = false;
		boolean inCommentSlash = false;
		boolean inEndOfLineComment = false;
		boolean inCommentGroup = false;
		boolean inAstrisk = false;
		
		InputStream is = getInputStreamForClass(parent, classJavaFileName); 
		
		byte [] bytes = new byte[1];
		while (is.read(bytes) != -1) {
			
			
			char c = (char) bytes[0];
			if (c == '/') {
				if (inAstrisk) {
					inCommentGroup = false;
				} else if (inCommentSlash) {
					inEndOfLineComment = true;
				} else {
					inCommentSlash = true;
				}
			} else if (c == '*') {
				if (inCommentSlash) {
					inCommentGroup = true;
				}
				inAstrisk = true;
			} else if (c == '\n') {
				inEndOfLineComment = false;
				inCommentSlash = false;
			} else if (c == '{') {
				if (!foundFirstCBrase) {
					foundFirstCBrase = true;
					//keep first curly brace for import parsing
					sb.append('{');
				} else {
					if (!inCBrace) {
						inCBrace = true;
						nestedCBrase = 0;
					} else {
						nestedCBrase++;
					}
				}
			} else if (c == '}') {
				if (inCBrace) {
					if (nestedCBrase > 0) {
						nestedCBrase--;
					} else {
						inCBrace = false;
					}
				}
			} else if (c == '(') {
				if (!inBrace) {
					inBrace = true;
					nestedBrase = 0;
				} else {
					nestedBrase++;
				}
			} else if (c == ')') {
				if (inBrace) {
					if (nestedBrase > 0) {
						nestedBrase--;
					} else {
						inBrace = false;
					}
				}
			} 
			if (!inCBrace && !inBrace && !inCommentSlash && !inCommentGroup) {
				sb.append(c);
				inAstrisk = false;
			}
		}
		is.close();
		
		
		if (log.isDebugEnabled()) {
			log.debug("returning \n" + sb.toString());
		}
		return sb.toString();
	}

	protected static InputStream getInputStreamForClass(Class<?> parent,
			String classJavaFileName) throws FileNotFoundException {
		InputStream is = parent.getResourceAsStream(classJavaFileName);
		if (log.isDebugEnabled()) {
			log.debug("got input stream " + is + " parent is " + parent);
		}
		
		File file = new File(".");
		String absPath =  file.getAbsolutePath();
		if (is == null) {
			//try to load it from the filesystem directly (eclipse maven exc)
			
			absPath = absPath.substring(0, absPath.length() -1);
			String fileSep = File.separator;
			String className = parent.getName();
			StringTokenizer tok = new StringTokenizer(className, ".");
			
			StringBuilder sb2 = new StringBuilder();
			while (tok.hasMoreElements()) {
				if (sb2.length() > 0) {
					sb2.append(fileSep);
				}
				sb2.append(tok.nextElement());
			}
			String all = absPath + File.separator + "src" + File.separator + sb2.toString() + ".java";
			is = new FileInputStream(all);
			
		}
		return is;
	}

	private static void assertConstructors(Class<?> clazz, List<Class<?>> parents) throws IllegalStateException {
		Constructor<?> [] constructors = clazz.getConstructors();
		if (constructors.length == 0) {
			if (log.isDebugEnabled()) {
				log.debug("class " + clazz + " has no constructors which is ok");
			}
			return;
		}
		try {
			clazz.getConstructor();
		} catch (Exception e) {
			IllegalStateException ex = new IllegalStateException(
					"class " + clazz + " with parents " + parents +
					" does not have a zero arg constructor");
			ex.fillInStackTrace();
			throw ex;
		}
		if (log.isDebugEnabled()) {
			log.debug("class " + clazz + " has a zero arg constructor which is ok");
		}
	}

	private static void assertFields(IsXmlIoSerializableBuilder builder) throws IllegalStateException {
		Class<?> clazz = builder.getCurrentClass();
		if (Enum.class.isAssignableFrom(clazz)) {
			if (log.isDebugEnabled()) {
				log.debug("not checking the fields of " + Enum.class);
			}
			return;
		}
		
		Field[] fields = clazz.getDeclaredFields();
		if (log.isDebugEnabled()) {
			log.debug("" + clazz + " has a " + fields.length + " fields ");
		}
		IsXmlIoSerializableBuilder newBuider = new IsXmlIoSerializableBuilder(builder);
		newBuider.getCurrentClassParents().add(0, clazz);
		
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (log.isDebugEnabled()) {
				log.debug("checking field " + field.getName() + " of " + clazz);
			}
			
			if (Modifier.isStatic(field.getModifiers())) {
				if (log.isDebugEnabled()) {
					log.debug("skipping static field " + field.getName());
				}
			}  else {
				newBuider.setCurrentClass(field.getType());
				newBuider.setCurrentField(field.getName());
				isXmlIoSerializable(newBuider);
			}
		}
	}
	
	public static VISIBILITY getVISIBILITY(Field field ) {
		if (Modifier.isPrivate(field.getModifiers())) {
			return VISIBILITY.PRIVATE;
		}
		if (Modifier.isProtected(field.getModifiers())) {
			return VISIBILITY.PROTECTED;
		}
		if (Modifier.isPublic(field.getModifiers())) {
			return VISIBILITY.PUBLIC;
		}
		return VISIBILITY.DEFAULT;
	}
}
