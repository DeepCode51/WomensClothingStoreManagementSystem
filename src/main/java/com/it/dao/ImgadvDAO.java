package com.it.dao;


import com.it.entity.Imgadv;

import java.util.HashMap;
import java.util.List;

public interface ImgadvDAO {
	List<Imgadv> selectAll(HashMap map);
	void add(Imgadv imgadv);
	void update(Imgadv imgadv);
	void delete(int id);
    Imgadv findById(int id);
}