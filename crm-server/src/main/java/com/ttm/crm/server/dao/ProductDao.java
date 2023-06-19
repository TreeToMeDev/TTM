package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Product;

@Component
public class ProductDao {

	private final SqlSession sqlSession;
	
	public ProductDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
		
	public List<Product> find() {
		return this.sqlSession.selectList("selectProducts");
	}
	
	public Product find(int id) {
		return this.sqlSession.selectOne("selectProduct", id);
	}
	
	public void add(Product product) {
		int result = this.sqlSession.insert("insertProduct", product);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(Product product) {
		int result = this.sqlSession.insert("updateProduct", product);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete (int id) {
		int result = this.sqlSession.delete("deleteProduct", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
}
