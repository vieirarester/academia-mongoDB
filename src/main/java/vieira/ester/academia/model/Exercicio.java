package vieira.ester.academia.model;

import org.bson.Document;

public class Exercicio {
    private String nome;
    private String grupoMuscular;
    private String equipamento;

    private Document documento;

    public Exercicio(){
    }

    public Exercicio(String nome, String grupoMuscular, String equipamento) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.equipamento = equipamento;
    }

    public Document getDocumento() {
        return documento;
    }

    public void setDocument(Document documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    @Override
    public String toString() {
        return "\n* ID: " + documento.getObjectId("_id") + "\n" +
               "* Nome: " + nome + "\n" +
               "* Grupo Muscular: " + grupoMuscular + "\n" +
               "* Equipamento: " + equipamento + "\n";
    }
}

