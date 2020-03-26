package com.rab3tech.customer.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.rab3tech.admin.dao.repository.AccountTypeRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3tech.dao.entity.AccountStatus;
import com.rab3tech.dao.entity.AccountType;
import com.rab3tech.dao.entity.CustomerSaving;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.service.exception.BankServiceException;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.EmailVO;

@Service
@Transactional
public class CustomerEnquiryServiceImpl implements CustomerEnquiryService {

	@Autowired
	private CustomerAccountEnquiryRepository customerAccountEnquiryRepository;
	
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${bank.from.email:nagen@gmail.com}")
	private String fromEmail;
	

	@Override
	public CustomerSavingVO save(CustomerSavingVO customerSavingVO) {
		customerSavingVO.setDoa(new Date());
		customerSavingVO.setAppref("AS-"+Utils.genRandomAlphaNum());
		boolean b = TransactionSynchronizationManager.isActualTransactionActive();
		if (b) {
			System.out.println("Ahahahahha tx is working!!!!!!!!!!!!!!");
		}
		
		CustomerSaving entity = new CustomerSaving();
		BeanUtils.copyProperties(customerSavingVO, entity,new String[] {"accType","status"});
		Optional<AccountType> oaccType=accountTypeRepository.findByName(customerSavingVO.getAccType());
		if(oaccType.isPresent()) {
			entity.setAccType(oaccType.get());
		}else {
			throw new BankServiceException("Hey this "+customerSavingVO.getAccType()+" account type is not valid!");
		}
		
		AccountStatus accountStatus=new AccountStatus();
		accountStatus.setId(1);
		entity.setStatus(accountStatus);
		
		CustomerSaving savingEntity = customerAccountEnquiryRepository.save(entity);
		customerSavingVO.setCsaid(savingEntity.getCsaid());
		
		System.out.println("Email sending .."+LocalDateTime.now());
		emailService.sendEquiryEmail(new EmailVO(customerSavingVO.getEmail(), fromEmail, null, "Hello! your account enquiry is submitted successfully.",customerSavingVO.getName()));
		System.out.println("Email done .."+LocalDateTime.now());
		
		return customerSavingVO;
	}

	// DRY
	@Override
	public List<CustomerSavingVO> findAll() {
		List<CustomerSaving> customerSavingList = customerAccountEnquiryRepository.findAll();
		return convertEntityIntoVO(customerSavingList);
	}

	@Override
	public CustomerSavingVO findById(int csaid) {
		CustomerSaving customerSavingEntity = customerAccountEnquiryRepository.findById(csaid).get();
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		BeanUtils.copyProperties(customerSavingEntity, customerSavingVO,new String[]{"accType","status"});
		customerSavingVO.setAccType(customerSavingEntity.getAccType().getName());
		customerSavingVO.setStatus(customerSavingEntity.getStatus().getName());
		return customerSavingVO;
	}

	@Override
	public void deleteById(int csaid) {
		customerAccountEnquiryRepository.deleteById(csaid);
	}

	private List<CustomerSavingVO> convertEntityIntoVO(List<CustomerSaving> customerSavingList) {
		List<CustomerSavingVO> customerSavingVOList = new ArrayList<>();
		for (CustomerSaving customerSavingEntity : customerSavingList) {
			CustomerSavingVO customerSavingVO = new CustomerSavingVO();
			BeanUtils.copyProperties(customerSavingEntity, customerSavingVO,new String[]{"accType","status"});
			customerSavingVO.setAccType(customerSavingEntity.getAccType().getName());
			customerSavingVO.setStatus(customerSavingEntity.getStatus().getName());
			customerSavingVOList.add(customerSavingVO);
		}
		return customerSavingVOList;
	}

}
