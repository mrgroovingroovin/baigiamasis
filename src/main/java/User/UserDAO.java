package User;

import java.sql.*;

public class UserDAO {

    public static void create(User user) {
        //Prisijungimai prie db
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "INSERT INTO `users`(`username`, `password`, `email`, `isadmin`) VALUES (?, ?, ?, ?)";

        //Vykdome prisijungimai prie db ir atliekama uzklausa
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");

            //Siekiant isvengti sql injekciju, kiekviena laukeli surasom uzklausa atskirai - ignoruojami specialus simboliai
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBoolean(4, user.isAdmin());

            //Naujo įrašo sukurimui, esamo iraso redagavimui ir trynimui, naudojame execute update metoda
            //Esamo iraso paieskai naudojame executeQuery metoda
            preparedStatement.executeUpdate();

            //vykdzius uzklausa gera praktika uzdaryti prisijungimus
            preparedStatement.close();
            connection.close();

            System.out.println("Pavyko sukurti įrašą");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Įvyko klaida, kuriant naują įrašą");
        }
    }

    public static int returnId(String name) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "SELECT id FROM `users` WHERE `username` = ?";
        int id = 0;
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    public static String getBCryptPassword(String username) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "SELECT password FROM `users` WHERE `username` = ?";

        String passwordBCrypted = "";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1, username);

            //Tik įrašu paieskai executeQuery()
            // ResultSet yra RAW neapdirbti duomenys. Stulpelio pav atitinka rakta, o konkreti iraso reiksme
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) { //Kol turime sarase elementus
                passwordBCrypted = resultSet.getString("password");
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwordBCrypted;
    }

    //---------------------Įrašo paieška pagal username---------------------
    public static String searchByUsername(String username) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymasdb";
        String querry = "";
        querry = "SELECT * FROM `users` WHERE `username` = ?";

        String username2 = "";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                username2 = resultSet.getBoolean("isadmin") ? "ADMINISTRATORIUS" : "KLIENTAS";
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username2;
    }

}
