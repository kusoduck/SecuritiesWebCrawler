package com.kusoduck.stock.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kusoduck.account.entity.Account;
import com.kusoduck.account.repository.AccountRepository;

/**
 * 這是一個服務層（Service Layer），用於處理業務邏輯。 `@Service` 註解標示這個類別是一個服務元件。 它透過建構函式注入 `AccountRepository`，以便與資料庫互動。
 */
@Service
public class AccountService {

	private final AccountRepository repository;

	public AccountService(AccountRepository accountRepository) {
		repository = accountRepository;
	}

	/**
	 * 根據名稱尋找帳號。
	 *
	 * @param name 帳號名稱（主鍵）。
	 * @return 如果找到，返回一個包含 Account 的 Optional；否則返回空的 Optional。
	 */
	public Optional<Account> findById(String name) {
		return repository.findById(name);
	}

}