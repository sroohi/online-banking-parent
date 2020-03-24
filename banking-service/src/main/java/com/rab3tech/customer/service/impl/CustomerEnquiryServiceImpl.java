package com.rab3tech.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.rab3tech.admin.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3tech.dao.entity.CustomerSavingEntity;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.CustomerSavingVO;

@Service
@Transactional
public class CustomerEnquiryServiceImpl implements CustomerEnquiryService {

	@Autowired
	private CustomerAccountEnquiryRepository customerAccountEnquiryRepository;

	@Override
	public CustomerSavingVO save(CustomerSavingVO customerSavingVO) {
		customerSavingVO.setDoa(new Date());
		customerSavingVO.setAppref("S-"+Utils.genRandomAlphaNum());
		boolean b = TransactionSynchronizationManager.isActualTransactionActive();
		if (b) {
			System.out.println("Ahahahahha tx is working!!!!!!!!!!!!!!");
		}
		CustomerSavingEntity entity = new CustomerSavingEntity();
		BeanUtils.copyProperties(customerSavingVO, entity);
		CustomerSavingEntity savingEntity = customerAccountEnquiryRepository.save(entity);
		customerSavingVO.setCsaid(savingEntity.getCsaid());
		return customerSavingVO;
	}

	// DRY
	@Override
	public List<CustomerSavingVO> findAll() {
		List<CustomerSavingEntity> customerSavingList = customerAccountEnquiryRepository.findAll();
		return convertEntityIntoVO(customerSavingList);
	}

	@Override
	public CustomerSavingVO findById(int csaid) {
		CustomerSavingEntity customerSavingEntity = customerAccountEnquiryRepository.findById(csaid).get();
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		BeanUtils.copyProperties(customerSavingEntity, customerSavingVO);
		return customerSavingVO;
	}

	@Override
	public void deleteById(int csaid) {
		customerAccountEnquiryRepository.deleteById(csaid);
	}

	private List<CustomerSavingVO> convertEntityIntoVO(List<CustomerSavingEntity> customerSavingList) {
		List<CustomerSavingVO> customerSavingVOList = new ArrayList<>();
		for (CustomerSavingEntity customerSavingEntity : customerSavingList) {
			CustomerSavingVO customerSavingVO = new CustomerSavingVO();
			BeanUtils.copyProperties(customerSavingEntity, customerSavingVO);
			customerSavingVOList.add(customerSavingVO);
		}
		return customerSavingVOList;
	}

}
