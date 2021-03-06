package com.bookdabang.tsh.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookdabang.common.domain.AddressVO;
import com.bookdabang.tsh.persistence.AddressDAO;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Inject
	public AddressDAO dao;

	@Override
	public AddressVO selectUserAddress(String userId) throws Exception {
		return dao.selectUserAddress(userId);
	}


	@Override
	public int nextAddressNo() throws Exception {
		// TODO Auto-generated method stub
		return dao.nextAddressNo();
	}


}
