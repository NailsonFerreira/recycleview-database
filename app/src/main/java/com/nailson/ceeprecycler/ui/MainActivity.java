package com.nailson.ceeprecycler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.model.Pessoa;
import com.nailson.ceeprecycler.recyclerview.adapter.ListaNotasAdapter;
import com.nailson.ceeprecycler.recyclerview.adapter.listener.OnItemClickListener;

import java.util.List;

import static com.nailson.ceeprecycler.interfaces.Constantes.REQUEST_CODE_PESSOA;
import static com.nailson.ceeprecycler.interfaces.Constantes.RESULT_CODE_PESSOA;

public class MainActivity extends AppCompatActivity {

    private PessoaDAO pessoaDAO;
    private List<Pessoa> pessoas;
    private ListaNotasAdapter adapter;

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
                Intent i = new Intent(MainActivity.this, FormularioActivity.class);
                startActivityForResult(i, REQUEST_CODE_PESSOA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(isRequestCode(requestCode) && isResultCode(resultCode) && data.hasExtra(getResources().getString(R.string.extra_pessoa))){
            Pessoa pessoa = (Pessoa) data.getSerializableExtra(getResources().getString(R.string.extra_pessoa));
            long insere = pessoaDAO.insere(pessoa);
            pessoa.setId((int) insere);
            adapter.adicionaPessoa(pessoa);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isResultCode(int resultCode) {
        return resultCode==RESULT_CODE_PESSOA;
    }

    private boolean isRequestCode(int requestCode) {
        return requestCode==REQUEST_CODE_PESSOA;
    }

    private void configuraRecyclerView(List<Pessoa> pessoas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycler_view);
        configuraAdapter(pessoas, listaNotas);
    }

    private void configuraAdapter(List<Pessoa> pessoas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(pessoas, this);
        listaNotas.setAdapter(adapter);
        //Listener criado manualmente
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaNotas.setLayoutManager(manager);
    }
}
