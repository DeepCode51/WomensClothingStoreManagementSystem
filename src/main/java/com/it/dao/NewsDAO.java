package com.it.dao;



import com.it.entity.News;

import java.util.HashMap;
import java.util.List;

public interface NewsDAO {
	List<News> selectAll(HashMap map);
	void add(News news);
	void delete(int id);
	void update(News news);
    News findById(int id);
}