package com.kusoduck.securities.html;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Service
public class MopsRedirector {
	private static Logger logger = LoggerFactory.getLogger(MopsRedirector.class);

	public String getRedirectUrl(String apiName, int rocYear, int quarter) {
		try {
			// 建立 HttpClient
			HttpClient client = HttpClient.newHttpClient();

			// 要送出的 JSON payload
			String jsonPayload = createJsonPayload(apiName,String.valueOf(rocYear), String.format("%02d", quarter));

			// 建立 POST 請求
			HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://mops.twse.com.tw/mops/api/redirectToOld"))
					.header("Content-Type", "application/json")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) " + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
					.header("Referer", "https://mops.twse.com.tw/mops/web/t163sb04").header("Origin", "https://mops.twse.com.tw")
					.header("Accept", "application/json, text/javascript, */*; q=0.01").header("Accept-Language", "zh-TW,zh;q=0.9")
					.POST(HttpRequest.BodyPublishers.ofString(jsonPayload)).build();

			// 發送請求並取得回應
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// 輸出結果
			logger.debug("回傳狀態碼: " + response.statusCode());
			logger.debug("回傳內容: ");
			logger.debug(response.body());
			return getUrlFromResponseBody(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String createJsonPayload(String apiName, String year, String season) {
		// 建立最外層的 JSON 物件
		JsonObject payload = new JsonObject();
		payload.addProperty("apiName", apiName);

		// 建立 "parameters" 內的 JSON 物件
		JsonObject parameters = new JsonObject();
		parameters.addProperty("year", year);
		parameters.addProperty("TYPEK", "sii");
		parameters.addProperty("season", season);
		parameters.addProperty("encodeURIComponent", 1);
		parameters.addProperty("firstin", 1);
		parameters.addProperty("off", 1);
		parameters.addProperty("step", 1);
		parameters.addProperty("isQuery", "Y");

		// 將 parameters 物件加入到 payload 中
		payload.add("parameters", parameters);

		// 使用 Gson 將物件轉換為字串
		// Gson gson = new Gson(); // 轉換為緊湊的單行 JSON
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // 轉換為格式化、易讀的 JSON

		return gson.toJson(payload);
	}

	private String getUrlFromResponseBody(String responseBody) {
		// 步驟 1: 建立 Gson 物件
		Gson gson = new Gson();

		// 步驟 2: 將 JSON 字串解析成 JsonObject
		JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

		// 步驟 3: 依序取得目標
		// a. 先從最外層取得 key 為 "result" 的物件
		JsonObject resultObject = jsonObject.getAsJsonObject("result");

		// b. 再從 "result" 物件中取得 key 為 "url" 的字串值
		return resultObject.get("url").getAsString();
	}
}
