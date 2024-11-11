package com.it.dao;

import com.it.entity.Orderdetails;

import java.util.HashMap;
import java.util.List;

public interface OrderdetailsDAO {
	List<Orderdetails> selectAll(HashMap map);
	void add(Orderdetails orderdetails);
	void update(Orderdetails orderdetails);
	void delete(int id);
	Orderdetails findById(int id);
}