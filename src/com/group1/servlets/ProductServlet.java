package com.group1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group1.data.DbMgr;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*Random random = new Random(System.nanoTime());
		int randomInt = random.nextInt(100000);*/
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
		int orderproduct_final_cost=0;
	    HttpSession session = request.getSession(true);
		String userId=(String) session.getAttribute("session1");
		// create connection with database
		ServletContext ctx = getServletContext();
		DbMgr dbMgr = (DbMgr) ctx.getAttribute("DbMgr");

		Connection con = dbMgr.getConnection();

		if(userId!=null)
		{
		String orderid=userId+generatedString;
		String orderproduct_id =request.getParameter("product_id"); 
	    String orderproduct_size =request.getParameter("product_size");
	    String orderproduct_cost = request.getParameter("product_cost");
    	orderproduct_final_cost=(Integer.parseInt(orderproduct_cost));
	    String orderproduct_shipping = request.getParameter("product_shipping");
	    String ordergiftwrap = request.getParameter("product_wrap");
	    
	    if(orderproduct_shipping!=null)
	    {
	    	orderproduct_final_cost=orderproduct_final_cost+30;
	    }
	    if(ordergiftwrap!=null)
	    {
	    	orderproduct_final_cost=orderproduct_final_cost+10;
	    }
	    //doGet(request, response);
		
		String insrtQuery = "INSERT INTO cartDetails(order_id, user_id, product_size, product_id, total_cost) "
				+ " values (?,?,?,?,?)";
		boolean success = false;
		try {
			PreparedStatement ps = con.prepareStatement(insrtQuery);
			ps.setString(1, orderid);
			ps.setString(2, userId);
			ps.setString(3, orderproduct_size);
			ps.setString(4, orderproduct_id);
			String ordercost= Integer.toString(orderproduct_final_cost);
			ps.setString(5, ordercost);
			success = ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		RequestDispatcher rd = ctx.getRequestDispatcher("/product.jsp");
		PrintWriter out = response.getWriter();
		out.println("<font color=green> successfully added to cart </font>");
		rd.include(request, response);
		}
		else{
			RequestDispatcher rd = ctx.getRequestDispatcher("/product.jsp");
			PrintWriter out = response.getWriter();
			String loginredirect="login.html";
			out.println("<font color=red> Sign in to proceed this order</font>"+"<a href="+loginredirect+"> click me</a>");
			rd.include(request, response);
		}
			

	}

}
