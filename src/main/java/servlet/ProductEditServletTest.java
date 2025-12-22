package servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.CategoryBean;
import model.entity.ProductBean;

public class ProductEditServletTest {

    private ProductEditServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ProductDAO productDao;
    private CategoryDAO categoryDao;

    @Before
    public void setUp() {
        servlet = spy(new ProductEditServlet());
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        productDao = mock(ProductDAO.class);
        categoryDao = mock(CategoryDAO.class);

        doReturn(productDao).when(servlet).createProductDAO();
        doReturn(categoryDao).when(servlet).createCategoryDAO();

        when(request.getRequestDispatcher("/product-edit.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    public void 既存商品情報が取得され編集画面に表示される() throws Exception {

        when(request.getParameter("id")).thenReturn("1");

        ProductBean product = new ProductBean();
        product.setProductId(1);
        product.setName("テスト商品");

        when(productDao.findById(1)).thenReturn(product);
        when(categoryDao.getAllCategories()).thenReturn(List.of(new CategoryBean()));

        servlet.doGet(request, response);

        verify(productDao).findById(1);
        verify(request).setAttribute("product", product);
        verify(request).setAttribute(eq("categories"), anyList());
        verify(dispatcher).forward(request, response);
    }


	
	    @Test
	    public void 編集内容がDB更新処理に渡される() throws Exception {
	
	        HttpSession session = mock(HttpSession.class);
	
	        when(request.getParameter("id")).thenReturn("1");
	        when(request.getParameter("name")).thenReturn("更新後商品");
	        when(request.getParameter("price")).thenReturn("200");
	        when(request.getParameter("stock")).thenReturn("5");
	        when(request.getParameter("categoryId")).thenReturn("2");
	        when(request.getSession()).thenReturn(session);
	
	        servlet.doPost(request, response);
	
	        verify(productDao).updateProduct(argThat(p ->
	                p.getProductId() == 1 &&
	                p.getName().equals("更新後商品") &&
	                p.getPrice() == 200 &&
	                p.getStock() == 5 &&
	                p.getCategoryId() == 2
	        ));
	
	        verify(response).sendRedirect("product-list");
	    }
}
