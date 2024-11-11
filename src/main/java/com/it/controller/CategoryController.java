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

import com.it.entity.Category;
import com.it.entity.Sysuser;
import com.it.dao.CategoryDAO;
import com.it.dao.SysuserDAO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.Info;

import java.util.*;

@Controller
public class CategoryController extends BaseController {

	@Resource
	CategoryDAO categoryDAO;
	
	
	//商品分类列表
	@ResponseBody
	@RequestMapping("admin/categoryList")
	public HashMap<String,Object> categoryList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
		String key = request.getParameter("key");
		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("key", key);
        map.put("fatherid", "0");
		List<Category> objectlist = categoryDAO.selectAll(map);
		for(Category category:objectlist){
            HashMap<String,String> ppp = new HashMap<String,String>();
            ppp.put("fatherid", String.valueOf(category.getId()));
            List<Category> childlist = categoryDAO.selectAll(ppp);
            category.setChildlist(childlist);
        }
		PageHelper.startPage(pageNum, pageSize);
		List<Category> list = categoryDAO.selectAll(map);
        for(Category category:list){
            HashMap<String,String> ppp = new HashMap<String,String>();
            ppp.put("fatherid", String.valueOf(category.getId()));
            List<Category> childlist = categoryDAO.selectAll(ppp);
            category.setChildlist(childlist);
        }
		PageInfo<Category> pageInfo = new PageInfo<Category>(list);
		res.put("pageInfo", pageInfo);
		res.put("list", objectlist);
		return res;
	}
		
	
	//添加商品分类
	@ResponseBody
	@RequestMapping("admin/categoryAdd")
	public HashMap<String,Object> categoryAdd(Category category ,HttpServletRequest request) {
		categoryDAO.add(category);
		return null;
	}
	
	//编辑商品分类
	@ResponseBody
	@RequestMapping("admin/categoryShow")
	public HashMap<String,Object> categoryShow(int id,HttpServletRequest request) {
		HashMap<String,Object> res = new HashMap<String,Object>();
		Category category = categoryDAO.findById(id);
		res.put("category", category);
		return res;
	}
	
	//编辑商品分类
	@ResponseBody
	@RequestMapping("admin/categoryEdit")
	public HashMap<String,Object> categoryEdit(Category category ,HttpServletRequest request) {
		categoryDAO.update(category);
		return null;
	}
	
	//删除商品分类
	@ResponseBody
	@RequestMapping("admin/categoryDel")
	public HashMap<String,Object> categoryDel(int id,HttpServletRequest request) {
        Category category = categoryDAO.findById(id);
        if(category.getFatherid()==0){
            HashMap map = new HashMap();
            map.put("fatherid", id);
            List<Category> objectlist = categoryDAO.selectAll(map);
            for(Category category1:objectlist){
                categoryDAO.delete(category1.getId());
            }
        }
		categoryDAO.delete(id);
		return null;
	}

    //二级联动下拉列表
    @ResponseBody
    @RequestMapping("member/searchchildlist")
    public HashMap<String,Object> searchchildlist(HttpServletRequest request) {
	    String categoryid = request.getParameter("categoryid");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("fatherid", categoryid);
        List<Category> childlist = categoryDAO.selectAll(map);
        res.put("childlist", childlist);
        return res;
    }

    //查找所有的二级类目
    @ResponseBody
    @RequestMapping("childList")
    public HashMap<String,Object> childList(HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap<String,String> map = new HashMap<String,String>();
        List<Category> childlist = categoryDAO.selectchildAll(null);
        res.put("childlist", childlist);
        return res;
    }

}
