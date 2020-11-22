/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class MySQLConnector {
	private static Logger logger = Logger.getLogger(MySQLConnector.class);
	private static String propFileName = System.getProperty("prop.database");
	private static Properties prop = new Properties();
	private static Connection conn;

	static {
		try {
			FileReader fileReader = new FileReader(propFileName);
			prop.load(fileReader);
		} catch (IOException e) {
			logger.error("Database property file loading fail", e);
		}
	}

	private MySQLConnector() {

	}

	public static Connection getConn(String schema) {
		if (StringUtils.isNotBlank(schema)) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/" + schema
								+ "?autoReconnect=true&useSSL=false&serverTimezone=UTC",
						prop.getProperty("user"), prop.getProperty("password"));
				conn.setAutoCommit(false);
			} catch (SQLException | ClassNotFoundException e) {
				logger.error("Database Connecting fail", e);
			}
		} else {
			logger.error("database schema can't be empty");
		}
		return conn;
	}
}
