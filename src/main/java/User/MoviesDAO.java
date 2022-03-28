package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class MoviesDAO {

    public static List<Movies> searchByName(String name) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "";


        if (name.isEmpty()) {
            querry = "SELECT * FROM `movies`";
        } else {
            querry = "SELECT * FROM `movies` WHERE `name` LIKE '%" + name + "%'";
        }

        ArrayList<Movies> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            //         if(!name.isEmpty()){
            //             preparedStatement.setString(1, name);
            //       }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Kol turime sarase elementus
                list.add(new Movies(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("summary"),
                        resultSet.getDouble("imdb"),
                        resultSet.getString("category"),
                        resultSet.getInt("user_id")
                ));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Movies> getMoviesName() {
        int user_id;
        String username = UserSingleton.getInstance().getUserName();
        user_id = UserDAO.returnId(username);
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymas";
        String querry = "SELECT `id`, `name` FROM movies WHERE `user_id` = ?";
        ArrayList<Movies> list = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);

            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) { //Kol turime sarase elementus
                list.add(new Movies(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void update(Movies movie) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String update = "UPDATE `movies` SET `name` = ?, `summary` = ?, `imdb` = ?, `category` = ? WHERE `id` = ?";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setString(2, movie.getSummary());
            preparedStatement.setDouble(3, movie.getImdb());
            preparedStatement.setString(4, movie.getCategory());
            preparedStatement.setInt(5, movie.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            System.out.println("Pavyko atnaujinti įrašą");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko atnaujinti įrašą");
        }
    }

    public static void deleteById(int id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String delete = "DELETE FROM movies WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

            System.out.println("Pavyko ištrinti įrašą");
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("Įrašo ištrinti nepavyko");
        }
    }


    public static int isBooked(int id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymas";
        String querry2 = "SELECT `user_id` FROM `movies` WHERE `id` = ?";
        int x = 0;
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry2);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                x = resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko atnaujinti įrašo");
        }
        if (x > 0) {
            return -1;
        } else return 0;
    }

    public static void book(int id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "UPDATE `movies` SET `user_id` = ? WHERE `id` = ?";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setInt(1, id);

            PreparedStatement preparedStatement2 = connection.prepareStatement(querry);
            String username = UserSingleton.getInstance().getUserName();

            int user_id = UserDAO.returnId(username);
            Movies movie = new Movies();

            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            System.out.println("Pavyko atnaujinti įrašą");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko atnaujinti įrašą");
        }
    }

    public static void create(Movies book) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "INSERT INTO `movies`(`name`, `summary`, `imdb`, `category`,`user_id`) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");

            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getSummary());
            preparedStatement.setDouble(3, book.getImdb());
            preparedStatement.setString(4, book.getCategory());
            preparedStatement.setInt(5, NULL);
            //preparedStatement.setInt(7, book.getUser_id());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
