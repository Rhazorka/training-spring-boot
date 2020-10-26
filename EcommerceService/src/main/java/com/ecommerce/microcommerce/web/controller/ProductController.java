package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


@Api( description="API pour es opérations CRUD sur les produits.")

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits
    @ApiOperation(value = "Récupère la liste des produis")
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {

        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    //Récupérer un produit par son Id
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {

        Product produit = productDao.findById(id);

        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        return produit;
    }

    //ajouter un produit
    @ApiOperation(value = "Pour ajouter un produit")
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();
        if (productAdded.getPrix()==0){
            throw new ProduitGratuitException("Le produit avec l'id " + productAdded.getId() + " a un prix égale a zéro");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @ApiOperation(value = "Pour supprimer un produit")
    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        productDao.delete(id);
    }
    @ApiOperation(value = "Pour mettre à jour un produit")
    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {
        if (product.getPrix()==0) {
            throw new ProduitGratuitException("Le produit avec l'id " + product.getId() + " a un prix égale a zéro");
        }
        productDao.save(product);
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {

        return productDao.chercherUnProduitCher(prix);
    }
    @ApiOperation(value = "Récupère la liste des produits est indique la marge de chacun d'eux")
    @GetMapping(value="/AdminProduits")
    public String calculerMargeProduit(){
        List<Product> products = productDao.findAll();
        int ecartTMP=0;
        String TMP="";
        for(Product p:products)
        {
            ecartTMP = p.getPrix()- p.getPrixAchat();
            TMP+=p.toString()+" : "+ecartTMP+"<br>";
        }
        return TMP;
    }
    @ApiOperation(value = "Récupère la liste des produits triés par ordre alphabétique")
    @GetMapping(value = "/AdminProduitsSorted")
    public List<Product> trierProduitsParOrdreAlphabetique(){
        List<Product> products = productDao.findAll();
        Collator collatorProduct = Collator.getInstance(Locale.FRENCH);
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return collatorProduct.compare(o1.getNom(), o2.getNom());
            }
        });
        return products;
    }

}
