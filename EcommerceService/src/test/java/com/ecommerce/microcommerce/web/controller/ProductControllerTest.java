package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJacksonValue;

public class ProductControllerTest {

    Product testProduct1 = new Product(3, "Table de Ping Pong" , 750, 400);
    Product testProduct2 = new Product(4,"table en bois",95,35);
    ProductController monController= new ProductController();
    ProductDao productDao;
    /*
    @Test
    public void testAfficherUnProduitAvecId(){
        int id = 3;
        Product prodTmp = monController.afficherUnProduit(id);
        System.out.println(prodTmp);
        System.out.println(testProduct1);
        assertEquals(testProduct1,prodTmp);
    }

    @Test
    public void testListeProduits(){
        MappingJacksonValue listeDeProd = monController.listeProduits();

        System.out.println(listeDeProd.getValue());
    }
*/
}
