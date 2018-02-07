package com.company.order;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@Named
@Path("/")
@EnableCircuitBreaker
public class OrderRest {
	private long id = 1;
	
	@Inject
	private OrderService orderService;


	@GET
	@Path("order")
	@Produces(MediaType.APPLICATION_JSON)
	public Order submitOrder(@QueryParam("idCustomer") long idCustomer,
			@QueryParam("idProduct") long idProduct,
			@QueryParam("amount") long amount) {

		Order order = new Order();
		

		order.setCustomer(orderService.getCustomers(idCustomer));
		order.setProduct(orderService.getProducts(idProduct));
		order.setId(id);
		order.setAmount(amount);
		order.setDateOrder(new Date());

		id++;
		return order;
	}

	
}