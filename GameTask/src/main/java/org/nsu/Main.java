package org.nsu;
import org.nsu.controller.Controller;
import org.nsu.model.Model;
import org.nsu.view.View;

public class Main {
    public static void main(String[] args) {
        // Создание объектов модели, представления и контроллера
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        // Отображение главного окна
        view.show();
    }
}