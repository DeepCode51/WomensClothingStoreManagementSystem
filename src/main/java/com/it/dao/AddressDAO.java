package com.it.dao;



import com.it.entity.Address;

import java.util.HashMap;
import java.util.List;

public interface AddressDAO {
	List<Address> selectAll(HashMap map);
	void add(Address address);
	void delete(int id);
	void update(Address address);
    Address findById(int id);
}