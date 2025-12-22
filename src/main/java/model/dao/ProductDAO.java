package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.ProductBean;

public class ProductDAO {

	public boolean insertProduct(ProductBean product) {
		String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getCategoryId());

			pstmt.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ProductBean> getAllProducts() {
		List<ProductBean> products = new ArrayList<>();

		String sql = """
				    SELECT p.id, p.name, p.price, p.stock,
				           p.category_id, c.category_name
				    FROM products p
				    JOIN categories c ON p.category_id = c.id
				    ORDER BY p.id
				""";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				ProductBean product = new ProductBean();
				product.setProductId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setStock(rs.getInt("stock"));
				product.setCategoryId(rs.getInt("category_id"));
				product.setCategoryName(rs.getString("category_name"));

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public boolean deleteProduct(int id) {
		String sql = "DELETE FROM products WHERE id = ?";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			return pstmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ProductBean findById(int id) {
		ProductBean product = null;
		String sql = "SELECT id, name, price, stock, category_id FROM products WHERE id = ?";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				product = new ProductBean();
				product.setProductId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setStock(rs.getInt("stock"));
				product.setCategoryId(rs.getInt("category_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
	}

	public boolean updateProduct(ProductBean product) {
		String sql = "UPDATE products SET name=?, price=?, stock=?, category_id=? WHERE id=?";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getCategoryId());
			pstmt.setInt(5, product.getProductId());

			return pstmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ProductBean> getProductsByCategory(int categoryId) {
	    List<ProductBean> products = new ArrayList<>();

	    String sql = """
	        SELECT p.id, p.name, p.price, p.stock,
	               p.category_id, c.category_name
	        FROM products p
	        JOIN categories c ON p.category_id = c.id
	        WHERE p.category_id = ?
	        ORDER BY p.id
	    """;

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, categoryId);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            ProductBean product = new ProductBean();
	            product.setProductId(rs.getInt("id"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getInt("price"));
	            product.setStock(rs.getInt("stock"));
	            product.setCategoryId(rs.getInt("category_id"));
	            product.setCategoryName(rs.getString("category_name"));
	            products.add(product);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return products;
	}

	
	protected ProductDAO createProductDAO() {
	    return new ProductDAO();
	}

}
