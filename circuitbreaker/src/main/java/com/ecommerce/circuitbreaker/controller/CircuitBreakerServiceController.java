package com.ecommerce.circuitbreaker.controller;

import com.ecommerce.circuitbreaker.delegate.CircuitBreakerServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerServiceController {
	@Autowired
	CircuitBreakerServiceDelegate circuitBreakerServiceDelegate;

	@RequestMapping(value = "/GetProduitsMarge", method = RequestMethod.GET)
	public String calculerMargeProduit(){
		System.out.println("Going to call product service to get data");
		return circuitBreakerServiceDelegate.ServiceGetDataProductsMarge();
	}
	@RequestMapping(value = "/GetProduitsSorted", method = RequestMethod.GET)
	public String trierProduitsParOrdreAlphabetique(){
		System.out.println("Going to call product service to get data");
		return circuitBreakerServiceDelegate.ServiceGetDataProductsSorted();
	}
	@RequestMapping(value = "/GetProduitsPrix/{prix}", method = RequestMethod.GET)
	public String testeDeRequetes(@PathVariable int prix){
		System.out.println("Going to call product service to get data");
		return circuitBreakerServiceDelegate.ServiceGetDataProductsPrix(prix);
	}
	@RequestMapping(value = "/GetProduitsData/{id}", method = RequestMethod.GET)
	public String afficherUnProduit(@PathVariable int id){
		System.out.println("Going to call product service to get data of the id "+id);
		return circuitBreakerServiceDelegate.ServiceGetDataProductId(id);
	}
	@RequestMapping(value = "/GetProduitsData", method = RequestMethod.GET)
	public String listeProduits(){
		System.out.println("Going to call product service to get data");
		return circuitBreakerServiceDelegate.ServiceGetDataProducts();
	}
}
