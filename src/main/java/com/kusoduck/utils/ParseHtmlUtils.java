package com.kusoduck.utils;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseHtmlUtils {
	public static Document getDocument(String url) throws IOException {
		Random random = new Random();
		String ua = UserAgent.UA[random.nextInt(UserAgent.UA.length)];
		try {
			SSLUtils.trustAllHttpsCertificates();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Jsoup.connect(url).timeout(30 * 1000).userAgent(ua).maxBodySize(0).get();
	}
}
