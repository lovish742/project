package it.spaceschool.ui;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class AppShell {

    private final StackPane root = new StackPane();
    private final StackPane content = new StackPane();

    public AppShell(Parent background) {
        root.getChildren().addAll(background, content);
    }

    public void setContent(Parent viewRoot) {
        content.getChildren().setAll(viewRoot);
    }

    public Parent getRoot() {
        return root;
    }
}
