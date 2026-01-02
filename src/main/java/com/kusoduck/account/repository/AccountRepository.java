package com.kusoduck.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kusoduck.account.entity.Account;

/**
 * 這是 Spring Data JPA 的 Repository 介面，用於資料存取層。
 * 它繼承了 JpaRepository，提供許多現成的 CRUD 方法（Create, Read, Update, Delete）。
 * JpaRepository<Account, String> 指定了操作的實體是 Account，主鍵類型是 String。
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    // 您可以在此處定義自訂的查詢方法，例如：
    // List<Account> findByAccount(String account);
}
