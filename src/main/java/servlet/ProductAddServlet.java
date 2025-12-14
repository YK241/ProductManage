package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.CategoryBean;
import model.entity.ProductBean;

@WebServlet("/product-add")
public class ProductAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<CategoryBean> categories = new CategoryDAO().getAllCategories();
		request.setAttribute("categories", categories);

		request.getRequestDispatcher("/product-add.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String priceStr = request.getParameter("price");
		String stockStr = request.getParameter("stock");
		String categoryIdStr = request.getParameter("categoryId");

		if (name == null || name.isEmpty()
				|| priceStr == null || priceStr.isEmpty()
				|| stockStr == null || stockStr.isEmpty()
				|| categoryIdStr == null || categoryIdStr.isEmpty()) {

			request.setAttribute("error", "すべての項目を正しく入力してください。");

			List<CategoryBean> categories = new CategoryDAO().getAllCategories();
			request.setAttribute("categories", categories);

			request.getRequestDispatcher("/product-add.jsp").forward(request, response);
			return;
		}

		int price;
		int stock;
		int categoryId;

		try {
			price = Integer.parseInt(priceStr);
			stock = Integer.parseInt(stockStr);
			categoryId = Integer.parseInt(categoryIdStr);
		} catch (NumberFormatException e) {

			request.setAttribute("error", "数値項目には正しい数値を入力してください。");

			List<CategoryBean> categories = new CategoryDAO().getAllCategories();
			request.setAttribute("categories", categories);

			request.getRequestDispatcher("/product-add.jsp").forward(request, response);
			return;
		}

		if (price < 0 || stock < 0) {

			request.setAttribute("error", "価格と在庫数は0以上で入力してください。");

			List<CategoryBean> categories = new CategoryDAO().getAllCategories();
			request.setAttribute("categories", categories);

			request.getRequestDispatcher("/product-add.jsp").forward(request, response);
			return;
		}

		ProductBean product = new ProductBean(name, price, stock, categoryId);
		boolean result = new ProductDAO().insertProduct(product);

		if (result) {
			response.sendRedirect("product-list");
		} else {
			request.setAttribute("error", "登録に失敗しました。");

			List<CategoryBean> categories = new CategoryDAO().getAllCategories();
			request.setAttribute("categories", categories);

			request.getRequestDispatcher("/product-add.jsp").forward(request, response);
		}
	}
}
