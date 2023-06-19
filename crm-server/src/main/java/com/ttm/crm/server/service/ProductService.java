package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.ProductDao;
import com.ttm.crm.server.entity.Product;

@Service
public class ProductService {
	
	private ProductDao productDao;

	@Autowired
	public ProductService(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	public void add(Product product) {
		Denullifier.denullify(product);
		productDao.add(product);
	}

	public void update(Product product) {
		Denullifier.denullify(product);
		productDao.update(product);
	}
	
	public List<Product> find() {
		return productDao.find();
	}

	public Product find(int id) {
		return productDao.find(id);
	}
	
	public void delete(int id) {
		productDao.delete(id);
	}
	
}
