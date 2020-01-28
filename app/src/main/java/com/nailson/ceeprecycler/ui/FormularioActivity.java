package com.nailson.ceeprecycler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    }

    private void salvar(){
        try {
            Pessoa pessoa = getPessoa();
            retornaResult(pessoa);
            finish();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(FormularioActivity.this, "Ocorreu um erro "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void retornaResult(Pessoa pessoa) {
        Intent result = new Intent();
        result.putExtra(getResources().getString(R.string.extra_pessoa), pessoa);
        setResult(2, result);
    }

    private Pessoa getPessoa() {
        String nome = txtNome.getText().toString();
        int idade = Integer.parseInt(txtIdade.getText().toString());
        return new Pessoa(nome, idade);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isMenuSalvar(item)){
            salvar();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isMenuSalvar(MenuItem item) {
        return item.getItemId()== R.id.menu_formulario_salva;
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
