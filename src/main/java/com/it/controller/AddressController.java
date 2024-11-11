package com.it.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.dao.AddressDAO;
import com.it.dao.MemberDAO;
import com.it.dao.ProductDAO;
import com.it.dao.SysuserDAO;
import com.it.entity.Address;
import com.it.entity.Member;
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
public class AddressController extends BaseController {

	@Resource
    AddressDAO addressDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    SysuserDAO sysuserDAO;

	//地址列表
	@ResponseBody
    @RequestMapping("addressList")
    public HashMap<String,Object> addressList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute("sessionmember");
        String key = request.getParameter("key");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        map.put("memberid", member.getId());
        List<Address> objectlist = addressDAO.selectAll(map);
        PageHelper.startPage(pageNum, pageSize);
        List<Address> list = addressDAO.selectAll(map);
        PageInfo<Address> pageInfo = new PageInfo<Address>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);

        return res;
    }

	
	//添加地址
	@ResponseBody
	@RequestMapping("addressAdd")
	public HashMap<String,Object> addressAdd(Address address ,HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        Member member = (Member) request.getSession().getAttribute("sessionmember");
        address.setMemberid(member.getId());
        address.setIsmr("否");
        addressDAO.add(address);
		return null;
	}

    @ResponseBody
    @RequestMapping("addressShow")
    public HashMap<String,Object> addressShow(int id,HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        Address address = addressDAO.findById(id);
        res.put("address", address);
        return res;
    }

    @ResponseBody
    @RequestMapping("addressEdit")
    public HashMap<String,Object> addressEdit(int id,HttpServletRequest request) {
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        HashMap map = new HashMap();
        map.put("memberid", sessionmember.getId());
        List<Address> addresses = addressDAO.selectAll(map);
        for(Address address:addresses){
            address.setIsmr("否");
            addressDAO.update(address);
        }
        Address address  = addressDAO.findById(id);
        address.setIsmr("是");
        addressDAO.update(address);
        return null;
    }
	

	
	//删除地址
	@ResponseBody
	@RequestMapping("addressDel")
	public HashMap<String,Object> addressDel(int id,HttpServletRequest request) {
		addressDAO.delete(id);
		return null;
	}

}
