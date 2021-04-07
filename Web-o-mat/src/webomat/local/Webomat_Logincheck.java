package webomat.local;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

public class Webomat_Logincheck  extends HttpServlet{

	/**
	 * Copyright 2021 André Büttemeyer
	 * Free to use under the MIT license.
	 * http://www.opensource.org/licenses/mit-license.php
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		String userid = req.getParameter("userid");
		String password = req.getParameter("password");
		
		String authenticationtoken  = createauthenticationtoken(userid, password);
		String runid = "";
		String jobstate = "";
		
		ServletContext ctx = getServletContext();
		String aeresturl = ctx.getInitParameter("AERestURL");
		
		int rcanmeldung = ishealthy(aeresturl, authenticationtoken);
		
		if (rcanmeldung == 200) {
			HttpSession session = req.getSession();
			session.setAttribute("authenticationtoken", authenticationtoken);
			session.setAttribute("runid", runid);
			session.setAttribute("jobstate", jobstate);
			res.sendRedirect("sitestart");
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("rcanmeldung", rcanmeldung);
			res.sendRedirect("webomat_loginerrorpage");
		}
		
	}
	
	public String createauthenticationtoken (String userid, String password) {
		String authentication = userid + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(authentication.getBytes(StandardCharsets.UTF_8));
		String authenticationtoken = "Basic " + new String(encodedAuth);
		return authenticationtoken;
	}
	
	public int ishealthy (String aeresturl, String authhead) {
		int status = 0;
		
		try {
			URL url = new URL(aeresturl + "system/health");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestProperty("Authorization", authhead);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			status = con.getResponseCode();
			con.disconnect();
				
		} catch (MalformedURLException e){
	            e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		} finally {
		}
		return status;
		
	}
	
}
