package com.shopping.utils;

import java.io.File;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;


public class InitDB {
//	public String userUrl;
//	public String productUrl;
//	public String shoppingcarUrl;
//	public String recordUrl;
	public InitDB() {
//			//从环境变量中获取应用的访问地址
//			String userUrl = "http://" + System.getenv("USERSVC_SERVICE_HOST") + ":" + System.getenv("USERSVC_SERVICE_PORT") + "/user"; 
//			String productUrl = "http://" + System.getenv("PRODUCTSVC_SERVICE_HOST") + ":" + System.getenv("PRODUCTSVC_SERVICE_PORT") + "/product"; 
//			String shoppingcarUrl = "http://" + System.getenv("SHOPPINGCARSVC_SERVICE_HOST") + ":" + System.getenv("SHOPPINGCARSVC_SERVICE_PORT") + "/shoppingcar"; 
//			String recordUrl = "http://" + System.getenv("RECORDSVC_SERVICE_HOST") + ":" + System.getenv("RECORDSVC_SERVICE_PORT") + "/record"; 
//			if(System.getenv("USERSVC_SERVICE_HOST")==null || System.getenv("USERSVC_SERVICE_HOST").equals("")) {
//				 userUrl = "http://localhost:8080/user"; 
//				 productUrl = "http://localhost:8080/product"; 
//				 shoppingcarUrl = "http://localhost:8080/shoppingcar"; 
//				 recordUrl = "http://localhost:8080/record"; 
//			}
//			
//			
//			System.out.println(userUrl);
//			System.out.println(productUrl);
//			System.out.println(shoppingcarUrl);
//			System.out.println(recordUrl);
//			System.out.println("--------GetEnv-------");	
//			this.userUrl = userUrl;
//			this.productUrl = productUrl;
//			this.shoppingcarUrl = shoppingcarUrl;
//			this.recordUrl = recordUrl;
	}
	
	public static String connmysql() {
		String res;
		String dbHost = System.getenv("MYSQLVMALL_SERVICE_HOST");
		String dbPort = System.getenv("MYSQLVMALL_SERVICE_PORT");
		
		if(System.getenv("MYSQLVMALL_SERVICE_HOST")==null || System.getenv("MYSQLVMALL_SERVICE_HOST").equals("")) {
			 dbHost = "192.168.73.229"; 
			 dbPort = "32441"; 
		}

        Connection conn;
        String url="JDBC:mysql://"+dbHost+":"+dbPort+"/mysql?useUnicode=true&characterEncoding=UTF-8";
		System.out.println("数据库："+url);
        String username="root";
        String password="123456";

       try {
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           conn =DriverManager.getConnection(url, username, password);
           ScriptRunner runner = new ScriptRunner(conn);
           Resources.setCharset(Charset.forName("utf8")); //设置字符集,不然中文乱码插入错误
           runner.setLogWriter(null);//设置是否输出日志
           //在resouse中新建一个文件夹：然后放入sql文件
      
           runner.runScript(Resources.getResourceAsReader("properties/shopping.sql"));
           //runner.runScript(Resources.getResourceAsReader("sql/CC21-01.sql"));
           runner.closeConnection();
           conn.close();
           res = "成功初始化数据库！";
       } catch (Exception e) {
           e.printStackTrace();
           res = "初始化数据库失败！";
       }
       return res;
   }
	
	
}
