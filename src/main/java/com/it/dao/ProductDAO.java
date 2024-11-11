package com.it.dao;

import java.util.HashMap;
import java.util.List;

import com.it.entity.Product;

public interface ProductDAO {
	List<Product> selectAll(HashMap map);
	void add(Product product);
	void update(Product product);
	void delete(int id);
	Product findById(int id);
}