package com.it.dao;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.it.entity.Category;
import com.it.entity.Sysuser;
public interface CategoryDAO {
	List<Category> selectAll(HashMap map);
    List<Category> selectchildAll(HashMap map);
	void add(Category category);
	void update(Category category);
	void delete(int id);
	Category findById(int id);
}
