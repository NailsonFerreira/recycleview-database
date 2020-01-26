package com.nailson.ceeprecycler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.model.Pessoa;

public class FormularioActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtIdade;
    private PessoaDAO pessoaDAO;
    private Button btnAdicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        initElements();
        initListeners();
    }

    private void initListeners() {
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nome = txtNome.getText().toString();
                    int idade = Integer.parseInt(txtIdade.getText().toString());
                    pessoaDAO.insere(new Pessoa(nome, idade));
                    irParaMain();

                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(FormularioActivity.this, "Ocorreu um erro "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void irParaMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void initElements() {
        pessoaDAO = new PessoaDAO(this);
        txtNome = findViewById(R.id.formulario_nome);
        txtIdade = findViewById(R.id.formulario_idade);
        btnAdicionar = findViewById(R.id.button_adicionar);
    }
}
