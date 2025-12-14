<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.entity.CategoryBean"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品登録</title>
</head>
<body>

	<h2>商品登録</h2>

	<%
	String error = (String) request.getAttribute("error");
	if (error != null) {
	%>
	<p style="color: red"><%=error%></p>
	<%
	}
	%>

	<form action="product-add" method="post">
		<p>
			商品名: <input type="text" name="name" required>
		</p>

		<p>
			価格: <input type="number" name="price" min="0" required>
		</p>

		<p>
			在庫数: <input type="number" name="stock" min="0" required>
		</p>

		<p>
			カテゴリ: <select name="categoryId" required>
				<option value="">選択してください</option>

				<%
				List<CategoryBean> categories = (List<CategoryBean>) request.getAttribute("categories");

				if (categories != null) {
					for (CategoryBean c : categories) {
				%>
				<option value="<%=c.getCategoryId()%>">
					<%=c.getName()%>
				</option>
				<%
				}
				}
				%>

			</select>
		</p>

		<p>
			<input type="submit" value="登録">
		</p>
	</form>

</body>
</html>
