package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

@WebServlet("/product-list")
public class ProductListServlet extends HttpServlet {

    protected ProductDAO createProductDAO() {
        return new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoryIdStr = request.getParameter("categoryId");

        List<ProductBean> products;
        ProductDAO dao = createProductDAO();

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            try {
                int categoryId = Integer.parseInt(categoryIdStr);
                products = dao.getProductsByCategory(categoryId);
            } catch (NumberFormatException e) {
                products = dao.getAllProducts();
            }
        } else {
            products = dao.getAllProducts();
        }

        request.setAttribute("products", products);

        request.getRequestDispatcher("/product-list.jsp")
               .forward(request, response);
    }
}
