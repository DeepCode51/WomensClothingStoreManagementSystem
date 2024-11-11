package com.it.controller;



import com.it.dao.OrderdetailsDAO;
import com.it.dao.OrdermsgDAO;
import com.it.dao.ProductDAO;
import com.it.entity.Orderdetails;
import com.it.entity.Ordermsg;
import com.it.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class StatisticsController {

    @Resource
    OrdermsgDAO ordermsgDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    OrderdetailsDAO orderdetailsDAO;


    @ResponseBody
    @RequestMapping("admin/tjMoney")
    public HashMap<String,Object> tjMoney(HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        String key = request.getParameter("key");
        HashMap map = new HashMap();
        map.put("status", "交易完成");
        List<Ordermsg> olist = ordermsgDAO.selectTjmoney(map);
        //ArrayList nslist = new ArrayList();
        ArrayList nlist = new ArrayList();
        ArrayList slist = new ArrayList();
        for(Ordermsg ordermsg:olist){
            nlist.add(ordermsg.getSavetime());
            slist.add(ordermsg.getTotal());
        }
        res.put("nlist", nlist);
        res.put("slist", slist);
        return res;
    }


    @ResponseBody
    @RequestMapping("admin/tjSalenum")
    public HashMap<String,Object> tjSalenum(HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        List dataAxis = new ArrayList();
        List dataval = new ArrayList();
        List<Product> productlist = productDAO.selectAll(null);
        for(Product product:productlist){
            dataAxis.add(product.getName());
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("productid", String.valueOf(product.getId()));
            map.put("status","交易完成");
            List<Orderdetails> list = orderdetailsDAO.selectAll(map);
            int num  = 0;
            for(Orderdetails orderdetails:list){
                num+= orderdetails.getNum();
            }
            dataval.add(num);

        }
        res.put("dataAxis", dataAxis);
        res.put("dataval", dataval);
        return res;
    }
}
