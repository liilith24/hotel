package hotel.databaseOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import hotel.classes.Food;

public class FoodDb {

    private final Connection conn = DataBaseConnection.connectTODB();

    public void insertFood(Food food) {
        String insertSQL = "INSERT INTO food (name, price) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, food.getName());
            statement.setDouble(2, food.getPrice());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successfully inserted a new Food Type");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "\nInsert query for Food failed");
        }
    }

    public List<Food> getFoods() {
        List<Food> foodList = new ArrayList<>();
        String query = "SELECT * FROM food";

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Food food = new Food();
                food.setFoodId(result.getInt("food_id"));
                food.setName(result.getString("name"));
                food.setPrice(result.getDouble("price"));
                foodList.add(food);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "\nError retrieving all foods");
        }

        return foodList;
    }

    public void updateFood(Food food) {
        String updateSQL = "UPDATE food SET name = ?, price = ? WHERE food_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(updateSQL)) {
            statement.setString(1, food.getName());
            statement.setDouble(2, food.getPrice());
            statement.setInt(3, food.getFoodId());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successfully updated food");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "\nUpdate query for Food failed");
        }
    }

    public void deleteFood(int foodId) {
        String deleteSQL = "DELETE FROM food WHERE food_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(deleteSQL)) {
            statement.setInt(1, foodId);

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted food");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "\nDelete query for Food failed");
        }
    }
}
