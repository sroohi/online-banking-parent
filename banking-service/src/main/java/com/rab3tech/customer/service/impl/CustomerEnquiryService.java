package com.rab3tech.customer.service.impl;

import java.util.List;

import com.rab3tech.vo.CustomerSavingVO;

public interface CustomerEnquiryService {
	public CustomerSavingVO save(CustomerSavingVO customerSavingVO);
	List<CustomerSavingVO> findAll();
	CustomerSavingVO findById(int csaid);
	void deleteById(int csaid);
}
