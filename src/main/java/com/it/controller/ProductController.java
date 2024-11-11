package com.it.controller;

import javax.annotation.Resource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.it.dao.*;
import com.it.entity.*;


import com.it.util.UserCFDemo;
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
public class ProductController extends BaseController {

	@Resource
	ProductDAO productDAO;
	@Resource
	CategoryDAO categoryDAO;
    @Resource
    SysuserDAO sysuserDAO;
    @Resource
    UtilController utilController;
    @Resource
    OrderdetailsDAO orderdetailsDAO;

    @Resource
    MemberDAO memberDAO;

    @Resource
    CommentDAO commentDAO;

    @Resource
    RecordDAO recordDAO;

	//商品列表
	@ResponseBody
	@RequestMapping("admin/productList")
	public HashMap<String,Object> productList(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
		Sysuser admin = (Sysuser)request.getSession().getAttribute("admin");
	    String key = request.getParameter("key");
		String key1 = request.getParameter("key1");
        String key2 = request.getParameter("key2");
        String issj = request.getParameter("issj");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        String rank = request.getParameter("rank");
        String flag = request.getParameter("flag");
        String istj = request.getParameter("istj");   //前台首页推荐商品

		HashMap<String,Object> res = new HashMap<String,Object>();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("key", key);
		map.put("categoryid", key1);
        map.put("childid", key2);
        map.put("istj", istj);
        map.put("issj", issj);
        map.put("minprice", minprice);
        map.put("maxprice", maxprice);

        //排序
        map.put("rank", rank);
        map.put("flag", flag);


		List<Product> objectlist = productDAO.selectAll(map);
		for(Product product:objectlist){
			Category fcategory = categoryDAO.findById(product.getCategoryid());
			product.setFcategory(fcategory);

            Category ccategory = categoryDAO.findById(product.getChildid());
            product.setCcategory(ccategory);

            int kc = utilController.getInventory(product.getId(), request);
            product.setKc(kc);

			

 		}
		PageHelper.startPage(pageNum, pageSize);
		List<Product> list = productDAO.selectAll(map);
		for(Product product:list){
			Category fcategory = categoryDAO.findById(product.getCategoryid());
            product.setFcategory(fcategory);


            Category ccategory = categoryDAO.findById(product.getChildid());
            product.setCcategory(ccategory);


            int kc = utilController.getInventory(product.getId(), request);
            product.setKc(kc);
			
 		}
		PageInfo<Product> pageInfo = new PageInfo<Product>(list);
		res.put("pageInfo", pageInfo);
		res.put("list", objectlist);
		return res;
	}



    //前台商品列表
    @ResponseBody
    @RequestMapping("productLb")
    public HashMap<String,Object> productLb(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,HttpServletRequest request){
        String key = request.getParameter("key");
        String key1 = request.getParameter("key1");
        String key2 = request.getParameter("key2");
        String issj = request.getParameter("issj");
        String istj = request.getParameter("istj");   //前台首页推荐商品
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("key", key);
        map.put("categoryid", key1);
        map.put("childid", key2);
        map.put("istj", istj);
        map.put("issj", issj);
        //排序
        List<Product> objectlist = productDAO.selectAll(map);
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productDAO.selectAll(map);
        PageInfo<Product> pageInfo = new PageInfo<Product>(list);
        res.put("pageInfo", pageInfo);
        res.put("list", objectlist);
        return res;
    }



	
	//商家商品
	@ResponseBody
	@RequestMapping("admin/productAdd")
	public HashMap<String,Object> productAdd(Product product ,HttpServletRequest request) {
        product.setIssj("yes");
        product.setIstj("no");
		productDAO.add(product);
		return null;
	}
	
	//编辑商品
	@ResponseBody
	@RequestMapping("admin/productShow")
	public HashMap<String,Object> productShow(int id,HttpServletRequest request) {
		HashMap<String,Object> res = new HashMap<String,Object>();
		Product product = productDAO.findById(id);
		Category fcategory = categoryDAO.findById(product.getCategoryid());
        Category ccategory = categoryDAO.findById(product.getChildid());
        product.setFcategory(fcategory);
        product.setCcategory(ccategory);
        int kc = utilController.getInventory(product.getId(), request);
        product.setKc(kc);

        //销量
        int salenum = 0;
        HashMap ccc = new HashMap();
        ccc.put("productid",id );
        ccc.put("status", "交易完成");
        List<Orderdetails> orderdetailslist = orderdetailsDAO.selectAll(ccc);
        for(Orderdetails orderdetails:orderdetailslist){
            salenum +=orderdetails.getNum();
        }
        product.setSalenum(salenum);
		res.put("product", product);

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("childid", String.valueOf(product.getChildid()));
		List<Product> lovelist = productDAO.selectAll(map);
		res.put("lovelist", lovelist);
		return res;
	}
	
	//编辑商品
	@ResponseBody
	@RequestMapping("admin/productEdit")
	public HashMap<String,Object> productEdit(Product product ,HttpServletRequest request) {
		productDAO.update(product);
		return null;
	}
	
