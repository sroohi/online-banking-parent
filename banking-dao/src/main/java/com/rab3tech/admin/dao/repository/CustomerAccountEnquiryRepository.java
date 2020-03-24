package com.rab3tech.admin.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.CustomerSavingEntity;

/**
 * 
 * @author nagendra
 *
 */
public interface CustomerAccountEnquiryRepository extends JpaRepository<CustomerSavingEntity, Integer> {
}

