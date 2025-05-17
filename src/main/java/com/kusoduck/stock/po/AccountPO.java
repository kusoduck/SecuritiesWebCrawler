package com.kusoduck.stock.po;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountPO {
	private String name;
	private String account;
	private String password;

	public AccountPO(String name, String account, String password) {
		this.name = name;
		this.account = account;
		this.password = password;
	}

	public AccountPO(ResultSet rs) throws SQLException {
		this.name = rs.getString("name");
		this.account = rs.getString("account");
		this.password = rs.getString("password");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
