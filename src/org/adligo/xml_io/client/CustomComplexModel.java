package org.adligo.xml_io.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomComplexModel {
	private int id = 1;
	private short sid = 2;
	private long lid = 3;

	private List<String> stringsList = getStringsPrivate();
	private double did = 4.4;
	private float fid = (float) 5.5;
	private String [] stringsArray = new String [] {"ba", "bb"};
	private char[] chars = new char [] {'d','e'};
	private byte [] bytes = new byte [] {0,1,2};
	private boolean [] bools = new boolean [] {false, true};
	private Map<Integer, String> keyValues = getKeyVaulesPrivate();
	
	private List<String> getStringsPrivate() {
		List<String> toRet = new ArrayList<String>();
		toRet.add("ab");
		toRet.add("ac");
		
		return toRet;
	}
	
	private Map<Integer, String> getKeyVaulesPrivate() {
		Map<Integer, String> toRet = new HashMap<Integer, String>();
		toRet.put(1, "a");
		toRet.put(2, "b");
		return toRet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getSid() {
		return sid;
	}

	public void setSid(short sid) {
		this.sid = sid;
	}

	public long getLid() {
		return lid;
	}

	public void setLid(long lid) {
		this.lid = lid;
	}

	public double getDid() {
		return did;
	}

	public void setDid(double did) {
		this.did = did;
	}

	public float getFid() {
		return fid;
	}

	public void setFid(float fid) {
		this.fid = fid;
	}

	public char[] getChars() {
		return chars;
	}

	public void setChars(char[] chars) {
		this.chars = chars;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public boolean[] getBools() {
		return bools;
	}

	public void setBools(boolean[] bools) {
		this.bools = bools;
	}

	public List<String> getStringsList() {
		return stringsList;
	}

	public void setStringsList(List<String> stringsList) {
		this.stringsList = stringsList;
	}

	public String[] getStringsArray() {
		return stringsArray;
	}

	public void setStringsArray(String[] stringsArray) {
		this.stringsArray = stringsArray;
	}

	public Map<Integer, String> getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(Map<Integer, String> keyValues) {
		this.keyValues = keyValues;
	}
}
