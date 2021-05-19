package com.infosys.ecart.OrderMS.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.infosys.ecart.OrderMS.entity.Order;

public interface OrderRepository extends CrudRepository<Order,Integer> {

	@Query("select PO from Order PO where PO.orderId = :orderId")
	public Order findByOrderId(Integer orderId);

}
