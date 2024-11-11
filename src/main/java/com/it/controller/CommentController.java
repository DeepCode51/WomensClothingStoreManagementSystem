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
import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;

@Controller
public class CommentController extends BaseController {

	@Resource
    CommentDAO commentDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    SysuserDAO sysuserDAO;
    @Resource
    OrderdetailsDAO orderdetailsDAO;


	//评价列表
	@ResponseBody
    @RequestMapping("admin/commentList")
    public HashMap<String,Object> commentList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        String key = request.getParameter("key");
        String productid = request.getParameter("productid");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        map.put("productid", productid);
        List<Comment> objectlist = commentDAO.selectAll(map);
        for(Comment comment:objectlist){
            Member member = memberDAO.findById(comment.getMemberid());
            Product product = productDAO.findById(comment.getProductid());
            comment.setProduct(product);
            comment.setMember(member);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentDAO.selectAll(map);
        for(Comment comment:list){
            Member member = memberDAO.findById(comment.getMemberid());
            Product product = productDAO.findById(comment.getProductid());
            comment.setProduct(product);
            comment.setMember(member);
        }
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);

        return res;
    }



	//添加评价
	@ResponseBody
	@RequestMapping("commentAdd")
	public HashMap<String,Object> commentAdd(Comment comment ,HttpServletRequest request) {
        Member member = (Member)request.getSession().getAttribute("sessionmember");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("memberid", member.getId());
        map.put("productid", comment.getProductid());
        map.put("status", "交易完成");
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        if(orderdetailslist.size()!=0){
            List<Comment> plist = commentDAO.selectAll(map);
            if(plist.size()==0){
                Product product  = productDAO.findById(comment.getProductid());
                comment.setMemberid(member.getId());
                comment.setSavetime(Info.getDateStr());
                commentDAO.add(comment);
                res.put("data", 200);//成功
            }else{
                res.put("data", 400);//已评
            }
        }else{
            res.put("data", 500);//没有购买记录
        }
		return res;
	}

    @ResponseBody
    @RequestMapping("admin/commentShow")
    public HashMap<String,Object> commentShow(int id,HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        Comment comment = commentDAO.findById(id);
        res.put("comment", comment);
        return res;
    }


    /**
     * 评价回复
     * @param id
     * @param hfcontent
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("admin/commentEdit")
    public HashMap<String,Object> commentEdit(int id,String hfcontent,HttpServletRequest request) {
	    Comment comment = commentDAO.findById(id);
	    comment.setHfcontent(hfcontent);
        commentDAO.update(comment);
        return null;
    }
	

	
	//删除评价
	@ResponseBody
	@RequestMapping("admin/commentDel")
	public HashMap<String,Object> commentDel(int id,HttpServletRequest request) {
		commentDAO.delete(id);
		return null;
	}

}
