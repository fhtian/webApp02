package cn.tianfahui.webApp02.dmz.contol;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.tianfahui.webApp02.dmz.utils.CommonUtil;
import cn.tianfahui.webApp02.dmz.utils.UserUtil;

@RequestMapping(value="/userInfo")
@Controller
public class CommonContro {
	
	@RequestMapping(value="/login.do")
	@ResponseBody
	public void loginUser(HttpServletRequest req,HttpServletResponse respon) {
		Map res = new JSONObject();
		String userName = req.getParameter("userName");
		String password01 = req.getParameter("passWord");
		if(userName==null||password01==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名或密码不允许为空");
		}else {
			JSONObject user = new JSONObject();
			user.put("userName", userName);
			user.put("passWord", password01);
			res = UserUtil.checkUsers(user);
		}
		CommonUtil.returnObj(respon,res.toString());
	}
	/**
	 * 新增用户
	 * @param req
	 * @param respon
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月3日 上午6:43:24
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/addUser.do")
	@ResponseBody
	public void addUser(HttpServletRequest req,HttpServletResponse respon) {
		Map res = new JSONObject();
		String userName = req.getParameter("userName");
		String password01 = req.getParameter("passWord01");
		String password02 = req.getParameter("passWord02");
		if(userName==null||password01==null||password02==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名或密码不允许为空");
		}else if(!password01.equals(password02)) {
			res.put("resultCode", "1");
			res.put("msg", "两次密码输入不一致，请重新输入！");
		}else {
			JSONObject user = new JSONObject();
			user.put("userName", userName);
			user.put("passWord", password01);
			res = UserUtil.addUsers(user);
		}
		CommonUtil.returnObj(respon,res.toString());
	}
	/**
	 * 注销用户
	 * @param req
	 * @param respon
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月3日 上午6:43:24
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/delUser.do")
	public void delUser(HttpServletRequest req,HttpServletResponse respon) {
		Map res = new JSONObject();
		String userName = req.getParameter("userName");
		String password01 = req.getParameter("passWord01");
		String password02 = req.getParameter("passWord02");
		if(userName==null||password01==null||password02==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名或密码不允许为空");
		}else {
			JSONObject user = new JSONObject();
			user.put("userName", userName);
			user.put("passWord", password01);
			res = UserUtil.delUsers(user);
		}
		CommonUtil.returnObj(respon,res.toString());
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkUserName.do")
	public void checkUserName(HttpServletRequest req,HttpServletResponse respon) throws Exception {
		Map res = new JSONObject();
		String userName = req.getParameter("userName");
		if(userName==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名不允许为空");
		}else if(UserUtil.checkUserName(userName)){
			res.put("resultCode", "1");
			res.put("msg", "用户名重复");
		}else {
			res.put("resultCode", "0");
			res.put("msg", "用户名不重复");
		}
		CommonUtil.returnObj(respon,res.toString());
	}
}
