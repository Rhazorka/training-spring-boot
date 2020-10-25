package com.ecommerce.circuitbreaker.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

// Service class
@Service
public class CircuitBreakerServiceDelegate {
	@Autowired
	RestTemplate restTemplate;
	// implement a callStudentService method and enabled it by hystrix. we wanna return student detail with a normal flow
	@HystrixCommand(fallbackMethod = "callProductServiceAndGetData_FallBack")
	public String ServiceGetDataProductsMarge() {
		String contenu = restTemplate.exchange("http://localhost:9090/AdminProduits"
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
				}).getBody();
		System.out.println("Reponse received as " + contenu + " - " + new Date());
		return "Réponse reçu. Voici les marges :<br>" + contenu;
	}

	@HystrixCommand(fallbackMethod = "callProductServiceAndGetData_FallBack")
	public String ServiceGetDataProductsSorted(){
		String contenu = restTemplate.exchange("http://localhost:9090/AdminProduitsSorted"
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
				}).getBody();
		System.out.println("Reponse received as "+contenu+" - "+ new Date());
		return "Réponse reçu. Voici les produits trier par ordre alphabétique :<br>"+contenu;
	}
	@HystrixCommand(fallbackMethod = "callProductServiceAndGetDataPrice_FallBack")
	public String ServiceGetDataProductsPrix(int prix){
		String contenu = restTemplate.exchange("http://localhost:9090/test/produits/{prix}"
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
				}
				, prix).getBody();
		System.out.println("Reponse received as "+contenu+" - "+ new Date());
		return "Réponse reçu. Voici les produits et leur prix : <br>"+contenu;
	}
	@HystrixCommand(fallbackMethod = "callProductServiceAndGetData_FallBack")
	public String ServiceGetDataProducts(){
		String contenu = restTemplate.exchange("http://localhost:9090/Produits"
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
				}).getBody();
		System.out.println("Reponse received as "+contenu+" - "+ new Date());
		return "Réponse reçu. Voici les produits et leur détail : <br>"+contenu;
	}
	@HystrixCommand(fallbackMethod = "callProductServiceAndGetDataId_FallBack")
	public String ServiceGetDataProductId(int id){
		String contenu = restTemplate.exchange("http://localhost:9090/Produits/{id}"
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
				}
				, id).getBody();
		System.out.println("Reponse received as "+contenu+" - "+ new Date());
		return "Réponse reçu. Voici le produits "+id+" et son détail : <br>"+contenu;
	}
	private String callProductServiceAndGetData_FallBack(){
		System.out.println("Product service is down!! fallback route enable...");
		return "Circuit breaker enabled!! No response from Product service at this moment. ";
	}
	private String callProductServiceAndGetDataId_FallBack(int id){
		System.out.println("Product service is down!! fallback route enable...");
		return "Circuit breaker enabled!! No response from Product service at this moment. you try too search a product with the id :"+id ;
	}
	private String callProductServiceAndGetDataPrice_FallBack(int prix){
		System.out.println("Product service is down!! fallback route enable...");
		return "Circuit breaker enabled!! No response from Product service at this moment. you try too search a product more expensive then :"+prix ;
	}


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
