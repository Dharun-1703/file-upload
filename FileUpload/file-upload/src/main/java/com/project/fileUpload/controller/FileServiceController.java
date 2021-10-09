package com.project.fileUpload.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.project.fileUpload.model.CSVData;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class FileServiceController {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping("/upload")
	public String sayHello() {
		return "upload";
	}

	@RequestMapping(method = RequestMethod.POST,value = "/uploadCSV")
	public String uploadFile(@RequestParam(value = "file")  MultipartFile file ,ModelMap modelMap) throws IOException {
		
		CSVReader csvReader = null;
		List<String> documentKeys = new ArrayList<>();
		
		try {
		File tempFile = new File("src/main/resources/targetFile.tmp");

		try (OutputStream os = new FileOutputStream(tempFile)) {
		    os.write(file.getBytes());
		}
		csvReader = new CSVReader(new FileReader(tempFile));
		
		String[] lines;
		
		documentKeys = getColumnName(csvReader);
		System.out.println("column count"+documentKeys.size());			
		System.out.println("document keys"+documentKeys);			

		
			while ((lines = csvReader.readNext()) != null) {
				Map<String, String> csvRecord = new HashMap<String, String>();
				CSVData csvData = new CSVData();
				for (int i = 0; i < documentKeys.size(); i++) {
					csvRecord.put(documentKeys.get(i), lines[i]);
				}
				csvRecord.entrySet().forEach(entry -> {
				    System.out.println(entry.getKey() + " " + entry.getValue());
				});
				csvData.setcsvData(csvRecord);
				mongoTemplate.insert(csvData,"csvdata");
				}
			
		modelMap.put("message", "Successfuly uploaded the "+file.getOriginalFilename());
		}catch(Exception e) {
			modelMap.put("message", "Failed to upload "+file.getOriginalFilename()+ ":"+e.getMessage());
			System.out.println("Exception occured while uploading the file"+ file.getOriginalFilename() +e);
		}
		
		return "upload";
	}

	
	private List<String> getColumnName(CSVReader csvReader) {
		List<String> columnNames = new ArrayList<>();
		String[] header;
		try {
			header = csvReader.readNext();
			for(String column : header) {
				columnNames.add(column);
			}
		} catch (IOException e) {
			System.out.println("Error occured while finding the column names");			
		} 
		
		return columnNames;
	}
}
