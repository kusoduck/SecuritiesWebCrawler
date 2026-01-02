package com.kusoduck.stock.app;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.kusoduck.stock.service.CrawlerService;

@SpringBootApplication
@ComponentScan(basePackages = "com.kusoduck")
@EntityScan(basePackages = "com.kusoduck") // 掃描所有 Entity
//@EnableJpaRepositories(basePackages = "com.kusoduck") // 掃描所有 Repository
public class SecuritiesDataCrawler {

	public static void main(String[] args) throws IOException {
		ApplicationContext context  = SpringApplication.run(SecuritiesDataCrawler.class, args);

        // 取得 Spring 管理的服務或元件
		CrawlerService service = context.getBean(CrawlerService.class);

        // 執行資料處理邏輯
		service.start();
	}
}


