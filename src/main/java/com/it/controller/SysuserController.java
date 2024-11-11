package com.it.controller;

import javax.annotation.Resource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;






import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.it.entity.Sysuser;
import com.it.dao.SysuserDAO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.Info;

import java.util.*;

@Controller
public class SysuserController extends BaseController {

	@Resource
	SysuserDAO sysuserDAO;
	
	//登录
	@ResponseBody
	@RequestMapping("admin/Login")
	public HashMap<String, Object> Login(Sysuser sysuser ,HttpServletRequest request) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", sysuser.getUsername());
		map.put("userpwd", sysuser.getUserpwd());
		    List<Sysuser> list = sysuserDAO.selectAll(map);
		    if(list.size()==0){
		    	res.put("data", 400);
		    }else{
		    	if(list.get(0).getShstatus().equals("已拒绝")){
		    		res.put("data", 300);
		    	}else{
		    		res.put("data", 200);
			    	request.getSession().setAttribute("admin", list.get(0));
		    	}
		    }
		return res;
	}
	
	
	//是否登录
	@ResponseBody
	@RequestMapping("admin/checkadmin")
	public HashMap<String, Object> checkadmin(HttpServletRequest request) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		Sysuser admin = (Sysuser)request.getSession().getAttribute("admin");
		if(admin!=null){
			res.put("data", 200);
			Sysuser sysuser = sysuserDAO.findById(admin.getId());
			res.put("admin", sysuser);
		}else{
			res.put("data", 400);
		}
		return res;
	}
	
	//退出
	@ResponseBody
	@RequestMapping("admin/logout")
	public HashMap<String, Object> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("admin");
		return null;
	}
	
	//个人信息
	@ResponseBody
	@RequestMapping("admin/adminShow")
	public HashMap<String, Object> adminShow(HttpServletRequest request) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		Sysuser admin = (Sysuser)request.getSession().getAttribute("admin");
		Sysuser user = sysuserDAO.findById(admin.getId());
		res.put("user", user);
		return res;
	}
	
	
	//修改密码
	@ResponseBody
	@RequestMapping("admin/passwordedit")
	public HashMap<String,Object> passwordedit(HttpServletRequest request) {
		HashMap<String,Object> res = new HashMap<String,Object>();
		String userpwd = request.getParameter("userpwd");
		String nuserpassword = request.getParameter("nuserpassword");
		System.out.println("");
		Sysuser admin = (Sysuser)request.getSession().getAttribute("admin");
		Sysuser user = sysuserDAO.findById(admin.getId());
		if(userpwd.equals(user.getUserpwd())){
			res.put("data", 200);
			user.setUserpwd(nuserpassword);
			sysuserDAO.update(user);
		}else{
			res.put("data", 400);
		}
		return res;
	}
		
	
	
	
	
	//验证登录名唯一性
	@ResponseBody
	@RequestMapping("admin/checkUsername")
	public HashMap<String,Object> checkUsername(HttpServletRequest request,HttpServletResponse response) {
		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap<String, String> map = new HashMap<String, String>();
		String username = request.getParameter("username");
		map.put("username", username);
		List<Sysuser> list = sysuserDAO.selectAll(map);
		if(list.size()==0){
			res.put("data",200);
		}else{
			res.put("data",400);
		}
		return res;
	}
	
	
	/**
	 * 商家列表
	 * @param pageNum
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("admin/userList")
	public HashMap<String,Object> userList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
		String key = request.getParameter("key");
		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("key", key);
        map.put("usertype", "商家");
		List<Sysuser> objectlist = sysuserDAO.selectAll(map);
		PageHelper.startPage(pageNum, pageSize);
		List<Sysuser> list = sysuserDAO.selectAll(map);
		PageInfo<Sysuser> pageInfo = new PageInfo<Sysuser>(list);
		res.put("pageInfo", pageInfo);
		res.put("list", objectlist);
		return res;
	}

    /**
     * 注册商家
     * @param sysuser
     * @param request
     * @param response
     * @return
     */

    @ResponseBody
    @RequestMapping("shopreg")
    public HashMap<String,Object> shopreg(Sysuser sysuser,HttpServletRequest request,HttpServletResponse response) {
        sysuser.setUsertype("商家");
        sysuser.setDelstatus("0");
        sysuserDAO.add(sysuser);
        return null;
    }

    /**
     * 审核商家
     * @param id
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("admin/editstatus")
    public HashMap<String,Object> editstatus(int id,HttpServletRequest request,HttpServletResponse response) {
        String flag = request.getParameter("flag");
        Sysuser shop = sysuserDAO.findById(id);
        if(flag.equals("tg")){
            shop.setShstatus("通过审核");
        }else{
            shop.setShstatus("已拒绝");
        }
        sysuserDAO.update(shop);
        return null;
    }

	//删除商家
	@ResponseBody
	@RequestMapping("admin/shopDel")
	public HashMap<String,Object> userDel(int id,HttpServletRequest request,HttpServletResponse response) {
		sysuserDAO.delete(id);
		return null;
	}
	
	
	@RequestMapping("admin/updateUpass")
	public String updateUpass(HttpServletRequest request) {
        String oldupass = request.getParameter("oldupass");
        String userpass = request.getParameter("userpass");
        Sysuser s  = (Sysuser)request.getSession().getAttribute("admin");
        Sysuser sysuser = sysuserDAO.findById(s.getId());
        if(sysuser.getUserpwd().equals(oldupass)){
        	sysuser.setUserpwd(userpass);
        	request.setAttribute("suc", "修改成功");
        }else{
        	request.setAttribute("suc", "原密码错误");
        }
		return "admin/updateupass";
	}
	
	//查找登录用户到个人信息页面
	@RequestMapping("admin/showAdmin")
	public String showAdmin(HttpServletRequest request) {
		String msg = request.getParameter("msg");
        Sysuser s  = (Sysuser)request.getSession().getAttribute("admin");
        Sysuser sysuser = sysuserDAO.findById(s.getId());
        request.setAttribute("sysuser", sysuser);
        if(msg!=null){
        request.setAttribute("suc", "编辑成功");	
        }
		return "admin/grinfo";
	}
	
	//更新个人信息

    @ResponseBody
    @RequestMapping("admin/userEdit")
    public void userEdit(Sysuser sysuser, HttpServletRequest request) {
        sysuserDAO.update(sysuser);
    }
	
	

}
