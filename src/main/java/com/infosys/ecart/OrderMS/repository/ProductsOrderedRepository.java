package com.infosys.ecart.OrderMS.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.infosys.ecart.OrderMS.entity.Composite;
import com.infosys.ecart.OrderMS.entity.ProductsOrdered;

public interface ProductsOrderedRepository extends CrudRepository<ProductsOrdered,Composite>{
	
	@Query("select PO from ProductsOrdered PO where PO.prodId = :prodId")
	public List<ProductsOrdered> findByProdId(@Param("prodId") Integer prodId);
	

	@Query("select PO from ProductsOrdered PO where PO.buyerId = :buyerId")
	public List<ProductsOrdered> findByBuyerId(@Param("buyerId") String buyerId);
}
