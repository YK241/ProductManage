package servlet;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

public class ProductListServletTest {

    private ProductListServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ProductDAO productDao;

    @Before
    public void setUp() {
        servlet = spy(new ProductListServlet());

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        productDao = mock(ProductDAO.class);

        doReturn(productDao).when(servlet).createProductDAO();

        when(request.getRequestDispatcher("/product-list.jsp"))
                .thenReturn(dispatcher);
    }


    @Test
    public void カテゴリ指定なしの場合は全商品一覧が表示される() throws Exception {

        List<ProductBean> list = new ArrayList<>();
        list.add(new ProductBean());
        list.add(new ProductBean());

        when(request.getParameter("categoryId")).thenReturn(null);
        when(productDao.getAllProducts()).thenReturn(list);

        servlet.doGet(request, response);

        verify(productDao, times(1)).getAllProducts();
        verify(request).setAttribute("products", list);
        verify(dispatcher).forward(request, response);
    }


    @Test
    public void カテゴリ指定ありの場合はカテゴリ別商品が表示される() throws Exception {

        List<ProductBean> filteredList = new ArrayList<>();
        ProductBean product = new ProductBean();
        product.setCategoryId(1);
        filteredList.add(product);

        when(request.getParameter("categoryId")).thenReturn("1");
        when(productDao.getProductsByCategory(1)).thenReturn(filteredList);

        servlet.doGet(request, response);

        verify(productDao, times(1)).getProductsByCategory(1);
        verify(request).setAttribute("products", filteredList);
        verify(dispatcher).forward(request, response);
    }
}
