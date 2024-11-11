package com.it.controller;
import com.it.entity.Chat;
import com.it.entity.Member;
import com.it.entity.Product;
import com.it.dao.ChatDAO;
import com.it.dao.MemberDAO;
import com.it.dao.ProductDAO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class ChatController extends BaseController {

	@Resource
    ChatDAO chatDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;


	//留言列表
	@ResponseBody
	@RequestMapping("admin/chatList")
	public HashMap<String,Object> chatList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
	    String key = request.getParameter("key");
		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap map = new HashMap();
		map.put("key", key);
		List<Chat> objectlist = chatDAO.selectAll(map);
		for(Chat chat:objectlist){
            Member member = memberDAO.findById(chat.getMemberid());
            chat.setMember(member);
        }
		PageHelper.startPage(pageNum, pageSize);
		List<Chat> list = chatDAO.selectAll(map);
        for(Chat chat:list){
            Member member = memberDAO.findById(chat.getMemberid());
            chat.setMember(member);
        }
		PageInfo<Chat> pageInfo = new PageInfo<Chat>(list);
		res.put("pageInfo", pageInfo);
		res.put("list", objectlist);
		return res;
	}


    @ResponseBody
    @RequestMapping("chatLb")
    public HashMap<String,Object> chatLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        String key = request.getParameter("key");
        String productid = request.getParameter("productid");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        map.put("productid", productid);
        List<Chat> objectlist = chatDAO.selectAll(map);
        for(Chat chat:objectlist){
            Member member = memberDAO.findById(chat.getMemberid());
            chat.setMember(member);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Chat> list = chatDAO.selectAll(map);
        for(Chat chat:list){
            Member member = memberDAO.findById(chat.getMemberid());
            chat.setMember(member);
        }
        PageInfo<Chat> pageInfo = new PageInfo<Chat>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);

        return res;
    }
		
	
	//添加留言
	@ResponseBody
	@RequestMapping("chatAdd")
	public HashMap<String,Object> chatAdd(Chat chat ,HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        chat.setMemberid(sessionmember.getId());
        chat.setSavetime(Info.getDateStr());
        chatDAO.add(chat);
		return null;
	}

    /**
     * 留言回复
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("admin/chatEdit")
    public HashMap<String,Object> chatEdit(int id,String hfcontent,HttpServletRequest request) {
        Chat chat = chatDAO.findById(id);
        chat.setHfcontent(hfcontent);
        chatDAO.update(chat);
        return null;
    }
	

	
	//删除留言
	@ResponseBody
	@RequestMapping("admin/chatDel")
	public HashMap<String,Object> chatDel(int id,HttpServletRequest request) {
		chatDAO.delete(id);
		return null;
	}

}
