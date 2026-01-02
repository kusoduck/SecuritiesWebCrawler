package com.kusoduck.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 這是一個 JPA 實體（Entity），對應到資料庫中的 `account` 資料表。
 * `@Entity` 註解標示這個類別是一個 JPA 實體。
 * `@Table` 註解指定它對應的資料表名稱。
 */
@Entity
@Table(name = "account")
public class Account {

    /**
     * `name` 欄位作為主鍵（Primary Key）。
     * `@Id` 標示這是主鍵。
     * `@Column(name = "name")` 指定對應的資料表欄位名稱。
     */
    @Id
    @Column(name = "name")
    private String name;

    /**
     * `account` 欄位。
     * `@Column(name = "account")` 指定對應的資料表欄位名稱。
     */
    @Column(name = "account")
    private String account;

    /**
     * `password` 欄位。
     * `@Column(name = "password")` 指定對應的資料表欄位名稱。
     */
    @Column(name = "password")
    private String password;

    // 您可以在此處加入建構函式、Getter 和 Setter
    public Account() {
    }

    public Account(String name, String account, String password) {
        this.name = name;
        this.account = account;
        this.password = password;
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
