package com.it.dao;

import java.util.HashMap;
import java.util.List;

import com.it.entity.Member;

public interface MemberDAO {
	List<Member> selectAll(HashMap map);
	void add(Member member);
	void delete(int id);
	void update(Member member);
	Member findById(int id);
}