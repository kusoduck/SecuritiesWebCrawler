/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kusoduck.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
	private Connection conn;

	public MySQLConnector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String username = getEncryptedUser();
			String password = getEncryptedPass();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/securities?autoReconnect=true&useSSL=false&serverTimezone=UTC",
					username, password);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Connect fail");
		}
	}

	private String getEncryptedPass() {
		return "q3300640";
	}

	private String getEncryptedUser() {
		return "root";
	}

	public Connection getConn() {
		return conn;
	}
}
