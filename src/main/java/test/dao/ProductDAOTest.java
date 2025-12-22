package test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import model.dao.ProductDAO;
import model.entity.ProductBean;

public class ProductDAOTest {

	@Test
	public void 商品がDBから削除され_一覧から消えることを確認() {

		ProductDAO dao = new ProductDAO();

		int deleteId = 4;

		boolean result = dao.deleteProduct(deleteId);
		assertTrue(result);

		List<ProductBean> list = dao.getAllProducts();

		boolean exists = list.stream().anyMatch(p -> p.getProductId() == deleteId);

		assertFalse(exists);
	}
}
