package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;

import java.io.File;

public class CadCadastro extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(javafx.stage.Stage stage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        Button uploadButton = new Button("Escolher Imagem");
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        });

        TextField nomeProduto = new TextField();
        nomeProduto.setPromptText("Nome do Produto");
        TextField descricao = new TextField();
        descricao.setPromptText("Descrição");
        Button salvarButton = new Button("Salvar");

        layout.getChildren().addAll(
                imageView,
                uploadButton,
                new Label("Informações do Produto"),
                nomeProduto,
                descricao,
                salvarButton
        );

        Scene scene = new Scene(layout, 400, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastro");
        stage.show();

    }

}