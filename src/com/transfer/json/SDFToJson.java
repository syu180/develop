package com.transfer.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SDFToJson {
	
	
	public static void main(String args[]) {
		readFileInList("src/com/transfer/resource/sample_1.sdf"); 
	  } 
		
	public static void readFileInList(String fileName) 
	  { 
		File file = new File(fileName);
        FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
	        String line;
	        System.out.println("Reading text file using FileReader");
	        Map<String, String> jMap = new HashMap();
	        List<Map<String, String>> jList = new ArrayList();
	        StringBuilder  sb = new StringBuilder();
	        while((line = br.readLine()) != null){
			    //process the line
	        	sb.append(line);
	        	sb.append("\n");
			    
			    if(line.startsWith("M  END")) {
			    	jMap.put("Structure", sb.toString());
			    } else if (line.startsWith(">")) {
			    	jMap.put(line.substring(line.indexOf("<")+1,line.lastIndexOf(">")), br.readLine());
			    }
			    if (jMap != null && jMap.size()>0
			    		&& line.startsWith("$$$$")) {
			    	jList.add(jMap);
			    	jMap = new HashMap();
			    	sb = new StringBuilder();
			    }
			}
	        String aListJson = JSON.toJSONString(jList);
	        CreateFileUtil.createJsonFile(aListJson, "src/com/transfer/resource", "output");
	        br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
        
	  } 
}
