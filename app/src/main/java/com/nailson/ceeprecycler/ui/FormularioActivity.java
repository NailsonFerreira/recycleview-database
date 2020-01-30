package com.nailson.ceeprecycler.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.model.Pessoa;

import java.io.Serializable;

import static com.nailson.ceeprecycler.interfaces.Constantes.EXTRA_PESSOA;
import static com.nailson.ceeprecycler.interfaces.Constantes.EXTRA_POSICAO;
import static com.nailson.ceeprecycler.interfaces.Constantes.POSICAO_INVALIDA;
import static com.nailson.ceeprecycler.interfaces.Constantes.RESULT_CODE_PESSOA;

public class FormularioActivity extends AppCompatActivity {

    private final String TAG = "FormularioActivity";
    private EditText txtNome;
    private EditText txtIdade;
    private Pessoa pessoaEdita;
    private int posicao = POSICAO_INVALIDA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        initElements();
        verificaExtras();
    }

    private void verificaExtras() {
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_PESSOA)
        && intent.hasExtra(EXTRA_POSICAO)){
            pessoaEdita = (Pessoa) intent.getSerializableExtra(EXTRA_PESSOA);
            posicao = intent.getIntExtra(EXTRA_POSICAO, POSICAO_INVALIDA);
            txtNome.setText(pessoaEdita.getNome());
            txtIdade.setText(String.valueOf(pessoaEdita.getIdade()));
            Log.i(TAG, String.format("verificaExtras():%s posicao: %s", pessoaEdita.toString(), posicao));

        }
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
        result.putExtra(EXTRA_PESSOA, pessoa);
        result.putExtra(EXTRA_POSICAO, posicao);
        setResult(Activity.RESULT_OK, result);
    }

    private Pessoa getPessoa() {
        String nome = txtNome.getText().toString();
        int idade = Integer.parseInt(txtIdade.getText().toString());

        if (pessoaEdita==null)
            pessoaEdita = new Pessoa();

        pessoaEdita.setIdade(idade);
        pessoaEdita.setNome(nome);
        return pessoaEdita;
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

    private void initElements() {
        txtNome = findViewById(R.id.formulario_nome);
        txtIdade = findViewById(R.id.formulario_idade);
    }
}
