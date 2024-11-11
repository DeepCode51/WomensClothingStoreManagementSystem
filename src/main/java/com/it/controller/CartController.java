package com.it.controller;

import javax.annotation.Resource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.it.entity.*;
import com.it.dao.*;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.Info;

import java.util.*;

@Controller
public class CartController extends BaseController {

    @Resource
    CartDAO cartDAO;
    @Resource
    SysuserDAO sysuserDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    UtilController utilController;


    //购物车列表
    @ResponseBody
    @RequestMapping("cartList")
    public HashMap<String, Object> cartList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "1", value = "pageSize") Integer pageSize, HttpServletRequest request) {
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap map = new HashMap();
        map.put("memberid", sessionmember.getId());
        List<Cart> objectlist = cartDAO.selectAll(map);
        double total = 0D;
        for (Cart cart : objectlist) {
            double xjtotal = 0D;
            Product product = productDAO.findById(cart.getProductid());
            double price = product.getPrice();
            if (product.getTprice() > 0) {
                price = product.getTprice();
            }
            total += price * cart.getNum();
            xjtotal = price * cart.getNum();
            cart.setProduct(product);
            cart.setXjtotal(xjtotal);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Cart> list = cartDAO.selectAll(map);
        for (Cart cart : list) {
            double xjtotal = 0D;
            Product product = productDAO.findById(cart.getProductid());
            int kc = utilController.getInventory(cart.getProductid(), request);
            product.setKc(kc);
            double price = product.getPrice();
            if (product.getTprice() > 0) {
                price = product.getTprice();
            }
            xjtotal = price * cart.getNum();
            cart.setProduct(product);
            cart.setXjtotal(xjtotal);
        }
        PageInfo<Cart> pageInfo = new PageInfo<Cart>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);
        res.put("total", total);
        return res;
    }


    //添加购物车
    @ResponseBody
    @RequestMapping("cartAdd")
    public HashMap<String, Object> cartAdd(Cart cart, HttpServletRequest request) {
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap map = new HashMap();
        map.put("memberid", sessionmember.getId());
        map.put("productid", String.valueOf(cart.getProductid()));
        Product product = productDAO.findById(cart.getProductid());
        int kc = utilController.getInventory(product.getId(), request);
        List<Cart> list = cartDAO.selectAll(map);
        if (list.size() == 0) {
            if (cart.getNum() > kc) {
                res.put("data", 400);
            } else {
                cart.setMemberid(sessionmember.getId());
                cartDAO.add(cart);
                res.put("data", 200);
            }
        } else {
            Cart cart1 = list.get(0);
            int num = cart1.getNum() + cart.getNum();
            if (num > kc) {
                res.put("data", 400);
            } else {
                cart1.setNum(num);
                cartDAO.update(cart1);
                res.put("data", 200);
            }
        }
        return res;
    }


    //删除购物车
    @ResponseBody
    @RequestMapping("cartDel")
    public HashMap<String, Object> cartDel(int id, HttpServletRequest request) {
        cartDAO.delete(id);
        return null;
    }

    /**
     * 修改数量
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("editcartnum")
    public HashMap<String, Object> editcartnum(int id,int num, HttpServletRequest request) {
        Cart cart = cartDAO.findById(id);
        cart.setNum(num);
        cartDAO.update(cart);
        return null;
    }

}