	//删除商品
	@ResponseBody
	@RequestMapping("admin/productDel")
	public HashMap<String,Object> productDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		productDAO.delete(Integer.parseInt(id));
		return null;
	}

    /**
     * 商品上下架
     */

    @ResponseBody
    @RequestMapping("admin/issjedit")
    public void issjedit(int id,HttpServletRequest request) {
        Product product =  productDAO.findById(id);
        if(product.getIssj().equals("yes")){
            product.setIssj("no");
        }else{
            product.setIssj("yes");
        }
        productDAO.update(product);
    }

    /**
     * 管理员推荐
     */

    @ResponseBody
    @RequestMapping("admin/istjedit")
    public void istjedit(int id,HttpServletRequest request) {
        Product product =  productDAO.findById(id);
        if(product.getIstj().equals("yes")){
            product.setIstj("no");
        }else{
            product.setIstj("yes");
        }
        productDAO.update(product);
    }

    /**
     * 设置特价
     * @param id
     * @param tprice
     * @param request
     */
    @ResponseBody
    @RequestMapping("admin/tpriceEdit")
    public void tpriceEdit(int id,double tprice,HttpServletRequest request) {
        Product product =  productDAO.findById(id);
        product.setTprice(tprice);
        productDAO.update(product);
    }


    @ResponseBody
    @RequestMapping("hotSale")
    public List<Product> hotSale() {
        List<Product> res = new ArrayList<Product>();
        HashMap ccc = new HashMap();
        Map<String, Long> entrymap = new HashMap<String, Long>();
        List<Product> productlist =productDAO.selectAll(null);
        for(Product product:productlist){
            ccc.put("productid",product.getId());
            List<Orderdetails> detailslist = orderdetailsDAO.selectAll(ccc);
            int num = 0;
            for(Orderdetails orderdetails:detailslist){
                num+=orderdetails.getNum();
            }
            entrymap.put(String.valueOf(product.getId()), (long) num);
        }
        List<Map.Entry<String, Long>> reslist = new ArrayList<Map.Entry<String, Long>>(entrymap.entrySet());
        //将map.entrySet()转换成list
        Collections.sort(reslist, new Comparator<Map.Entry<String, Long>>() {
            //降序排序
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for(int i=0;i<reslist.size();i++){
            String [] arr = reslist.get(i).toString().split("=");
            Product product2 = productDAO.findById(Integer.parseInt(arr[0]));
            res.add(product2);
        }
        return res;
    }




    /**
     * 
     */

     // https://blog.csdn.net/u012995888/article/details/79077681
    @ResponseBody
    @RequestMapping("productLove")
    public HashMap<String,Object> productLove(HttpServletRequest request){
        HashMap<String,Object> res = new HashMap<String,Object>();
        Member member = (Member)request.getSession().getAttribute("sessionmember");
        //会员集合
        List<Member> ulist = memberDAO.selectAll(null);
        String[] uarray=new String[ulist.size()];
        for(int i=0;i<ulist.size();i++){
            uarray[i]=String.valueOf(ulist.get(i).getId());
        }

        //商品集合
        HashMap map = new HashMap();
        List<Product> products = productDAO.selectAll(null);
        String[] dyarray=new String[products.size()];
        for(int i=0;i<products.size();i++){
            dyarray[i]=String.valueOf(products.get(i).getId());
        }

        //评分
        int [][] arr2 = new int[ulist.size()][];
        int count = 0;
        //System.out.println("arr2.length=="+arr2.length);
        for(int i=0;i<arr2.length;i++){
            Member mb = ulist.get(i);
            //System.out.println("userobj"+i+"    "+userobj);
            //创建一维数组
            int[] tmpArr = new int[products.size()];
            //创建二维数组
            for(int j=0;j < tmpArr.length;j++){
                Product product = products.get(j);
                map.put("productid", product.getId());
                map.put("memberid", mb.getId());
                List<Record> records = recordDAO.selectAll(map);

                if(records.size()>0){
                    int pf = 0;
                    for(Record record : records){
                        pf += record.getCs();
                    }
                    tmpArr[j] = pf;
                }

                //System.out.println("dyobj"+j+"    "+dyobj);
                //tmpArr[j] = (++count);
            }
            arr2[i] = tmpArr;
        }
        for(int m=0;m<arr2.length;m++){
            for(int n=0;n<arr2[m].length;n++){
                System.out.print(arr2[m][n]+"  ");
            }
            System.out.println();
        }

        UserCFDemo u = new UserCFDemo();
        u.users = uarray;
        u.movies = dyarray;
        u.allUserMovieStarList = arr2;
        u.membernum = ulist.size();
        u.mvnum = products.size();
        List<String> rtnlist = u.mvlist(String.valueOf(member.getId()));
        String aa = "";


        List<Product> tjproductlist = new ArrayList<Product>();


        for(int m = 0;m< rtnlist.size();m++){
            Product p = productDAO.findById(Integer.valueOf(rtnlist.get(m)));
            tjproductlist.add(p);
            System.out.println("推荐的商品==="+p.getName());
        }
        res.put("lovelist", tjproductlist);

        return res;
    }


}
