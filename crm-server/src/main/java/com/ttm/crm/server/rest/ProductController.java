package com.ttm.crm.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Product;
import com.ttm.crm.server.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
    public List<Product> get() {
		return productService.find();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> get(@PathVariable("id") int id) {
		Product product = productService.find(id);
		return ResponseEntity.ok().body(product);
	}
	
	@PostMapping
	public void post(@Valid @RequestBody Product product) {
		productService.add(product);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Product> patch(@PathVariable("id") int id, @RequestBody Product product) {
		product.setId(id);
		productService.update(product);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		productService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
