package com.company.order;

import java.lang.management.ManagementFactory;

import javax.inject.Inject;
import javax.management.ObjectName;

import org.apache.polygene.library.circuitbreaker.CircuitBreaker;
import org.apache.polygene.library.circuitbreaker.CircuitBreakers;
import org.apache.polygene.library.circuitbreaker.jmx.CircuitBreakerJMX;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class OrderService {

	@Inject
	RestTemplate restTemplate;

	CircuitBreaker cb = new CircuitBreaker(3, 25000, CircuitBreakers.in( IllegalArgumentException.class ));

	public OrderService() throws Exception {

		System.out.println("in constuctor");
		final ObjectName name = new ObjectName("circuit-breakers",
				"zest-circuit-breaker", "com.company.order");
		ManagementFactory.getPlatformMBeanServer().registerMBean(
				new CircuitBreakerJMX(cb, name), name);
	}

	public Customer reliable(long customerId) {
		Customer customer = new Customer();
		customer.setId(00000);
		System.out.println("In Reliable");
		return customer;
	}

	public Product reliableProduct(long customerId) {
		Product product = new Product();
		product.setId(00000);
		return product;
	}

	@HystrixCommand(fallbackMethod = "reliable" )
	public Customer getCustomers(long idCustomer) {

		return restTemplate.getForObject(
				"http://localhost:8090/customer?id={id}", Customer.class,
				idCustomer);

	}

	public Product getProducts(long idCustomer) {

		try {
			System.out.println(cb.status().name());
			if (cb.isOn()) {
				Product response = restTemplate.getForObject(
						"http://localhost:8084/product?id={id}", Product.class,
						idCustomer);
				cb.success();
				return response;
			} else {
				return reliableProduct(idCustomer);
			}
		} catch (Exception e) {
			//cb.trip();

			System.out.println(e);cb.throwable(e);
			return reliableProduct(idCustomer);
			
			//throw new RuntimeException();
		}
	}

}
