package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

@WebServlet("/product-delete-confirm")
public class ProductDeleteConfirmServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		ProductBean product = new ProductDAO().findById(id);

		if (product == null) {
			request.setAttribute("error", "商品が存在しません");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		request.setAttribute("product", product);
		request.getRequestDispatcher("/product-delete-confirm.jsp").forward(request, response);
	}
}
