package com.infosys.ecart.OrderMS.service;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.ecart.OrderMS.dto.OrderDTO;
import com.infosys.ecart.OrderMS.dto.ProductsorderedDTO;
import com.infosys.ecart.OrderMS.entity.Order;
import com.infosys.ecart.OrderMS.entity.ProductsOrdered;
import com.infosys.ecart.OrderMS.exception.OrderMSException;
import com.infosys.ecart.OrderMS.repository.OrderRepository;
import com.infosys.ecart.OrderMS.repository.ProductsOrderedRepository;

@Service
@Transactional
public class OrderServiceImpl  implements OrderService{

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductsOrderedRepository productsorderedRepository;
	
	
	@Override
	public OrderDTO getOrderDetails(Integer orderId) throws OrderMSException {
		
		Order d = orderRepository.findByOrderId(orderId);
		
		if(d == null)
		{
			throw new OrderMSException("NO_SUCH_ORDER_EXIST");
		}
		
			    OrderDTO orderDTO=new OrderDTO();
			    orderDTO.setOrderId(d.getOrderId());
				orderDTO.setBuyerId(d.getBuyerId());
				orderDTO.setAmount(d.getAmount());
				orderDTO.setDate(d.getDate());
				orderDTO.setAddress(d.getAddress());
				orderDTO.setStatus(d.getStatus());
			
			return orderDTO;
	}

	@Override
	public Integer placeOrder(OrderDTO orderDTO) throws OrderMSException {
		Order o = new Order();
		o.setOrderId(orderDTO.getOrderId());
		o.setBuyerId(orderDTO.getBuyerId());
		o.setAmount(orderDTO.getAmount());
		o.setDate(orderDTO.getDate());
		o.setAddress(orderDTO.getAddress());
		o.setStatus(orderDTO.getStatus());
		
		orderRepository.save(o);
		return o.getOrderId();
	}
	@Override
	public List<ProductsorderedDTO> viewOrdersByBuyerId(String buyerId) throws OrderMSException {
		
	
		Iterable<ProductsOrdered> iterable = productsorderedRepository.findByBuyerId(buyerId);
		List<ProductsorderedDTO> list = new ArrayList<ProductsorderedDTO>();
		if(iterable == null) {
			throw new OrderMSException("Order list is empty.");
		}
			for(ProductsOrdered product : iterable) {
				ProductsorderedDTO p = new ProductsorderedDTO();
				
				p.setBuyerId(product.getBuyerId());
				p.setProdId(product.getProdId());
				p.setSellerId(product.getSellerId());
				p.setQuantity(product.getQuantity());
				
				list.add(p);
			}
			
			return list;
	}

	@Override
	public List<ProductsorderedDTO> viewOrdersByProdId(Integer prodId) throws OrderMSException {
		
	
		Iterable<ProductsOrdered> iterable = productsorderedRepository.findByProdId(prodId);
		List<ProductsorderedDTO> list = new ArrayList<ProductsorderedDTO>();
		if(iterable == null) {
			throw new OrderMSException("Order list is empty.");
		}
			for(ProductsOrdered product : iterable) {
				ProductsorderedDTO p = new ProductsorderedDTO();
				
				p.setBuyerId(product.getBuyerId());
				p.setProdId(product.getProdId());
				p.setSellerId(product.getSellerId());
				p.setQuantity(product.getQuantity());
				
				list.add(p);
			}
			
			return list;
	}
}



	
	
	
	
	
	
	
	