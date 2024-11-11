package com.it.entity;

import java.util.List;

public class Ordermsg {
    private int id;

    private String ddno;

    private int memberid;
    private String productmsg;

    private double total;

    private String addr;

    private String status;



    private String savetime;

    private String wlinfo;


    private Member member;


    public String getWlinfo() {
        return wlinfo;
    }

    public void setWlinfo(String wlinfo) {
        this.wlinfo = wlinfo;
    }

    private List<Orderdetails> orderdetailslist;

    public List<Orderdetails> getOrderdetailslist() {
        return orderdetailslist;
    }

    public void setOrderdetailslist(List<Orderdetails> orderdetailslist) {
        this.orderdetailslist = orderdetailslist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDdno() {
        return ddno;
    }

    public void setDdno(String ddno) {
        this.ddno = ddno;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getProductmsg() {
        return productmsg;
    }

    public void setProductmsg(String productmsg) {
        this.productmsg = productmsg;
    }
}