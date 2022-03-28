package com.example.filmai;


import User.Movies;
import User.MoviesDAO;
import User.UserDAO;
import User.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Validation;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label groupLabel, usernameLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField titleField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    @FXML
    private TextArea summaryField;
    @FXML
    private TextField imdbField;
    @FXML
    private TextField categoryField;

    // Lentelės stulpeliai
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn summaryColumn;
    @FXML
    private TableColumn imdbColumn;
    @FXML
    private TableColumn categoryCollumn;
    @FXML
    private TableColumn user_idCollumn;

    @FXML
    private TableView bookTableView;

    @FXML
    public void onLogOutButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("login-view.fxml"));
        Stage LoginStage = new Stage();
        LoginStage.setTitle("Prisijungimo langas");
        LoginStage.setScene(new Scene(root, 600, 400));
        LoginStage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    ObservableList<Movies> list = FXCollections.observableArrayList();

    @FXML
    public void onToBooked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("booked-view.fxml"));
        Stage LoginStage = new Stage();
        LoginStage.setTitle("Rezervuoti filmai");
        LoginStage.setScene(new Scene(root, 600, 400));
        LoginStage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void searchButtonClick() {
        list.clear();
        String nameField2 = nameField.getText();

        List<Movies> movieList = MoviesDAO.searchByName(nameField2);

        for (Movies movie : movieList) {

            list.add(new Movies(movie.getId(), movie.getName(), movie.getSummary(), movie.getImdb(), movie.getCategory(), movie.getUser_id()));


            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
            imdbColumn.setCellValueFactory(new PropertyValueFactory<>("imdb"));
            categoryCollumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            user_idCollumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));

            bookTableView.setItems(list);
        }
        /*
        if (bookList.isEmpty()) {
            status.setText("Nepavyko atlikti paieška pagal filmų pavadinimą");
        } else {
            status.setText("Pavyko atlikti paieška pagal filmų pavadinimą");
        }*/
    }

        @FXML
        public void onCreateButtonClick () {
            String nameField2 = nameField.getText();
            String summaryField2 = summaryField.getText();
            String imdbField2 = imdbField.getText();
            String categoryField2 = categoryField.getText();

            // Tikriname pagal Validacijas
            if (!Validation.isValidTitle(nameField2)) {
                statusLabel.setText("Neteisingai įvedėt pavadinimą");
            } else {
                // keiciame kintamuju tipus pagal konstruktoriu
                double imdb = Double.parseDouble(imdbField.getText());
                // int year2 = Integer.parseInt(year);
                //int secondsField3 = Integer.parseInt(secondsField.getText());

                // Kuriame įrašą DB
                //int userID = UserDAO.searchByUsernameReturnID(usernameLabel.getText());

                Movies movie = new Movies(nameField2, summaryField2, imdbField2, categoryField2);
                MoviesDAO.create(movie);
                statusLabel.setText("Pavyko sukurti įrašą");
            }
        }

        @FXML
        public void onBookButtonClick () {
            int id = Integer.parseInt(idField.getText());
            int x;
            x = MoviesDAO.isBooked(id);
            if (x == -1) {
                statusLabel.setText("Filmas jau rezervuotas kito vartotojo");
            } else if (x == 0) {
                MoviesDAO.book(id);
                statusLabel.setText("Filmas sėkmingai rezervuotas");
            }
        }

        @FXML
        public void onUpdateButtonClick () {
            String nameField2 = nameField.getText();
            String summaryField2 = summaryField.getText();
            String isbnField2 = imdbField.getText();
            String categoryField2 = categoryField.getText();

            if (groupLabel.getText().equals("ADMINISTRATORIUS")) {
                // keiciame kintamuju tipus pagal konstruktoriu
                double imdbField2 = Double.parseDouble(imdbField.getText());
                int id = Integer.parseInt(idField.getText());

            Movies movie = new Movies(id, nameField2, summaryField2, imdbField2, categoryField2);
            MoviesDAO.update(movie);
                statusLabel.setText("Pavyko paredaguoti įrašą");
            } else {
                // Vartotojas
                statusLabel.setText("Redaguoti įraša gali tik administratorius.");
            }


        }

        @FXML
        public void onDeleteButtonClick () {
            String idField2 = idField.getText();

            if (groupLabel.getText().equals("ADMINISTRATORIUS")) {
                if (!Validation.isValidTime(idField2)) {
                    statusLabel.setText("Neteisingai įvestas ID");
                } else {
                    int idField3 = Integer.parseInt(idField.getText());
//                MoviesDAO.deleteById(idField3);
                    statusLabel.setText("Pavyko sėkmingai ištrinti įrašą");
                }
            } else {
                // Vartotojas
                statusLabel.setText("Trinti įraša gali tik administratorius.");
            }
        }

        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){

            // Parodomas prisijunges vartotojas
            String username = UserSingleton.getInstance().getUserName();
            usernameLabel.setText(username);

            // Parodoma prisijungusio vartotojo role(grupe)
            String role = UserDAO.searchByUsername(username);
            groupLabel.setText(role);

        }
    }

