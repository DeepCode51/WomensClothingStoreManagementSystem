package com.it.dao;



import com.it.entity.Inventory;

import java.util.HashMap;
import java.util.List;

public interface InventoryDAO {
	List<Inventory> selectAll(HashMap map);
	void add(Inventory inventory);
}