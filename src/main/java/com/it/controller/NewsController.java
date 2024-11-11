package com.it.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.dao.MemberDAO;
import com.it.dao.NewsDAO;
import com.it.dao.ProductDAO;
import com.it.dao.SysuserDAO;
import com.it.entity.Member;
import com.it.entity.News;
import com.it.entity.Sysuser;
import com.util.Info;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class NewsController extends BaseController {

	@Resource
    NewsDAO newsDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    SysuserDAO sysuserDAO;

	//资讯列表
	@ResponseBody
    @RequestMapping("admin/newsList")
    public HashMap<String,Object> newsList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        Sysuser admin = (Sysuser)request.getSession().getAttribute("admin");
        String key = request.getParameter("key");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        List<News> objectlist = newsDAO.selectAll(map);
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsDAO.selectAll(map);
        PageInfo<News> pageInfo = new PageInfo<News>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);

        return res;
    }


    @ResponseBody
    @RequestMapping("newsLb")
    public HashMap<String,Object> newsLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
	     String key = request.getParameter("key");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        List<News> objectlist = newsDAO.selectAll(map);
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsDAO.selectAll(map);
        PageInfo<News> pageInfo = new PageInfo<News>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);
        return res;
    }
		
	
	//添加资讯
	@ResponseBody
	@RequestMapping("admin/newsAdd")
	public HashMap<String,Object> newsAdd(News news ,HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        news.setSavetime(Info.getDateStr());
        newsDAO.add(news);
		return null;
	}

    @ResponseBody
    @RequestMapping("admin/newsShow")
    public HashMap<String,Object> newsShow(int id,HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        News news = newsDAO.findById(id);
        res.put("news", news);
        return res;
    }

    @ResponseBody
    @RequestMapping("admin/newsEdit")
    public HashMap<String,Object> newsEdit(News news,HttpServletRequest request) {
        newsDAO.update(news);
        return null;
    }
	

	
	//删除资讯
	@ResponseBody
	@RequestMapping("admin/newsDel")
	public HashMap<String,Object> newsDel(int id,HttpServletRequest request) {
		newsDAO.delete(id);
		return null;
	}

}
