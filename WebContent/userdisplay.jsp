<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import ="com.group1.data.DbMgr " %>
<%@ page import= "java.sql.Connection" %>
<%@ page import= "java.sql.PreparedStatement"%>
<%@ page import= "java.sql.ResultSet" %>
<%@ page import= "javax.servlet.http.HttpSession" %>

<form action="Update" method=POST>
<%
try
{
String userId=(String) session.getAttribute("session1");
ServletContext ctx = getServletContext();
DbMgr dbMgr = (DbMgr) ctx.getAttribute("DbMgr");
Connection con = dbMgr.getConnection();
PreparedStatement pr;
pr = con.prepareStatement("select* from users where user_id=?");
pr.setString(1,userId);
ResultSet results = pr.executeQuery();
%>
<TABLE cellpadding="15" border="1" style="background-color: #ffffcc;">
<%
while (results.next()){
%>
<tr><td>User name:</td><td>value="<%=results.getString("user_name") %>"</td></tr>
<tr><td>Country:</td><td><input type="text" name="userCountry" id="userCountry" value="<%=results.getString("country") %>"/></td></tr>
<tr><td>Email:</td><td><input type="email" name="userEmail" id="userEmail" value="<%=results.getString("email") %>"/></td></tr>
<tr><td><input type="Submit" value="update"><td><a href="landing.html"><input type="button" value="cancel" /></a></td><tr>
</TABLE>


 <%} %>
 


</form>
<%
// close all the connections.
results.close();
pr.close();

} catch (Exception ex) {
%>
<font size="+3" color="red"></b>
<%
out.println("Site temporary unavailable.");
}
%>
</font>
</body>
</html>