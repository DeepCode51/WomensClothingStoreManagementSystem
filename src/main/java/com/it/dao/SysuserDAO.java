package com.it.dao;
import java.util.*;

import com.it.entity.Sysuser;
public interface SysuserDAO {
	Sysuser findById(int id);
	void add(Sysuser sysuser);
	List<Sysuser> selectAll(HashMap<String, String> map);
	void update(Sysuser sysuser);
	void delete(int id);
	void updatetotal(Sysuser sysuser);
}
