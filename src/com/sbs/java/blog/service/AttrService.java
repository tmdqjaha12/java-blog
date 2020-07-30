package com.sbs.java.blog.service;

import java.sql.Connection;

import com.sbs.java.blog.dao.AttrDao;
import com.sbs.java.blog.dto.Attr;

public class AttrService extends Service {

	private AttrDao attrDao;

	public AttrService(Connection dbConn) {
		attrDao = new AttrDao(dbConn);
	}

	public Attr get(String name) {
		String[] nameBits = name.split("__");
		String relTypeCode = nameBits[0];
		int relId = Integer.parseInt(nameBits[1]);
		String typeCode = nameBits[2];
		String type2Code = nameBits[3];
		
		return attrDao.get(relTypeCode, relId, typeCode, type2Code);
	}

	public int setValue(String name, String value) {
		String[] nameBits = name.split("__");
		String relTypeCode = nameBits[0];
		int relId = Integer.parseInt(nameBits[1]);
		String typeCode = nameBits[2];
		String type2Code = nameBits[3];

		return attrDao.setValue(relTypeCode, relId, typeCode, type2Code, value);
	}

	public String getValue(String name) {
		String[] nameBits = name.split("__");
		String relTypeCode = nameBits[0];
		int relId = Integer.parseInt(nameBits[1]);
		String typeCode = nameBits[2];
		String type2Code = nameBits[3];
		
		return attrDao.getValue(relTypeCode, relId, typeCode, type2Code);
	}

	public int remove(String name) {
		String[] nameBits = name.split("__");
		String relTypeCode = nameBits[0];
		int relId = Integer.parseInt(nameBits[1]);
		String typeCode = nameBits[2];
		String type2Code = nameBits[3];
		
		return attrDao.remove(relTypeCode, relId, typeCode, type2Code);
	}
}