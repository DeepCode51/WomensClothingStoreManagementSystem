package com.it.controller;

import javax.annotation.Resource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.it.entity.Product;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.entity.Member;
import com.it.entity.Sysuser;
import com.it.dao.MemberDAO;
import com.it.dao.SysuserDAO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.Info;

import java.util.*;

@Controller
public class MemberController extends BaseController {

	@Resource
	MemberDAO memberDAO;


    //判断用户是否登录
    @ResponseBody
    @RequestMapping("checkmember")
    public HashMap<String, Object> checkmember(HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        if (sessionmember != null) {
            Member member = memberDAO.findById(sessionmember.getId());
            res.put("sessionmember", member);
            res.put("data", 200);

        } else {
            res.put("data", 400);
        }
        return res;
    }


    //退出
    @ResponseBody
    @RequestMapping("memberExit")
    public void memberexit(HttpServletRequest request) {
        request.getSession().removeAttribute("sessionmember");
    }

    //登录
    @ResponseBody
    @RequestMapping("Login")
    public HashMap<String, Object> Login(Member member, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("uname", member.getUname());
        map.put("upass", member.getUpass());
        List<Member> list = memberDAO.selectAll(map);
        if (list.size() == 0) {
            res.put("data", 400);
        } else {
            Member mmm = list.get(0);
            if(mmm.getStatus().equals("正常")){
                request.getSession().setAttribute("sessionmember", mmm);
                res.put("sessionmember", mmm);
                res.put("data", 200);
            }else{
                res.put("data", 300);
            }
        }
        return res;
    }
	
	
	//用户列表
	@ResponseBody
	@RequestMapping("admin/memberList")
	public HashMap<String,Object> memberList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
		String key = request.getParameter("key");
		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("key", key);
		List<Member> objectlist = memberDAO.selectAll(map);
		PageHelper.startPage(pageNum, pageSize);
		List<Member> list = memberDAO.selectAll(map);
		PageInfo<Member> pageInfo = new PageInfo<Member>(list);
		res.put("pageInfo", pageInfo);
		res.put("list", objectlist);
		return res;
	}


    //检查用户名的唯一性
    @ResponseBody
    @RequestMapping("checkUname")
    public HashMap<String, Object> checkUname(String uname, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("uname", uname);
        List<Member> list = memberDAO.selectAll(map);
        if(list.size()==0){
            res.put("data", 200);
        }else{
            res.put("data", 400);
        }
        return  res;
    }

    //用户注册
    @ResponseBody
    @RequestMapping("register")
    public HashMap<String, Object> register(Member member, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        member.setStatus("正常");
        memberDAO.add(member);
        res.put("data", 200);
        return res;
    }

    //个人信息
    @ResponseBody
    @RequestMapping("memberShow")
    public HashMap<String, Object> memberShow(HttpServletRequest request) {
        Member member = (Member)request.getSession().getAttribute("sessionmember");
        HashMap<String, Object> res = new HashMap<String, Object>();
        Member mmm = memberDAO.findById(member.getId());
        res.put("member", mmm);
        return res;
    }


    //修改个人信息
    @ResponseBody
    @RequestMapping("memberEdit")
    public HashMap<String, Object> memberEdit(Member member, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        memberDAO.update(member);
        res.put("data", 200);
        return res;
    }


    //删除用户
	@ResponseBody
	@RequestMapping("admin/memberDel")
	public HashMap<String,Object> memberDel(int id,HttpServletRequest request) {
		memberDAO.delete(id);
		return null;
	}

    /**
     * 修改密码
     */

    @ResponseBody
    @RequestMapping("passwordedit")
    public HashMap<String,Object> passwordedit(HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        Member member = (Member)request.getSession().getAttribute("sessionmember");
        Member mmm  = memberDAO.findById(member.getId());
        String upass = request.getParameter("upass");
        String nuserpassword = request.getParameter("nuserpassword");
        if(mmm.getUpass().equals(upass)){
            mmm.setUpass(nuserpassword);
            memberDAO.update(mmm);
            res.put("data",200);
        }else{
            res.put("data",400);
        }
        return res;
    }


    /**
     * 账户冻结
     */
    @ResponseBody
    @RequestMapping("admin/statusedit")
    public void statusedit(int id,HttpServletRequest request) {
        Member member =  memberDAO.findById(id);
        if(member.getStatus().equals("正常")){
            member.setStatus("冻结");
        }else{
            member.setStatus("正常");
        }
        memberDAO.update(member);
    }

}
