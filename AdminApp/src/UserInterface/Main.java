package UserInterface;


import Actors.Article;
import DatabaseUtils.ArticleManagement;
import DatabaseUtils.DBConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {
    Stage window;
    Scene menuScene, newArticleScene, removeArticleScene, displayArticleScene, modifyArticleScene;
    DBConnection connection;
    public static final float PREF_BUTTON_WIDTH = 180;
    public static final float PREF_BUTTON_HEIGTH = 30;

    @Override
    public void start(Stage primaryStage) throws Exception{
        connection = new DBConnection("Passw0rd", "postgres", "jdbc:postgresql://156.17.130.97:5432/footballdb");
        connection.connectDB();
        window = primaryStage;
        initWindow();
        initScenes();
        window.setScene(menuScene);
        primaryStage.show();
    }

    private void initWindow(){
        window.setTitle("FootballMania - zarządzanie artykułami");
        window.setWidth(800);
        window.setHeight(800);
        window.setResizable(false);
    }

    private void initScenes(){
        menuScene = new Scene(createMainMenuLayout());
        newArticleScene = new Scene(createAddArticleLayout());
        removeArticleScene = new Scene(createRemoveArticleLayout());
        displayArticleScene = new Scene(createDisplayArticlesLayout());
        modifyArticleScene = new Scene(createModifyArticleLayout());
    }

//TODO: wyswietl wszystkie artykuły

    private VBox createMainMenuLayout(){
        Button addArticle = new Button("Dodaj artykuł");
        addArticle.setOnAction(e -> window.setScene(newArticleScene));
        addArticle.setPrefHeight(PREF_BUTTON_HEIGTH);
        addArticle.setPrefWidth(PREF_BUTTON_WIDTH);
        Button modifyArticle = new Button("Modyfikuj artykuł");
        modifyArticle.setOnAction(e -> window.setScene( new Scene(createModifyArticleLayout())));
        modifyArticle.setPrefHeight(PREF_BUTTON_HEIGTH);
        modifyArticle.setPrefWidth(PREF_BUTTON_WIDTH);
        Button removeArticle = new Button("Usuń artykuł");
        removeArticle.setOnAction(e -> {
            window.setScene(new Scene(createRemoveArticleLayout()));
        });
        removeArticle.setPrefHeight(PREF_BUTTON_HEIGTH);
        removeArticle.setPrefWidth(PREF_BUTTON_WIDTH);
        Button displayArticles = new Button("Pokaż artykuły");
        displayArticles.setOnAction(e -> window.setScene(new Scene(createDisplayArticlesLayout())));
        displayArticles.setPrefHeight(PREF_BUTTON_HEIGTH);
        displayArticles.setPrefWidth(PREF_BUTTON_WIDTH);
        VBox vBox = new VBox(addArticle, modifyArticle, removeArticle, displayArticles);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

//    private ArrayList<Button> createListOfAllArticlesAsButtons(){
//        ArrayList<Button> articlesAsButtons = new ArrayList<>();
//        ArrayList<String> allArticles = ArticleManagement.getAllArticles(connection.getConnection());
//        for (String article : allArticles) {
//            Button button = new Button(article);
//            articlesAsButtons.add(button);
//        }
//        return articlesAsButtons;
//    }

    private VBox createDisplayArticlesLayout(){
        ChoiceBox<String> choiceBox = createChoiceBoxFromArray(ArticleManagement.getAllArticles(connection.getConnection()));
        choiceBox.setPrefWidth(PREF_BUTTON_WIDTH);
        Label author = new Label("autor");
        Label title = new Label("tytuł");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));
        Button backButton = new Button("Powrót");
        backButton.setOnAction(e -> {
            window.setScene(menuScene);
        });
        backButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        backButton.setPrefWidth(PREF_BUTTON_WIDTH);
        choiceBox.setOnAction(e -> {
            String[] words = choiceBox.getSelectionModel().getSelectedItem().split("\\s");
            int articleId = Integer.valueOf(words[0]);
            Article article = null;
            try {
                article = Controller.mapResultSetToArticle(ArticleManagement.getArticleByID(connection.getConnection(), articleId),connection.getConnection());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            author.setText("Autor: " + article.getAuthor().getFirstName() + " " + article.getAuthor().getLastName());
            title.setText("Tytuł: " + article.getTitle());
            Label content = new Label(article.getContent());
            content.setWrapText(true);
            scrollPane.setContent(content);
            scrollPane.setMinViewportHeight(200);
        });
        VBox vBox = new VBox(choiceBox, author, title, scrollPane, backButton);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;

    }

    private VBox createAddArticleLayout(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\PWR\\SEMESTR_6\\SI\\LAB4\\wiki_train_34_categories_data"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Label label = new Label("Dodaj artykuł");
        Label authorLabel = new Label("Adres email autora");
        TextField authorInput = new TextField();
        Label titleLabel = new Label("Tytuł");
        TextField titleInput = new TextField();
        Label categoryLabel = new Label("Wybierz kategorię, której dotyczy artykuł");
        ChoiceBox<String> categories = createChoiceBoxFromArray(ArticleManagement.getArticleCategories(connection.getConnection()));
        categories.setPrefWidth(PREF_BUTTON_WIDTH);
        Label tagLabel = new Label("Wpisz listę tagów, oddziel je średnikami");
        TextField tagInput = new TextField();
        Label contentLabel = new Label("Zawartość");
        Button addButton = new Button("Dodaj artykuł");
        addButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        addButton.setPrefWidth(PREF_BUTTON_WIDTH);
        addButton.setDisable(true);
        Button selectContentButton = new Button("Wybierz plik");
        selectContentButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        selectContentButton.setPrefWidth(PREF_BUTTON_WIDTH);
        selectContentButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(window);
            enableButton(addButton, selectedFile, authorInput.getText(), titleInput.getText());
            addButton.setOnAction(e1 -> {
                createPopupWindow(Controller.createArticle(authorInput.getText(), titleInput.getText(), selectedFile.getAbsolutePath(), categories.getValue(), tagInput.getText(), connection.getConnection()));
                addButton.setDisable(true);
            });
        });
        Button backButton = new Button("Powrót");
        backButton.setOnAction(e -> {
            window.setScene(menuScene);
            clearTextFields(authorInput, titleInput);
        });
        backButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        backButton.setPrefWidth(PREF_BUTTON_WIDTH);
        VBox vBox = new VBox(label, authorLabel, authorInput, titleLabel, titleInput, categoryLabel, categories, tagLabel, tagInput, contentLabel, selectContentButton, addButton, backButton);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }

    private void clearTextFields(TextField authorInput, TextField titleInput) {
        authorInput.clear();
        titleInput.clear();
    }

    private void enableButton(Button button, File content, String author, String title) {
        if(content == null || author == null || title == null){
            button.setDisable(true);
        }else{
            button.setDisable(false);
        }
    }

    private VBox createRemoveArticleLayout(){
        Label label = new Label("Wybierz artykuł, który chcesz usunąć");
        ChoiceBox<String> articles = createChoiceBoxFromArray(ArticleManagement.getAllArticles(connection.getConnection()));
        articles.setPrefWidth(PREF_BUTTON_WIDTH);
        Button deleteButton = new Button("Usuń");
        deleteButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        deleteButton.setPrefWidth(PREF_BUTTON_WIDTH);
        deleteButton.setOnAction(e -> {
            createPopupWindow(Controller.deleteArticle(articles.getSelectionModel().getSelectedItem(), connection.getConnection()));
            updateArticlesList(articles, ArticleManagement.getAllArticles(connection.getConnection()));
        });
        Button backButton = new Button("Powrót");
        backButton.setOnAction(e -> window.setScene(menuScene));
        backButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        backButton.setPrefWidth(PREF_BUTTON_WIDTH);
        VBox vBox = new VBox(label, articles, deleteButton, backButton);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }

    private VBox createModifyArticleLayout(){
        Label label = new Label("Wybierz artykuł, który chcesz zmodyfikować");
        ChoiceBox<String> articles = createChoiceBoxFromArray(ArticleManagement.getAllArticles(connection.getConnection()));
        articles.setPrefWidth(PREF_BUTTON_WIDTH);
        Button modifyButton = new Button("Modyfikuj artykuł");
        modifyButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        modifyButton.setPrefWidth(PREF_BUTTON_WIDTH);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Label authorLabel = new Label("Adres email autora");
        TextField authorInput = new TextField();
        Label idLabel = new Label();
        Label titleLabel = new Label("Tytuł");
        TextField titleInput = new TextField();
        Label contentLabel = new Label("Zawartość");
        Button selectContentButton = new Button("Zmień plik z treścią artykułu");
        selectContentButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        selectContentButton.setPrefWidth(PREF_BUTTON_WIDTH);
        final Article[] article = {null};
        articles.setOnAction(e -> {
            String[] words = articles.getSelectionModel().getSelectedItem().split("\\s");
            int articleId = Integer.valueOf(words[0]);
            try {
                article[0] = Controller.mapResultSetToArticle(ArticleManagement.getArticleByID(connection.getConnection(), articleId),connection.getConnection());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            authorInput.setText(article[0].getAuthor().getEmail());
            titleInput.setText(article[0].getTitle());
            idLabel.setText(Integer.toString(article[0].getArticleID()));
        });
        final File[] selectedFile = {null};
        selectContentButton.setOnAction(e -> {
            selectedFile[0] = fileChooser.showOpenDialog(window);
        });
        Button backButton = new Button("Powrót");
        backButton.setOnAction(e -> {
            window.setScene(menuScene);
            clearTextFields(authorInput, titleInput);
        });
        modifyButton.setOnAction(e -> {
            if(selectedFile[0] == null){
                createPopupWindow(Controller.modifyArticle(Integer.valueOf(idLabel.getText()), authorInput.getText(), titleInput.getText(), article[0].getContent(), connection.getConnection()));
            }else{
                createPopupWindow(Controller.modifyArticle(Integer.valueOf(idLabel.getText()), authorInput.getText(), titleInput.getText(), Controller.readFile(selectedFile[0].getAbsolutePath()), connection.getConnection()));
            }
        });
        backButton.setPrefHeight(PREF_BUTTON_HEIGTH);
        backButton.setPrefWidth(PREF_BUTTON_WIDTH);
        VBox vBox = new VBox(label, articles, authorLabel, authorInput, titleLabel, titleInput, contentLabel,  selectContentButton, modifyButton, backButton);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        return vBox;
    }

    private ChoiceBox createChoiceBoxFromArray(ArrayList<String> articles){
        ChoiceBox<String> articlesList = new ChoiceBox<>();
        for (String article : articles ) {
            articlesList.getItems().add(article);
        }
        return articlesList;
    }

    private void updateArticlesList(ChoiceBox<String> choiceBox, ArrayList<String> articles){
        choiceBox.getItems().clear();
        for (String article : articles ) {
            choiceBox.getItems().add(article);
        }
    }

    private void createPopupWindow(Boolean isSuccess){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if ((isSuccess)) {
            alert.setHeaderText("Operacja udana!");
        } else {
            alert.setHeaderText("Operacja nieudana!");
        }
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
