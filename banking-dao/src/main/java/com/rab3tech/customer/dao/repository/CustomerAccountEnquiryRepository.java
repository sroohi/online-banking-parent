package com.rab3tech.customer.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.CustomerSaving;

/**
 * 
 * @author nagendra
 * 
 * Spring Data JPA repository
 *
 */
public interface CustomerAccountEnquiryRepository extends JpaRepository<CustomerSaving, Integer> {
}

