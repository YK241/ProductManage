<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.entity.CategoryBean"%>
<html lang="ja">
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
			<td><%=c.getCategoryId()%></td>
			<td><%=c.getName()%></td>
		</tr>
		<%
		}
		}
		%>
	</table>

	<div style="margin-top: 20px;">
		<a href="category-register.jsp">新規登録</a> &nbsp;|&nbsp; <a
			href="product-add">商品登録</a> &nbsp;|&nbsp; <a href="product-list">商品一覧へ</a>
		&nbsp;|&nbsp; <a href="logout">ログアウト</a>
	</div>


</body>
</html>