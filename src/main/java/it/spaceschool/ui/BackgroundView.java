package it.spaceschool.ui;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BackgroundView {

    private final StackPane root = new StackPane();

    public BackgroundView() {
        Image img = new Image(getClass().getResourceAsStream("/images/space.jpg"));
        ImageView iv = new ImageView(img);

        iv.setPreserveRatio(false);
        iv.fitWidthProperty().bind(root.widthProperty());
        iv.fitHeightProperty().bind(root.heightProperty());

        root.getChildren().add(iv);
        root.getStyleClass().add("bg");
    }

    public Parent getRoot() {
        return root;
    }
}
