package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		// map the response from the other microservice to use the info in here
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class,
				uriVariables);
		
		// obtain the value from the response
		CurrencyConversionBean response = responseEntity.getBody();
		
		return CurrencyConversionBean
				.builder()
				.id(response.getId())
				.from(from)
				.to(to)
				.conversionMultiple(response.getConversionMultiple())
				.quantity(quantity)
				.totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
				.port(response.getPort())
				.build();
	}
	
	@GetMapping("currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		// obtain the value from the response
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		
		return CurrencyConversionBean
				.builder()
				.id(response.getId())
				.from(from)
				.to(to)
				.conversionMultiple(response.getConversionMultiple())
				.quantity(quantity)
				.totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
				.port(response.getPort())
				.build();
	}
}
