package com.it.entity;

import java.util.*;

public class Sysuser {
	private int id;
	private String usertype;
	private String username;
	private String userpwd;
	private String realname;
	private String filename;
	private String sex;
	private String tel;
	private String idcard;
	private String delstatus;
	private String shstatus;
    private double total;


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getDelstatus() {
		return delstatus;
	}
	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}

    public String getShstatus() {
        return shstatus;
    }

    public void setShstatus(String shstatus) {
        this.shstatus = shstatus;
    }
}
