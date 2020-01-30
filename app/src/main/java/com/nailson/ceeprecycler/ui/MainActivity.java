package com.nailson.ceeprecycler.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.model.Pessoa;
import com.nailson.ceeprecycler.recyclerview.PessoaTouchHelperCallBack;
import com.nailson.ceeprecycler.recyclerview.adapter.ListaNotasAdapter;
import com.nailson.ceeprecycler.recyclerview.adapter.listener.OnItemClickListener;

import java.util.List;

import static com.nailson.ceeprecycler.interfaces.Constantes.EXTRA_PESSOA;
import static com.nailson.ceeprecycler.interfaces.Constantes.EXTRA_POSICAO;
import static com.nailson.ceeprecycler.interfaces.Constantes.POSICAO_INVALIDA;
import static com.nailson.ceeprecycler.interfaces.Constantes.REQUEST_CODE_PESSOA;
import static com.nailson.ceeprecycler.interfaces.Constantes.REQUEST_CODE_PESSOA_EDITA;
import static com.nailson.ceeprecycler.interfaces.Constantes.RESULT_CODE_PESSOA;

public class MainActivity extends AppCompatActivity {

    private PessoaDAO pessoaDAO;
    private List<Pessoa> pessoas;
    private ListaNotasAdapter adapter;
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        pessoaDAO = new PessoaDAO(this);

        pessoas = pessoaDAO.selectAll();

        configuraRecyclerView(pessoas);

        TextView txtInsereNota = findViewById(R.id.lista_notas_insere_nota);
        txtInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaFormularioAdiciona();
            }
        });
    }

    private void irParaFormularioAdiciona() {
        Intent i = new Intent(MainActivity.this, FormularioActivity.class);
        startActivityForResult(i, REQUEST_CODE_PESSOA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(isRequestCode(requestCode) && isResultCode(resultCode) && data.hasExtra(EXTRA_PESSOA)){
            Pessoa pessoa = (Pessoa) data.getSerializableExtra(EXTRA_PESSOA);
            long insere = pessoaDAO.insere(pessoa);
            pessoa.setId((int) insere);
            adapter.adicionaPessoa(pessoa);
        }

        if(requestCode == 2 && isResultCode(resultCode)
                && data.hasExtra(EXTRA_PESSOA)
        && data.hasExtra(EXTRA_POSICAO)){
            posicao = data.getIntExtra(EXTRA_POSICAO, POSICAO_INVALIDA);
            Pessoa pessoa = (Pessoa) data.getSerializableExtra(EXTRA_PESSOA);
            if (pessoaDAO.existe(pessoa)){
                pessoaDAO.altera(pessoa);
                adapter.altera(pessoa, posicao);
            } else {
                long insere = pessoaDAO.insere(pessoa);
                pessoa.setId((int) insere);
                adapter.adicionaPessoa(pessoa);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isResultCode(int resultCode) {
        return resultCode== Activity.RESULT_OK;
    }

    private boolean isRequestCode(int requestCode) {
        return requestCode==REQUEST_CODE_PESSOA;
    }

    private void configuraRecyclerView(List<Pessoa> pessoas) {
        RecyclerView listaPessoas = findViewById(R.id.lista_notas_recycler_view);
        configuraAdapter(pessoas, listaPessoas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PessoaTouchHelperCallBack(this, adapter));
        itemTouchHelper.attachToRecyclerView(listaPessoas);
    }

    private void configuraAdapter(List<Pessoa> pessoas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(pessoas, this);
        listaNotas.setAdapter(adapter);
        //Listener criado manualmente
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Pessoa pessoa, int position) {
                irParaFormularioEdita(pessoa, position);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaNotas.setLayoutManager(manager);
    }

    private void irParaFormularioEdita(Pessoa pessoa, int position) {
        Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
        intent.putExtra(EXTRA_PESSOA, pessoa);
        intent.putExtra(EXTRA_POSICAO, position);
        startActivityForResult(intent, REQUEST_CODE_PESSOA_EDITA);
    }
}
