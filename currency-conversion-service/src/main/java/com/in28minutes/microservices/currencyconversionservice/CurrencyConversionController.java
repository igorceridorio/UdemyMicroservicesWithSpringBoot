package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

	@GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		return CurrencyConversionBean
				.builder()
				.id(1L)
				.from(from)
				.to(to)
				.conversionMiltiple(BigDecimal.ONE)
				.quantity(quantity)
				.totalCalculatedAmount(new BigDecimal("1000"))
				.port(0)
				.build();
	}
}
