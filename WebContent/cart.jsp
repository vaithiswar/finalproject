<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import ="com.group1.data.DbMgr " %>
<%@ page import= "java.sql.Connection" %>
<%@ page import= "java.sql.PreparedStatement"%>
<%@ page import= "java.sql.ResultSet" %>
<%@ page import= "javax.servlet.http.HttpSession" %>

<form action="CartServlet" method=POST>
<%
try
{
	String userId=(String)session.getAttribute("session1");
	ServletContext ctx = getServletContext();
	DbMgr dbMgr = (DbMgr) ctx.getAttribute("DbMgr");
	Connection con = dbMgr.getConnection();
	PreparedStatement pr,pr_product;
	pr = con.prepareStatement("select a.order_id,a.product_id,a.product_size,a.total_cost,b.product_name from cartdetails a,productdetails b where b.product_id=a.product_id and user_id=?");
	pr.setString(1,userId);
	ResultSet results = pr.executeQuery();
	int totalprice=0;
	
	
%>
<Table  border="1" style="background-color: #ffffcc;">
<td>Order id</td>
<td>Product id</td>
<td>Product Name</td>
<td>Size ordered</td>
<td>Price</td>
<%
while (results.next()){
%>
<tr>
<td><input type="text" name="order_id" value="<%=results.getString("ORDER_ID")%>" readonly></td>
<td><input type="text" name="product_id" value="<%=results.getString("PRODUCT_ID")%>" readonly></td>
<td><input type="text" name="product_name" value="<%=results.getString("PRODUCT_NAME")%>" readonly></td>
<td><input type="text" name="product_size" value="<%=results.getString("PRODUCT_SIZE")%>"readonly></td>
<td><input type="text" name="product_cost" value="<%=results.getString("TOTAL_COST")%>"readonly></td>
<%
totalprice=totalprice+Integer.parseInt(results.getString("TOTAL_COST"));
}
%>
<tr><td></td><td></td><td></td><td>total cost:</td><td><input type="text" value="<%=totalprice%>"readonly></td></tr>
<tr><td></td><td></td><td></td><td><input type="Submit" value="clear cart"></td><td><a href="final.html"><input type="button" value="Proceed to pay" /></a></td></tr>
</TABLE>
<table>
<tr><td><a href="landing.html"><input type="button" value="home" /></a></td>
<td><a href="product.jsp"><input type="button" value="back" /></a></td>
</tr>
</table>

</form>
<%
System.out.println(totalprice);
// close all the connections.
results.close();
pr.close();
} catch (Exception ex) {
%>
<font size="3" color="red"/>
<%
out.println("Requested data currently unavailable");
}
%>
</body>
</html>