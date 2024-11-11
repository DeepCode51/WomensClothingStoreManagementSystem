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
import com.util.CheckCode;
import com.util.Info;

import java.util.*;

@Controller
public class OrdermsgController extends BaseController {

    @Resource
    OrdermsgDAO ordermsgDAO;
    @Resource
    CartDAO cartDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    SysuserDAO sysuserDAO;
    @Resource
    OrderdetailsDAO orderdetailsDAO;
    @Resource
    UtilController utilController;
    @Resource
    InventoryDAO inventoryDAO;


    //创建订单
    @ResponseBody
    @RequestMapping("ordermsgAdd")
    public HashMap<String, Object> ordermsgAdd(HttpServletRequest request) {
        String addr = request.getParameter("addr");
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap map = new HashMap();
        Member member = (Member) request.getSession().getAttribute("sessionmember");
        //创建订单
        double total = 0D;
        CheckCode cc = new CheckCode();
        String ddno = cc.getCheckCode();
        map.put("memberid", member.getId());
        List<Cart> cartlist = cartDAO.selectAll(map);
        if (cartlist.size() != 0) {
            String productmsg = "<div><ul>";
            for (Cart cart : cartlist) {
                Product product = productDAO.findById(cart.getProductid());
                double price = product.getPrice();
                if (product.getTprice() > 0) {
                    price = product.getTprice();
                }
                total += price * cart.getNum();

                //订单详情
                Orderdetails orderdetails = new Orderdetails();
                orderdetails.setDdno(ddno);
                orderdetails.setProductid(product.getId());
                orderdetails.setNum(cart.getNum());
                orderdetails.setStatus("未完成");
                orderdetails.setMemberid(member.getId());
                orderdetailsDAO.add(orderdetails);
                productmsg += "<li style=padding-bottom:5px><span>" + product.getName() + "</span><span style=margin-left:10px;>" + cart.getNum() + "</span></li>";
                cartDAO.delete(cart.getId());
            }
            productmsg += "</ul></div>";
            Ordermsg ordermsg = new Ordermsg();
            ordermsg.setDdno(ddno);
            ordermsg.setProductmsg(productmsg);
            ordermsg.setMemberid(member.getId());
            ordermsg.setTotal(total);
            ordermsg.setStatus("待付款");
            ordermsg.setAddr(addr);
            ordermsg.setSavetime(Info.getDateStr());
            ordermsgDAO.add(ordermsg);
            res.put("data", 200);
        } else {
            res.put("data", 400);

        }
        return res;
    }


    @ResponseBody
    @RequestMapping("ordermsgAdd1")
    public HashMap<String, Object> ordermsgAdd1(HttpServletRequest request) {
        String addr = request.getParameter("addr");
        String productid = request.getParameter("productid");
        String num = request.getParameter("num");
        String total = request.getParameter("total");

        Product product = productDAO.findById(Integer.parseInt(productid));


        HashMap<String, Object> res = new HashMap<String, Object>();
        Member member = (Member) request.getSession().getAttribute("sessionmember");
        //创建订单
        CheckCode cc = new CheckCode();
        String ddno = cc.getCheckCode();
        String productmsg = "<div><ul>";

        //订单详情
        Orderdetails orderdetails = new Orderdetails();
        orderdetails.setDdno(ddno);
        orderdetails.setProductid(Integer.parseInt(productid));
        orderdetails.setNum(Integer.parseInt(num));
        orderdetails.setStatus("未完成");
        orderdetails.setMemberid(member.getId());
        orderdetailsDAO.add(orderdetails);
        productmsg += "<li style=padding-bottom:5px><span>" + product.getName() + "</span><span style=margin-left:10px;>" + num + "</span></li>";
        productmsg += "</ul></div>";
        Ordermsg ordermsg = new Ordermsg();
        ordermsg.setDdno(ddno);
        ordermsg.setProductmsg(productmsg);
        ordermsg.setMemberid(member.getId());
        ordermsg.setTotal(Double.parseDouble(total));
        ordermsg.setStatus("待付款");
        ordermsg.setAddr(addr);
        ordermsg.setSavetime(Info.getDateStr());
        ordermsgDAO.add(ordermsg);
        res.put("data", 200);
        return res;
    }

    /**
     * 订单列表
     * 购买订单
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("orderList")
    public HashMap<String, Object> orderList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "1", value = "pageSize") Integer pageSize, HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("sessionmember");
        String key = request.getParameter("key");
        String status = request.getParameter("status");

        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        map.put("status", status);

        map.put("memberid", member.getId());
        List<Ordermsg> objectlist = ordermsgDAO.selectAll(map);
        for (Ordermsg ordermsg : objectlist) {
            Member mmm = memberDAO.findById(ordermsg.getMemberid());
            ordermsg.setMember(mmm);
            //详情订单
            HashMap ppp = new HashMap();
            ppp.put("ddno", ordermsg.getDdno());
            List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(ppp);
            for (Orderdetails orderdetails : orderdetailslist) {
                Product product = productDAO.findById(orderdetails.getProductid());
                orderdetails.setProduct(product);
            }
            ordermsg.setOrderdetailslist(orderdetailslist);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Ordermsg> list = ordermsgDAO.selectAll(map);
        for (Ordermsg ordermsg : list) {
            Member mmm = memberDAO.findById(ordermsg.getMemberid());
            ordermsg.setMember(mmm);

            //详情订单
            HashMap ppp = new HashMap();
            ppp.put("ddno", ordermsg.getDdno());
            List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(ppp);
            for (Orderdetails orderdetails : orderdetailslist) {
                Product product = productDAO.findById(orderdetails.getProductid());
                orderdetails.setProduct(product);
            }
            ordermsg.setOrderdetailslist(orderdetailslist);
        }
        PageInfo<Ordermsg> pageInfo = new PageInfo<Ordermsg>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);
        return res;
    }


    /**
     * 销售订单
     */

