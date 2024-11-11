package com.it.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.dao.MemberDAO;
import com.it.dao.ProductDAO;
import com.it.dao.RecordDAO;
import com.it.dao.SysuserDAO;
import com.it.entity.Member;
import com.it.entity.Record;
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
public class RecordController extends BaseController {

	@Resource
    RecordDAO recordDAO;
    @Resource
    ProductDAO productDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    SysuserDAO sysuserDAO;


    /**
     * 添加浏览记录
     * @param record
     * @param request
     * @return
     */
	@ResponseBody
	@RequestMapping("recordAdd1")
	public HashMap<String,Object> recordAdd1(Record record , HttpServletRequest request) {
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        HashMap<String,Object> res = new HashMap<String,Object>();
        HashMap ccc = new HashMap();
        ccc.put("memberid", sessionmember.getId());
        ccc.put("productid", record.getProductid());
        ccc.put("flag","浏览");
        List<Record> records = recordDAO.selectAll(ccc);
        if(records.size()==0){
            record.setMemberid(sessionmember.getId());
            record.setCs(1);
            record.setFlag("浏览");
            recordDAO.add(record);
        }
		return res;
	}


    /**
     * 添加搜索记录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("recordAdd2")
    public HashMap<String,Object> recordAdd1(HttpServletRequest request) {
        Member sessionmember = (Member) request.getSession().getAttribute("sessionmember");
        String arr = request.getParameter("arr");
        HashMap<String,Object> res = new HashMap<String,Object>();
        if(arr!=null){
            String[] ids = arr.split(",");
            for(int i=0;i<ids.length;i++){
                HashMap ccc = new HashMap();
                ccc.put("memberid", sessionmember.getId());
                ccc.put("productid", ids[i]);
                ccc.put("flag", "搜索");
                List<Record> records = recordDAO.selectAll(ccc);
                if (records.size() == 0) {
                    Record record = new Record();
                    record.setMemberid(sessionmember.getId());
                    record.setProductid(Integer.parseInt(ids[i]));
                    record.setCs(2);
                    record.setFlag("搜索");
                    recordDAO.add(record);
                }
            }
        }
        return res;
    }
}
