package com.rab3tech.admin.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.AccountStatus;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
	
	public Optional<AccountStatus> findByName(String name);
}

