package webomat.local;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Webomat_Loginerrorpage extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		
		HttpSession session = req.getSession();
		int rcanmeldung =  (int) session.getAttribute("rcanmeldung");
		
		res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head><title>Web-o-mat Loginerrorpage</title></head>");
        out.println("<link rel=\"stylesheet\" href=\"skeleton.css\">");
        out.println("<body>");
        out.println("<h1>Login not successful!</h1>");
        out.println("<h2>Returncode: " + rcanmeldung + "</h2>");
        out.println("<br>");
        out.println("<br>");
        out.println("<table class='u-full-width'>");
        out.println("<thead><tr><th>Return Code</th><th>Beschreibung</th></tr></thead>");
        out.println("<tbody><tr><td>0</td><td>Automic Web Interface is not available.</td></tr>");
        out.println("<tr><td>401</td><td>Unauthorized access - login data not valid.</td></tr>");
        out.println("<tr><td>403</td><td>Forbidden - the user is not allowed to access the resource.</td></tr>");
        out.println("<tr><td>404</td><td>The resource is not available.</td></tr>");
        out.println("<tr><td>405</td><td>Method not allowed.</td></tr>");
        out.println("<tr><td>500</td><td>Internal error occurred.</td></tr>");
        out.println("<tr><td>501</td><td>Service Unavailable.</td></tr></tbody></table>");
        out.println("<button class=\"button-primary\" onclick=\"window.location.href = 'index.html';\">Return to login</button>");
        out.println("</body></html>");
		
	}
}