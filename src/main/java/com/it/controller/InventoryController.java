package com.it.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.dao.InventoryDAO;
import com.it.dao.MemberDAO;
import com.it.dao.ProductDAO;
import com.it.dao.SysuserDAO;
import com.it.entity.Inventory;
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
public class InventoryController extends BaseController {
	@Resource
    InventoryDAO inventoryDAO;
	//添加库存
	@ResponseBody
	@RequestMapping("admin/inventoryAdd")
	public HashMap<String,Object> inventoryAdd(Inventory inventory , HttpServletRequest request) {
        HashMap<String,Object> res = new HashMap<String,Object>();
        inventoryDAO.add(inventory);
		return null;
	}

}
