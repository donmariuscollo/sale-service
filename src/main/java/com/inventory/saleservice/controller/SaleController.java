package com.inventory.saleservice.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inventory.saleservice.model.Product;
import com.inventory.saleservice.model.SaleBean;
import com.inventory.saleservice.service.SaleBeanService;

@Produces("application/json")
@Consumes("application/json")
@RestController
public class SaleController {
	
	@Autowired
	private SaleBeanService saleBeanService;	
		
	@PostMapping("/api/sale")
	public ResponseEntity<SaleBean> save(@RequestBody SaleBean saleBean){
		
		String path="http://localhost:8001/api/product";
		String url=path+"/{itemName}";
		
		RestTemplate rest=new RestTemplate();
		ResponseEntity<Product> response=null;
		try{
			response=rest.getForEntity(url, Product.class,saleBean.getItemName());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		if (!response.getStatusCode().equals(HttpStatus.FOUND)){
			return new ResponseEntity<SaleBean>(HttpStatus.BAD_REQUEST);
		}

		Product product=response.getBody();
		if (saleBean.getQuantity()>product.getQuantity())
			return new ResponseEntity<SaleBean>(HttpStatus.BAD_REQUEST);
				
		int totalQuantity=product.getQuantity()-saleBean.getQuantity();
		product.setQuantity(totalQuantity);
		
		//save product
		product=rest.postForObject(path,new HttpEntity<Product>(product), Product.class);

		//save sale bean
		saleBean.setCostPrice(product.getCostPrice());
		saleBean.setSellingPrice(product.getSellingPrice());		
		saleBean.setStatus(0);
		SaleBean newSaleBean=saleBeanService.save(saleBean);		

		return new ResponseEntity<SaleBean>(newSaleBean,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/api/sales")
	public ResponseEntity<List<SaleBean>> getList(){
		List<SaleBean> list=saleBeanService.getList();
		return new ResponseEntity<List<SaleBean>>(list,HttpStatus.OK);
	}

}
