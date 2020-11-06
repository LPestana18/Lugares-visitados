package br.com.lucaspestana.lugares_visitados.Classes;

public class Place {
    private String nome;
    private String descricao;

    public Place() {
        // Default constructor required for calls to DataSnapshot.getValue(Lugar.class)
    }

    public Place(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
