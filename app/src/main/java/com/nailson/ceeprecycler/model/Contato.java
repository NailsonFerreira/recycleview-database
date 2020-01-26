package com.nailson.ceeprecycler.model;

public class Contato {

    public static final String TABELA_CONTATOS = "CONTATOS";
    public static final String ID_PESSOA = "ID_PESSOA";
    public static final String TELEFONE = "TELEFONE";
    public static final String EMAIL = "EMAIL";

    private int idPessoa;
    private String telefone;
    private String email;

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s:{" +
                "\"idPessoa\":\"%s\"," +
                "\"telefone\":\"%s\"," +
                "\"email\":\"%s\"" +
                "}",TABELA_CONTATOS, idPessoa, telefone, email);
    }
}
