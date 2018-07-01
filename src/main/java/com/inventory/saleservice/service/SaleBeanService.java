package com.inventory.saleservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.saleservice.model.SaleBean;
import com.inventory.saleservice.repository.SaleBeanRepository;

@Service
public class SaleBeanService {
	@Autowired
	SaleBeanRepository saleBeanRepository;
	
	
	public SaleBean save(SaleBean saleBean){
		return saleBeanRepository.save(saleBean);
	}
	
	public List<SaleBean> getList(){
		return (List<SaleBean>) saleBeanRepository.findAll();
	}
}
