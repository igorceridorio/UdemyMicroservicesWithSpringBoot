package com.in28minutes.microservices.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitConfiguration {

	private int maximun;
	private int minimum;
}
