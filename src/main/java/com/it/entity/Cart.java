package com.it.entity;

public class Cart {
    private Integer id;

    private Integer memberid;
    private Integer productid;
    private Integer num;


    private Product product;

    private double xjtotal;


    public double getXjtotal() {
        return xjtotal;
    }

    public void setXjtotal(double xjtotal) {
        this.xjtotal = xjtotal;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }
}