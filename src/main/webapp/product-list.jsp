<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.entity.ProductBean"%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body>
	<%
	String message = (String) session.getAttribute("message");
	if (message != null) {
	%>
	<p style="color: green;"><%=message%></p>
	<%
	session.removeAttribute("message");
	}
	%>

	<h2>商品一覧</h2>

	<table border="1">
		<tr>
			<th>ID</th>
			<th>商品名</th>
			<th>価格</th>
			<th>在庫数</th>
			<th>カテゴリ</th>
			<th>編集</th>
			<th>削除</th>
		</tr>

		<%
		List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");
		if (products != null) {
			for (ProductBean p : products) {
		%>
		<tr>
			<td><%=p.getProductId()%></td>
			<td><%=p.getName()%></td>
			<td><%=p.getPrice()%></td>
			<td><%=p.getStock()%></td>
			<td><%=p.getCategoryName()%></td>
			<td>
				<form action="product-edit" method="get" style="margin: 0;">
					<input type="hidden" name="id" value="<%=p.getProductId()%>">
					<input type="submit" value="編集">
				</form>
			</td>
			<td>
				<form action="product-delete-confirm" method="get"
					style="margin: 0;">
					<input type="hidden" name="id" value="<%=p.getProductId()%>">
					<input type="submit" value="削除">
				</form>
			</td>
		</tr>
		<%
		}
		}
		%>
	</table>

	<p>
		<a href="product-add">商品登録へ</a> &nbsp;|&nbsp; <a href="category-list">カテゴリー表へ</a>
		&nbsp;|&nbsp; <a href="logout">ログアウト</a>
	</p>

</body>
</html>
