package cn.tianfahui.webApp02.dmz.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class UserUtil {
	public static void main(String[] args) throws Exception {
		JSONObject user = new JSONObject();
		user.put("userName", "aaa");
		user.put("passWord", "aaa");
		System.out.println(getUsers());
		System.out.println(addUsers(user));
		System.out.println(getUsers());
	
	}
	/**
	 * 删除注销用户
	 * @param user
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月3日 下午9:17:16
	 */
	public static Map delUsers(JSONObject user) {
		JSONObject res = new JSONObject();
		if(user.get("userName")==null||user.get("passWord")==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名或密码不允许为空");
			return res;
		}
		try {
			JSONArray users =  getUsers();
			Object temp = null;
			for(Object obj:users) {
				//如果有相同的账户就不允许注册
				if(((JSONObject)obj).get("userName").equals(user.get("userName"))&&
						((JSONObject)obj).get("passWord").equals(user.get("passWord"))) {
					temp = obj;
				}
			}
			if(temp==null) {
				res.put("resultCode", "1");
				res.put("msg", "注销用户异常，用户不存在");
				return res;
			}else {
				users.remove(temp);
			}
			
			reWriteFile(users.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			res.put("resultCode", "5");
			res.put("msg", "注销用户异常");
			return res;
		}
		res.put("resultCode", "0");
		res.put("msg", "注销用户成功");
		return res;
	}
	/**
	 * 校验用户用于登录
	 * @param user
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月8日 下午3:58:48
	 */
	public static Map checkUsers(JSONObject user) {
		JSONObject res = new JSONObject();
		if(user.get("userName")==null||user.get("passWord")==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名或密码不允许为空");
			return res;
		}else {
			try {
				JSONArray users = getUsers();
				Object temp = null;
				for(Object obj:users) {
					//如果有相同的账户就不允许注册
					if(((JSONObject)obj).get("userName").equals(user.get("userName"))&&
							((JSONObject)obj).get("passWord").equals(user.get("passWord"))) {
						res.put("resultCode", "0");
						res.put("msg", "登陆成功");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				res.put("resultCode", "2");
				res.put("msg", "异常");
			}
		}
		return res;
	}
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月2日 下午10:10:03
	 */
	public static Map addUsers(JSONObject user) {
		JSONObject res = new JSONObject();
		if(user.get("userName")==null||user.get("passWord")==null) {
			res.put("resultCode", "1");
			res.put("msg", "用户名或密码不允许为空");
			return res;
		}
		try {
			
			if(checkUserName((String) user.get("userName"))) {
				res.put("resultCode", "1");
				res.put("msg", "用户名不允许为重复");
			}else {
				String basePath = new File("").getCanonicalPath();
				String path = basePath + Constance.USER_PATH;
				File file = new File(path);
				if (file.isDirectory()) {
					String[] filelist = file.list();
					String fileName = path + "\\" + filelist[filelist.length-1];
					filewriter(fileName,user.toJSONString());
				}
				res.put("resultCode", "0");
				res.put("msg", "新增用户成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.put("resultCode", "5");
			res.put("msg", "新增用户异常");
			return res;
		}
		return res;
	}
	/**
	 * 校验用户名是否存在
	 * @param userName
	 * @return
	 * @author tianfahui
	 * @throws Exception 
	 * @date: 2018年7月7日 下午3:27:33
	 */
	public static boolean checkUserName(String userName) throws Exception {
		JSONArray users =  getUsers();
		for(Object obj:users) {
			//如果有相同的账户就不允许注册
			if(((JSONObject)obj).get("userName").equals(userName)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 重新写入用户信息
	 * @param str
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月3日 下午9:37:33
	 */
	public static boolean reWriteFile(String str) {
		String basePath;
		try {
			basePath = new File("").getCanonicalPath();
			String path = basePath + Constance.USER_PATH;
			JSONArray res = new JSONArray();
			File file = new File(path);
			String fileName = "";
			if (file.isDirectory()) {
				String[] filelist = file.list();
				String fileStr = "";
				for (int i = 0; i < filelist.length; i++) {
					fileName = path + "\\" + filelist[i];
				}
			}
			clearInfoForFile(fileName);
			filewriter(fileName,str.substring(1, str.length()-1));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	 /**
	  * 清空文件内容
	  * @param fileName
	  * @author tianfahui
	  * @date: 2018年7月3日 下午9:40:07
	  */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * 获取所有的用户信息
	 * @return
	 * @author tianfahui
	 * @throws Exception
	 * @date: 2018年7月2日 下午9:14:09
	 */
	public static JSONArray getUsers() throws Exception {
		String basePath = new File("").getCanonicalPath();
		String path = basePath + Constance.USER_PATH;
		JSONArray res = new JSONArray();
		File file = new File(path);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			String fileStr = "";
			for (int i = 0; i < filelist.length; i++) {
				String fileName = path + "\\" + filelist[i];
				fileStr += "[" + readToString(fileName) + "]";
			}
			res = JSONArray.parseArray(fileStr);
		}
		return res;
	}
	/**
	 * 读取文件中的内容
	 * @param fileName
	 * @return
	 * @author tianfahui
	 * @date: 2018年7月2日 下午10:14:36
	 */
	public static String readToString(String fileName) {
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 文件追加内容
	 * @param file
	 * @param conent
	 * @author tianfahui
	 * @date: 2018年7月2日 下午10:04:06
	 */
	public static void filewriter(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
