package com.it.entity;


public class Chat {

  private int id;
  private int memberid;
  private String content;
  private String savetime;
  private String hfcontent;
    private Member member;

    public int getMemberid() {
        return memberid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getSavetime() {
    return savetime;
  }

  public void setSavetime(String savetime) {
    this.savetime = savetime;
  }


  public String getHfcontent() {
    return hfcontent;
  }

  public void setHfcontent(String hfcontent) {
    this.hfcontent = hfcontent;
  }

}
