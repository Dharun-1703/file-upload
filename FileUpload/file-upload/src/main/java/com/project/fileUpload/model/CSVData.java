package com.project.fileUpload.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.BasicDBObject;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document
public class CSVData {
	@Id
	private String id;
	@Field(value = "csvData")
	private BasicDBObject csvData;
	
	public Map getcsvData() {
		if(null != csvData) {
			return csvData.toMap();
		}
		return null;
	}
	
	public void setcsvData(Map csvData) {
		this.csvData = new BasicDBObject(csvData);
	}
	

}
