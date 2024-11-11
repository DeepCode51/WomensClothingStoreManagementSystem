package com.it.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.dao.*;
import com.it.entity.*;
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
public class ApplyController extends BaseController {

	@Resource
    ApplyDAO applyDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    SysuserDAO sysuserDAO;
    @Resource
    OrderdetailsDAO orderdetailsDAO;


	//售后列表
	@ResponseBody
	@RequestMapping("admin/applyList")
	public HashMap<String,Object> applyList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
	    Sysuser seller = (Sysuser)request.getSession().getAttribute("admin");
	    String key = request.getParameter("key");
		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap map = new HashMap();
		map.put("key", key);
        map.put("sellerid", seller.getId());
		List<Apply> objectlist = applyDAO.selectAll(map);
		for(Apply apply:objectlist){
            Member member = memberDAO.findById(apply.getMemberid());
            apply.setMember(member);

            Product product = productDAO.findById(apply.getProductid());
            apply.setProduct(product);
        }
		PageHelper.startPage(pageNum, pageSize);
		List<Apply> list = applyDAO.selectAll(map);
        for(Apply apply:list){
            Member member = memberDAO.findById(apply.getMemberid());
            apply.setMember(member);

            Product product = productDAO.findById(apply.getProductid());
            apply.setProduct(product);
        }
		PageInfo<Apply> pageInfo = new PageInfo<Apply>(list);
		res.put("pageInfo", pageInfo);
		res.put("list", objectlist);

		return res;
	}


    @ResponseBody
    @RequestMapping("applyLb")
    public HashMap<String,Object> applyLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
	    String key = request.getParameter("key");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        map.put("memberid", sessionmember.getId());
        List<Apply> objectlist = applyDAO.selectAll(map);
        for(Apply apply:objectlist){
            Product product = productDAO.findById(apply.getProductid());
            apply.setProduct(product);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Apply> list = applyDAO.selectAll(map);
        for(Apply apply:list){
            Product product = productDAO.findById(apply.getProductid());
            apply.setProduct(product);
        }
        PageInfo<Apply> pageInfo = new PageInfo<Apply>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);
        return res;
    }
		
	
	//添加售后
	@ResponseBody
	@RequestMapping("applyAdd")
	public HashMap<String,Object> applyAdd(HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        String ordermsgid = request.getParameter("ordermsgid");
        String flag = request.getParameter("flag");
        String content = request.getParameter("content");
        Orderdetails orderdetails = orderdetailsDAO.findById(Integer.parseInt(ordermsgid));


        Apply apply = new Apply();
        apply.setMemberid(sessionmember.getId());
        apply.setDdno(orderdetails.getDdno());
        apply.setProductid(orderdetails.getProductid());
        apply.setMemberid(sessionmember.getId());
        apply.setFlag(flag);
        apply.setContent(content);
        apply.setSavetime(Info.getDateStr());
        apply.setStatus("未处理");
        applyDAO.add(apply);
		return null;
	}


    @ResponseBody
    @RequestMapping("admin/applyEdit")
    public HashMap<String,Object> applyEdit(int id,HttpServletRequest request) {
	    Apply apply = applyDAO.findById(id);
	    apply.setStatus("已处理");
	    applyDAO.update(apply);
        return null;
    }
	

	
	//删除售后
	@ResponseBody
	@RequestMapping("admin/applyDel")
	public HashMap<String,Object> applyDel(int id,HttpServletRequest request) {
		applyDAO.delete(id);
		return null;
	}

}
