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

@WebServlet("/product-edit")
public class ProductEditServlet extends HttpServlet {
	
    protected ProductDAO createProductDAO() {
        return new ProductDAO();
    }

    protected CategoryDAO createCategoryDAO() {
        return new CategoryDAO();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		ProductBean product = createProductDAO().findById(id);

		if (product == null) {
			request.setAttribute("error", "商品が存在しません");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		List<CategoryBean> categories = createCategoryDAO().getAllCategories();

		request.setAttribute("product", product);
		request.setAttribute("categories", categories);

		request.getRequestDispatcher("/product-edit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    int id;
	    int price;
	    int stock;
	    int categoryId;

	    try {
	        id = Integer.parseInt(request.getParameter("id"));
	        price = Integer.parseInt(request.getParameter("price"));
	        stock = Integer.parseInt(request.getParameter("stock"));
	        categoryId = Integer.parseInt(request.getParameter("categoryId"));
	    } catch (NumberFormatException e) {
	        request.setAttribute("error", "数値項目は正しい数値を入力してください");

	        ProductBean product = new ProductBean();
	        product.setProductId(Integer.parseInt(request.getParameter("id")));
	        product.setName(request.getParameter("name"));

	        request.setAttribute("product", product);
	        request.setAttribute("categories", createCategoryDAO().getAllCategories());

	        request.getRequestDispatcher("/product-edit.jsp").forward(request, response);
	        return;
	    }

	    if (price < 0 || stock < 0) {
	        request.setAttribute("error", "価格と在庫数は0以上で入力してください");
	        ProductBean product = new ProductBean(
	            request.getParameter("name"), price, stock, categoryId
	        );
	        product.setProductId(id);

	        request.setAttribute("product", product);
	        request.setAttribute("categories", new CategoryDAO().getAllCategories());

	        request.getRequestDispatcher("/product-edit.jsp").forward(request, response);
	        return;
	    }

	    ProductBean product = new ProductBean(
	        request.getParameter("name"), price, stock, categoryId
	    );
	    product.setProductId(id);

	    createProductDAO().updateProduct(product);

	    request.getSession().setAttribute("message", "商品情報を更新しました");
	    response.sendRedirect("product-list");
	}

}
