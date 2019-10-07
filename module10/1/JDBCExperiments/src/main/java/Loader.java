import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Loader {

  public static void main(String[] args) {

    String url = "jdbc:mysql://localhost/skillbox1?serverTimezone=UTC";
    try {
      Connection connection = DriverManager.getConnection(url, "root", "mmm333");
      Statement statement = connection.createStatement();

      ResultSet resultSet = statement.executeQuery(
          "SELECT course_name, COUNT(subscription_date)/MAX(MONTH(subscription_date)) AS Avg_purchases FROM purchaselist GROUP BY course_name ORDER BY Avg_purchases DESC;");
      while (resultSet.next()){
        System.out.println(resultSet.getString("course_name") + "  -  " + resultSet.getDouble("Avg_purchases"));
      }
      resultSet.close();
      statement.close();
      connection.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
