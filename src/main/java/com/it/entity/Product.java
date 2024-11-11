package com.it.entity;



public class Product {
    private Integer id;
    private String name;
    private String filename;
    private int categoryid;
    private int childid;
    private double price;
    private double tprice;
    private String content;
    private String issj;
    private String istj;


    private int kc;
    private int salenum;




    private Category fcategory;
    private Category ccategory;


    public int getSalenum() {
        return salenum;
    }

    public void setSalenum(int salenum) {
        this.salenum = salenum;
    }

    public double getTprice() {
        return tprice;
    }

    public void setTprice(double tprice) {
        this.tprice = tprice;
    }

    public int getKc() {
        return kc;
    }

    public void setKc(int kc) {
        this.kc = kc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getChildid() {
        return childid;
    }

    public void setChildid(int childid) {
        this.childid = childid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIssj() {
        return issj;
    }

    public void setIssj(String issj) {
        this.issj = issj;
    }

    public String getIstj() {
        return istj;
    }

    public void setIstj(String istj) {
        this.istj = istj;
    }

    public Category getFcategory() {
        return fcategory;
    }

    public void setFcategory(Category fcategory) {
        this.fcategory = fcategory;
    }

    public Category getCcategory() {
        return ccategory;
    }

    public void setCcategory(Category ccategory) {
        this.ccategory = ccategory;
    }
}