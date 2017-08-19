<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import ="com.group1.data.DbMgr " %>
<%@ page import= "java.sql.Connection" %>
<%@ page import= "java.sql.PreparedStatement"%>
<%@ page import= "java.sql.ResultSet" %>
<%@ page import= "javax.servlet.http.HttpSession" %>

<form action="ProductServlet" method=POST>
<%
try
{
	String userId=(String)session.getAttribute("session1");
	ServletContext ctx = getServletContext();
	DbMgr dbMgr = (DbMgr) ctx.getAttribute("DbMgr");
	Connection con = dbMgr.getConnection();
	PreparedStatement pr;
	pr = con.prepareStatement("select * from productDetails");
	ResultSet results = pr.executeQuery();
%>
<Table  border="1" style="background-color: #ffffcc;">
<td>Product id</td>
<td>Product Name</td>
<td>Description</td>
<td>cost</td>
<td>Size</td>
<td>Shipping</td>
<td>Gift wrap</td>
<td>Order</td>
<%
while (results.next()){
%>
<tr>
<td><input type="text" name="product_id" value="<%=results.getString("PRODUCT_ID")%>" readonly></td>
<td><input type="text" name="product_name" value="<%=results.getString("PRODUCT_NAME")%>" readonly></td>
<td><input type="text" name="product_description" value="<%=results.getString("PRODUCT_DESCRIPTION")%>" readonly></td>
<td><input type="text" name="product_cost" value="<%=results.getString("PRODUCT_COST")%>"readonly></td>
<td><select name="product_size"><option>S</option><option>M</option><option>L</option></select></td>
<td><input type="checkbox" name="product_shipping" value="yes" unchecked>Fast Shipping</td>
<td><input type="radio" name="product_wrap" value="yes" unchecked>Show your love</td>
<td><input type="Submit" value="Add to cart"></td>
<%}%>
</TABLE>
<p>*showing your love would cost just 10$ extra than the price</p>
<p>**fast shipping is 30$ extra than the specified price</p>
</form>
<table>
<tr><td><a href="landing.html"><input type="button" value="back" /></a></td>
<td><a href="cart.jsp"><input type="button" value="Check out" /></a></td>
</tr>
</table>
<%
// close all the connections.
results.close();
pr.close();
} catch (Exception ex) {
%>
<b><font size="+3" color="red"></b>
<%
out.println("Requested data currently unavailable");
}
%>
</body>
</html>