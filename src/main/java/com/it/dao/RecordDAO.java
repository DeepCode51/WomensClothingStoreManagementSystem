package com.it.dao;



import com.it.entity.Record;

import java.util.HashMap;
import java.util.List;

public interface RecordDAO {
	List<Record> selectAll(HashMap map);
	void add(Record record);
	void delete(int id);
	void update(Record record);
    Record findById(int id);
}