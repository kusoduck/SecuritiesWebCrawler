package com.kusoduck.utils;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.kusoduck.securities.html.parser.UserAgent;

public class ParseHtmlUtils {
	public static Document getDocument(String url) throws IOException {
		Random random = new Random();
		String ua = UserAgent.UA[random.nextInt(UserAgent.UA.length)];
		return Jsoup.connect(url).timeout(10 * 1000).validateTLSCertificates(false).userAgent(ua).maxBodySize(0).get();
	}
}
