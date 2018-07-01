package com.inventory.saleservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.saleservice.model.SaleBean;

@Repository
public interface SaleBeanRepository extends CrudRepository<SaleBean,Long>{

}
