package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

	public boolean insertCategory(int id, String name) {
		String sql = "INSERT INTO categories (id, category_name) VALUES (?, ?)";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.out.println("登録エラー: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public List<CategoryBean> getAllCategories() {
		List<CategoryBean> categories = new ArrayList<>();
		String sql = "SELECT id, category_name FROM categories";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				CategoryBean category = new CategoryBean();
				category.setCategoryId(rs.getInt("id"));
				category.setName(rs.getString("category_name"));
				categories.add(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}
}
