package vieira.ester.academia.controller;

import java.util.Scanner;

import vieira.ester.academia.dao.ExercicioDAO;
import vieira.ester.academia.model.Exercicio;

public class MenuController {

    private Scanner scanner;
    private ExercicioDAO exercicioDAO;

    public MenuController(Scanner scanner, ExercicioDAO exercicioDAO) {
        this.scanner = scanner;
        this.exercicioDAO = exercicioDAO;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n*============= MENU =============*");
            System.out.println("1 - Adicionar Exercício");
            System.out.println("2 - Listar Todos os Exercícios");
            System.out.println("3 - Filtrar Por Grupo Muscular");
            System.out.println("4 - Filtrar Por Nome");
            System.out.println("5 - Atualizar um Exercício");
            System.out.println("6 - Deletar um Exercício");
            System.out.println("0 - Sair");
            System.out.println("*================================*");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    adicionarExercicio();
                    break;
                case "2":
                    listarTodosExercicios();
                    break;
                case "3":
                    filtrarPorGrupoMuscular();
                    break;
                case "4":
                    filtrarPorNome();
                    break;
                case "5":
                    atualizarExercicio();
                    break;
                case "6":
                    deletarExercicio();
                    break;
                case "0":
                    sair();
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void adicionarExercicio() {
        System.out.print("Digite o nome do exercício: ");
        String nome = scanner.nextLine().toLowerCase();

        System.out.print("Digite o grupo muscular: ");
        String grupoMuscular = scanner.nextLine().toLowerCase();

        System.out.print("Digite o equipamento: ");
        String equipamento = scanner.nextLine().toLowerCase();

        Exercicio exercicio = new Exercicio(nome, grupoMuscular, equipamento);
        exercicioDAO.inserir(exercicio);

        System.out.println("\nExercício adicionado com sucesso!");
    }

    private void listarTodosExercicios(){
        exercicioDAO.listarTodos();
    }

    private void filtrarPorGrupoMuscular() {
        System.out.print("Digite o grupo para filtrar: ");
        String filtroGrupo = scanner.nextLine();

        exercicioDAO.listarPorGrupo(filtroGrupo);
    }

    private void filtrarPorNome() {
        System.out.print("Digite o nome para filtrar: ");
        String filtroNome = scanner.nextLine();

        exercicioDAO.listarPorNomeParcial(filtroNome);
    }

    private void atualizarExercicio() {
        System.out.print("Digite o nome do exercício para atualizar: ");
        String filtroNomeAtualizar = scanner.nextLine();

        exercicioDAO.atualizar(filtroNomeAtualizar);
    }

    private void deletarExercicio() {
        System.out.print("Digite o nome do exercício para deletar: ");
        String filtroNomeDeletar = scanner.nextLine();

        exercicioDAO.deletar(filtroNomeDeletar);
    }

    private void sair(){
        System.out.println("Saindo...");
        System.exit(0);
    }
}
