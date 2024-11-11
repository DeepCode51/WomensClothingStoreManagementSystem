package com.it.dao;

import com.it.entity.Fav;

import java.util.HashMap;
import java.util.List;

public interface FavDAO {
	List<Fav> selectAll(HashMap map);
	void add(Fav fav);
	void delete(int id);
}
