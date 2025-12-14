<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="model.entity.ProductBean"%>

<%
ProductBean p = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>削除確認</title>
</head>
<body>

	<h2>削除確認</h2>

	<p>以下の商品を削除しますか？</p>

	<p>
		商品名：<%=p.getName()%><br> 価格：<%=p.getPrice()%>
	</p>

	<form action="product-delete" method="post" style="display: inline;">
		<input type="hidden" name="id" value="<%=p.getProductId()%>">
		<input type="submit" value="はい">
	</form>

	<form action="product-list" method="get"
		style="display: inline; margin-left: 10px;">
		<input type="submit" value="いいえ">
	</form>

</body>
</html>
