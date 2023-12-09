package vieira.ester.academia.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import vieira.ester.academia.model.Exercicio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

public class ExercicioDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> exercicios;

    public ExercicioDAO() {
        // Conectar ao MongoDB local
        MongoClientSettings conexao = MongoClientSettings.builder()
            .applyToClusterSettings(builder ->
                    builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
            .build();

        MongoClient mongoClient = MongoClients.create(conexao);

        // Selecionar ou criar um banco de dados e uma coleção
        this.database = mongoClient.getDatabase("academia");
        this.exercicios = database.getCollection("exercicios");
    }

    public void inserir(Exercicio exercicio) {
        Document documento = new Document("nome", exercicio.getNome())
                .append("grupoMuscular", exercicio.getGrupoMuscular())
                .append("equipamento", exercicio.getEquipamento());

        exercicio.setDocument(documento);

        exercicios.insertOne(documento);
    }

    public void listarTodos(){
        MongoCursor<Document> cursor = exercicios.find().iterator();

        try {
            System.out.println("\n-----------TODOS EXERCÍCIOS-----------\n");

            // Itera sobre os documentos e imprime as informações
            while (cursor.hasNext()) {
                Document documento = cursor.next();
                System.out.println("ID: " + documento.getObjectId("_id"));
                System.out.println("Nome: " + documento.getString("nome"));
                System.out.println("Grupo Muscular: " + documento.getString("grupoMuscular"));
                System.out.println("Equipamento: " + documento.getString("equipamento"));
                System.out.println("--------------------------------------");
            }
        } finally {
            cursor.close();
        }
    }

    public List<Exercicio> listarPorGrupo(String grupoMuscular){
        Document filtro = new Document("grupoMuscular", grupoMuscular);

        FindIterable<Document> resultado = exercicios.find(filtro);
        List<Exercicio> exerciciosPorGrupo = new ArrayList<>();

        if(resultado != null && resultado.iterator().hasNext()){
            for(Document documento: resultado){
                Exercicio exercicio = new Exercicio(
                    documento.getString("nome"),
                    documento.getString("grupoMuscular"),
                    documento.getString("equipamento"));

                    exercicio.setDocument(documento);
            
                exerciciosPorGrupo.add(exercicio);
            }

        exerciciosPorGrupo.forEach(exercicio -> System.out.println(exercicio.toString()));

        return exerciciosPorGrupo;

        } else{
            System.out.println("\nGrupo não encontrado!");
            return null;
        }      
    }

    public List<Exercicio> listarPorNomeParcial(String nome){

        Pattern nomeParcial = Pattern.compile("^.*" + nome + ".*$", Pattern.CASE_INSENSITIVE);
        Document filtro = new Document("nome", nomeParcial);

        FindIterable<Document> resultado = exercicios.find(filtro);
        List<Exercicio> exerciciosPorNomeParcial = new ArrayList<>();

        if(resultado != null && resultado.iterator().hasNext()){
            for(Document documento: resultado){
                Exercicio exercicio = new Exercicio(
                    documento.getString("nome"),
                    documento.getString("grupoMuscular"),
                    documento.getString("equipamento"));

                    exercicio.setDocument(documento);
            
                exerciciosPorNomeParcial.add(exercicio);
            }

        for (Exercicio exercicio : exerciciosPorNomeParcial) {
            System.out.println(exercicio.toString());
        }

        return exerciciosPorNomeParcial;

        } else{
            System.out.println("\nExercício não encontrado!");
            return null;
        }      
    }

    public Exercicio listarPorNome(String nome){

        Document filtro = new Document("nome", nome.toLowerCase());
        Document resultado = exercicios.find(filtro).first();

        if (resultado != null) {
            Exercicio exercicio =  new Exercicio(
                    resultado.getString("nome"),
                    resultado.getString("grupoMuscular"),
                    resultado.getString("equipamento")
            );

            exercicio.setDocument(resultado);
            System.out.println(exercicio);

            return exercicio;
        } else {
            System.out.println("\nExercício não encontrado!");
            return null;
        }
    }
    

    public void atualizar(String nome){

        Exercicio exercicio = listarPorNome(nome);

        if (exercicio != null) {
        
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o novo nome (ou Enter para manter): ");
            String novoNome = scanner.nextLine();

            System.out.print("Digite o novo grupo muscular (ou Enter para manter): ");
            String novoGrupoMuscular = scanner.nextLine();

            System.out.print("Digite o novo equipamento (ou Enter para manter): ");
            String novoEquipamento = scanner.nextLine();

            List<Bson> atualizacoes = new ArrayList<>();

            if (!novoNome.isEmpty()) {
                atualizacoes.add(Updates.set("nome", novoNome.toLowerCase()));
            }

            if (!novoGrupoMuscular.isEmpty()) {
                atualizacoes.add(Updates.set("grupoMuscular", novoGrupoMuscular));
            }

            if (!novoEquipamento.isEmpty()) {
                atualizacoes.add(Updates.set("equipamento", novoEquipamento));
            }

            if (!atualizacoes.isEmpty()) {
                exercicios.updateOne(exercicio.getDocumento(), Updates.combine(atualizacoes));
                System.out.println("\nExercício atualizado com sucesso!");
            
            } else {
                System.out.println("\n** Nenhum campo atualizado. **");
            }
        }
    }

    public void deletar(String nome){

        Exercicio exercicio = listarPorNome(nome);
        Scanner scanner = new Scanner(System.in);

            if(exercicio != null){

            System.out.print("Tem certeza que deseja deletar o exercício? (S/N) ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
            
                exercicios.deleteOne(exercicio.getDocumento());

                System.out.println("\nExercício excluído com sucesso!");
            } else {
                System.out.println("\n** Exclusão cancelada. **");
            }
        } 
    }
}
