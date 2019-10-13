package com.generate.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GenerateImage {

	public static void main(String args[]) {
		List<Map<String, String>> texts = readMolText();
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://chemdrawdirect.perkinelmer.cloud/rest/generateImage";
		List<BufferedImage> images = new ArrayList();
		texts.stream().forEach(t -> {
			Map<String, Object> map = new HashMap();
			String Structure = t.get("Structure");
			map.put("chemData", Structure);
			map.put("chemDataType", "chemical/x-mdl-molfile");
			map.put("imageType", "image/png");
			JSONObject jsonObj = new JSONObject(map);
			byte[] bt = restTemplate.postForObject(url, jsonObj, byte[].class);
			try {
				BufferedImage new_image_buffer = ImageIO.read(new ByteArrayInputStream(bt));
				images.add(new_image_buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		Integer allWidth = 0; // å›¾ç‰‡æ€»å®½åº¦
		Integer allHeight = 0; // å›¾ç‰‡æ€»é«˜åº¦
		for (int i = 0; i < images.size(); i++) {
			// ç«–å?‘
			if (i == 0) {
				allWidth = images.get(0).getWidth();
			}
			allHeight += images.get(i).getHeight();
		}
		BufferedImage combined = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_RGB);
		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		// ç«–å?‘å?ˆæˆ?
		Integer height = 0;
		for (int j = 0; j < images.size(); j++) {
			g.drawImage(images.get(j), 0, height, null);
			height += images.get(j).getHeight();
		}
		try {
			ImageIO.write(combined, "png", new File("src/com/transfer/resource/test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static List readMolText() {
		File file = new File("src/com/transfer/resource/output.json");
		String input;
		List<Map<String, String>> texts = new ArrayList();
		try {
			input = FileUtils.readFileToString(file, "UTF-8");
			texts = (List<Map<String, String>>) JSONArray.parse(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texts;
	}
}
