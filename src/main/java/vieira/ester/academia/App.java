package vieira.ester.academia;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            ExercicioDAO exercicioDAO = new ExercicioDAO();
            
            MenuController menuController = new MenuController(scanner, exercicioDAO);

            menuController.iniciar();

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao iniciar o aplicativo. Detalhes: " + e.getMessage());
        }
    }
}
