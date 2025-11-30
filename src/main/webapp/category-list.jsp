<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.entity.CategoryBean"%>
<html>
<head>
<title>カテゴリ一覧</title>
</head>
<body>
	<h2>カテゴリ一覧</h2>
	<table border="1">
		<tr>
			<th>カテゴリID</th>
			<th>カテゴリ名</th>
		</tr>
		<%
		List<CategoryBean> categories = (List<CategoryBean>) request.getAttribute("categories");
		if (categories != null) {
			for (CategoryBean c : categories) {
		%>
		<tr>
			<td><%=c.getId()%></td>
			<td><%=c.getName()%></td>
		</tr>
		<%
		}
		}
		%>
	</table>

	<a href="category-register.jsp">新規登録</a>

</body>
</html>