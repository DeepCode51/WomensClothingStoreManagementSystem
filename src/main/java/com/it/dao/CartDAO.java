package com.it.dao;

import java.util.HashMap;
import java.util.List;

import com.it.entity.Cart;

public interface CartDAO {
	List<Cart> selectAll(HashMap map);
	void add(Cart cart);
	void delete(int id);
	Cart findById(int id);
    void update(Cart cart);
}