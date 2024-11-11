package com.it.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.it.dao.InventoryDAO;
import com.it.entity.Inventory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 上传图片公共接口等工具类
 * @author Administrator
 *
 */
@Controller  
public class UtilController  extends BaseController {
	//数据库切换
	//DataSourceContextHolder.setCustomerType("dataSourceThree");
	//切换后关闭
	//DataSourceContextHolder.clearCustomerType();

    @Resource
    InventoryDAO inventoryDAO;

	//上传图片
	@ResponseBody
	@RequestMapping("admin/uploadImg")
	public Map uploadImg(MultipartFile file,HttpServletRequest request) {
		String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        try{
            if(file!=null){
                String originalName = file.getOriginalFilename();
                prefix=originalName.substring(originalName.lastIndexOf(".")+1);
                Date date = new Date();
                String uuid = UUID.randomUUID()+"";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                //String filepath = "D:\\ebooksysimages\\" + dateStr+"\\"+uuid+"." + prefix;
                String filepath = request.getRealPath("/upload/")+"/"+uuid+"." + prefix;

                File files=new File(filepath);
                //打印查看上传路径
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
                Map<String,Object> map2=new HashMap<>();
                Map<String,Object> map=new HashMap<>();
                map.put("code",0);
                map.put("msg","");
                map.put("data",map2);
                //map2.put("src","/images/"+ dateStr+"/"+uuid+"." + prefix);
                map2.put("src",uuid+"." + prefix);
                return map;
            }

        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("msg","");
        return map;
	}


	public int getInventory(int productid,HttpServletRequest request){
	    int kc = 0;

	    //入库总数
        int intotal = 0;
	    HashMap inmap = new HashMap();
	    inmap.put("productid", productid);
        inmap.put("flag", "in");
	    List<Inventory> inlist = inventoryDAO.selectAll(inmap);
	    for(Inventory inventory:inlist){
	        intotal += inventory.getNum();
        }
        //入库总数
        int outtotal = 0;
        HashMap outmap = new HashMap();
        outmap.put("productid", productid);
        outmap.put("flag", "out");
        List<Inventory> outlist = inventoryDAO.selectAll(outmap);
        for(Inventory inventory:outlist){
            outtotal += inventory.getNum();
        }
        kc = intotal-outtotal;
	    return kc;
    }
}
