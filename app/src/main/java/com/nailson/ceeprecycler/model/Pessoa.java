package com.nailson.ceeprecycler.model;

public class Pessoa {


    public static final String TABELA_PESSOAS = "PESSOAS";
    public static final String ID = "ID";
    public static final String NOME = "NOME";
    public static final String IDADE = "IDADE";

    private int id;
    private String nome;
    private int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public Pessoa(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return String.format("%s:{" +
                "\"id\":\"%s\"," +
                "\"nome\":\"%s\"," +
                "\"idade\":\"%s\"" +
                "}",TABELA_PESSOAS, id, nome, idade);
    }
}
