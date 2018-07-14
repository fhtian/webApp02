package cn.tianfahui.webApp02.dmz.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class CommonUtil {
	public static boolean returnObj(HttpServletResponse respon,String str) {
		try {
			respon.setHeader("Access-Control-Allow-Origin", "*");
			respon.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
			respon.setHeader("Access-Control-Max-Age", "3600");
			respon.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			respon.setContentType("text/html;charset="
					+ respon.getCharacterEncoding());
			respon.getWriter().write(str.toString());
			respon.getWriter().flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
}
