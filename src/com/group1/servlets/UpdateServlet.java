package com.group1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * Servlet implementation class UpdateServlet
 */
@WebServlet(name = "Update", urlPatterns = { "/Update" })
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
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
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
       String userId=(String) session.getAttribute("session1");
       //out.println(userId);
        ServletContext sc1 = getServletContext();
        DbMgr dbMgr = (DbMgr) sc1.getAttribute("DbMgr"); 
        Connection con = dbMgr.getConnection();
       
       String email =request.getParameter("userEmail");
       
       String country =request.getParameter("userCountry");
       PreparedStatement pr;
        try {
            pr = con.prepareStatement("update users set email=?, country=? where user_Id=?");
            pr.setString(1,email);
       pr.setString(2,country);
       pr.setString(3,userId);
       pr.executeUpdate();
       RequestDispatcher rd = sc1.getRequestDispatcher("/landing.html");
       PrintWriter outer = response.getWriter();
       outer.println("<font color=green> successfully updated your details </font>");
		rd.include(request, response);
       //response.sendRedirect("/final.html");
        } catch (SQLException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       
       
        
        
        
 }

}