    @ResponseBody
    @RequestMapping("admin/orderList")
    public HashMap<String, Object> orderList1(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "1", value = "pageSize") Integer pageSize, HttpServletRequest request) {
        String key = request.getParameter("key");
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap map = new HashMap();
        map.put("key", key);
        List<Ordermsg> objectlist = ordermsgDAO.selectAll(map);
        PageHelper.startPage(pageNum, pageSize);
        List<Ordermsg> list = ordermsgDAO.selectAll(map);
        for (Ordermsg ordermsg : list) {
            Member mmm = memberDAO.findById(ordermsg.getMemberid());
            ordermsg.setMember(mmm);
        }
        PageInfo<Ordermsg> pageInfo = new PageInfo<Ordermsg>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);
        return res;
    }

    /**
     * 订单详情
     */

    @ResponseBody
    @RequestMapping("orderdetailsList")
    public HashMap<String, Object> orderdetailsList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "1", value = "pageSize") Integer pageSize, HttpServletRequest request) {
        String ddno = request.getParameter("ddno");
        HashMap<String, Object> res = new HashMap<String, Object>();
        HashMap map = new HashMap();
        map.put("ddno", ddno);
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        for (Orderdetails orderdetails : orderdetailslist) {
            Product product = productDAO.findById(orderdetails.getProductid());
            orderdetails.setProduct(product);
            double xjtotal = product.getPrice() * orderdetails.getNum();
            orderdetails.setXjtotal(xjtotal);
        }
        res.put("list", orderdetailslist);
        return res;
    }

    /**
     * 查找订单
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("member/ordermsgShow")
    public HashMap<String, Object> ordermsgShow(int id, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        Ordermsg ordermsg = ordermsgDAO.findById(id);
        HashMap map = new HashMap();
        map.put("ddno", ordermsg.getDdno());
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        for (Orderdetails orderdetails : orderdetailslist) {
            Product product = productDAO.findById(orderdetails.getProductid());
            orderdetails.setProduct(product);
        }
        ordermsg.setOrderdetailslist(orderdetailslist);
        res.put("ordermsg", ordermsg);
        return res;
    }

    //删除销售订单
    @ResponseBody
    @RequestMapping("admin/orderDel")
    public HashMap<String, Object> orderDel(int id, HttpServletRequest request) {
        Ordermsg ordermsg = ordermsgDAO.findById(id);
        HashMap map = new HashMap();
        map.put("ddno", ordermsg.getDdno());
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        for (Orderdetails orderdetails : orderdetailslist) {
            orderdetailsDAO.delete(orderdetails.getId());
        }
        ordermsgDAO.delete(id);
        return null;
    }


    /**
     * 付款
     */
    @ResponseBody
    @RequestMapping("fukuan")
    public HashMap<String, Object> fukuan(int id, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        boolean flag = true;
        Ordermsg ordermsg = ordermsgDAO.findById(id);
        HashMap map = new HashMap();
        map.put("ddno", ordermsg.getDdno());
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        ordermsg.setStatus("待发货");
        ordermsgDAO.update(ordermsg);
        for (Orderdetails orderdetails : orderdetailslist) {
            orderdetails.setStatus("待发货");
            orderdetailsDAO.update(orderdetails);
        }
        res.put("data", 200);
        return res;
    }

    /**
     * 发货
     */
    @ResponseBody
    @RequestMapping("admin/fahuo")
    public HashMap<String, Object> fahuo(int id, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        boolean flag = true;
        Ordermsg ordermsg = ordermsgDAO.findById(id);
        HashMap map = new HashMap();
        map.put("ddno", ordermsg.getDdno());
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        for (Orderdetails orderdetails : orderdetailslist) {
            int kc = utilController.getInventory(orderdetails.getProductid(), request);
            if (kc < orderdetails.getNum()) {
                flag = false;
                break;
            }
        }
        if (flag == true) {
            String company = request.getParameter("company");
            String wlno = request.getParameter("wlno");
            String wlinfo = "运单号:" + wlno + "    " + company;
            ordermsg.setWlinfo(wlinfo);
            ordermsg.setStatus("待收货");
            ordermsgDAO.update(ordermsg);

            for (Orderdetails orderdetails : orderdetailslist) {
                Inventory inventory = new Inventory();
                inventory.setFlag("out");
                inventory.setProductid(orderdetails.getProductid());
                inventory.setNum(orderdetails.getNum());
                inventoryDAO.add(inventory);
            }
            res.put("data", 200);
        } else {
            res.put("data", 400);
        }
        return res;

    }

    /**
     * 确认收货
     */
    @ResponseBody
    @RequestMapping("shouhuo")
    public void shouhuo(int id, HttpServletRequest request) {
        Ordermsg ordermsg = ordermsgDAO.findById(id);
        HashMap map = new HashMap();
        map.put("ddno", ordermsg.getDdno());
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(map);
        for (Orderdetails orderdetails : orderdetailslist) {
            orderdetails.setStatus("交易完成");
            orderdetailsDAO.update(orderdetails);
        }
        ordermsg.setStatus("交易完成");
        ordermsgDAO.update(ordermsg);

        Sysuser admin = sysuserDAO.findById(16);
        admin.setTotal(admin.getTotal() - ordermsg.getTotal());
        sysuserDAO.updatetotal(admin);

    }

}
