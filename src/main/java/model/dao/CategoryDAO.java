package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

    public List<CategoryBean> getAllCategories() {
        List<CategoryBean> categories = new ArrayList<>();
        String sql = "SELECT id, category_name FROM categories";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

        	while (rs.next()) {
        	    CategoryBean category = new CategoryBean();
        	    category.setId(rs.getInt("id"));
        	    category.setName(rs.getString("category_name"));
        	    System.out.println("取得: " + category.getId() + " - " + category.getName());
        	    categories.add(category);
        	}

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}