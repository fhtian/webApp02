package cn.tianfahui.webApp02.dmz.contol;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

@Controller
public class Tcommon {
	
//	@RequestMapping(value="/getMsg.do")
	@RequestMapping(value="/")
	public void getMsg(HttpServletRequest req,HttpServletResponse respon) {
		JSONObject js = new JSONObject();
		js.put("aa", "aa");
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(10);
		js.put("sessionId", session.getId());
		System.out.println(js);
		resultResponse(respon,js);
//		return JSONObject.toJSONString(js);
	}
	
	private void resultResponse(HttpServletResponse response,JSONObject json) {
		try {
			response.setContentType("text/html;charset="
					+ response.getCharacterEncoding());
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
