package com.infosys.ecart.OrderMS.controller;

import java.util.List;
import javax.validation.Valid;

//import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;

import com.infosys.ecart.OrderMS.service.OrderService;
import com.infosys.ecart.OrderMS.dto.CartDTO;
import com.infosys.ecart.OrderMS.dto.OrderDTO;
import com.infosys.ecart.OrderMS.dto.ProductDTO;
import com.infosys.ecart.OrderMS.dto.ProductsorderedDTO;
import com.infosys.ecart.OrderMS.entity.ProductsOrdered;
//import com.infosys.ecart.OrderMS.entity.Order;
import com.infosys.ecart.OrderMS.exception.OrderMSException;
import com.infosys.ecart.OrderMS.repository.ProductsOrderedRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/order-api")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	ProductsOrderedRepository productsOrderedRepository;
	
	@PostMapping("/buyer/placeOrder")
	public ResponseEntity<String> addOrder(@Valid @RequestBody OrderDTO orderDTO)  throws OrderMSException{
			//String buyerUri = "";// for isPreveleged and rewardpoints fields
			String cartUri = "http://localhost:8200/buyer-api/cart/"; /// give buyerId
			String productUri = "http://localhost:8100/seller/viewProduct/";///add prodId to uri and gets price and stock in
			String updateStock = "http://localhost:8100/updateStock";
			RestTemplate rest = new RestTemplate();
			
			CartDTO c= rest.getForObject(cartUri+orderDTO.getBuyerId(),CartDTO.class);
			
			ProductDTO pd = rest.getForObject(productUri+c.getProdId(),ProductDTO.class);
			Float amount = pd.getPrice() * c.getQuantity();
			orderDTO.setAmount(amount);
			
			
			pd.setStock(pd.getStock()-c.getQuantity());
			rest.put(updateStock,pd);
			

			String msg = "Your order is placed with order ID : "+orderService.placeOrder(orderDTO);
			
			ProductsOrdered p = new ProductsOrdered();
			p.setBuyerId(orderDTO.getBuyerId());
			p.setSellerId(pd.getSellerId());
			p.setQuantity(c.getQuantity());
			p.setProdId(c.getProdId());
			productsOrderedRepository.save(p);
			return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	

	@GetMapping("/buyer/viewProductsOrdered/{buyerId}")
	public ResponseEntity<List<ProductsorderedDTO>> viewOrderByBuyerId(@PathVariable String buyerId) throws OrderMSException{
		return new ResponseEntity<>(orderService.viewOrdersByBuyerId(buyerId),HttpStatus.OK);
	}
	
	@GetMapping("/buyer/viewOrder/{orderId}")
	public ResponseEntity<OrderDTO> viewOrder(@PathVariable Integer orderId) throws OrderMSException{
		return new ResponseEntity<>(orderService.getOrderDetails(orderId),HttpStatus.OK);
	}
	
	
	@GetMapping("/seller/viewProductsOrdered/{prodId}")
	public ResponseEntity<List<ProductsorderedDTO>> viewOrderByProdId(@PathVariable Integer prodId) throws OrderMSException{
		return new ResponseEntity<>(orderService.viewOrdersByProdId(prodId),HttpStatus.OK);
	}
	
	}	
		

