package com.infosys.ecart.OrderMS.service;

import java.util.List;
import com.infosys.ecart.OrderMS.dto.OrderDTO;
import com.infosys.ecart.OrderMS.dto.ProductsorderedDTO;
import com.infosys.ecart.OrderMS.exception.OrderMSException;

public interface OrderService {
	Integer placeOrder(OrderDTO orderDTO) throws OrderMSException;
	List<ProductsorderedDTO> viewOrdersByBuyerId(String buyerId) throws OrderMSException;
	List<ProductsorderedDTO> viewOrdersByProdId(Integer prodId) throws OrderMSException;
	OrderDTO getOrderDetails(Integer ordId) throws OrderMSException;
  
}
