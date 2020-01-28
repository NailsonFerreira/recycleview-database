package com.nailson.ceeprecycler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.dao.BasicDAO;
import com.nailson.ceeprecycler.dao.NotaDAO;
import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.model.Nota;
import com.nailson.ceeprecycler.model.Pessoa;
import com.nailson.ceeprecycler.recyclerview.ListaNotasAdapter;
import com.nailson.ceeprecycler.ui.FormularioActivity;

import java.util.Calendar;
import java.util.List;

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
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==1 && resultCode==2 && data.hasExtra(getResources().getString(R.string.extra_pessoa))){
            Pessoa pessoa = (Pessoa) data.getSerializableExtra(getResources().getString(R.string.extra_pessoa));
            pessoaDAO.insere(pessoa);
            adapter.adicionaPessoa(pessoa);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<Nota> getNotasExemplo() {
        NotaDAO dao = new NotaDAO();
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i<1000; i++) {
            dao.insere(new Nota("Titulo "+i, calendar.getTime().toString()));
        }
        return dao.todos();
    }

    private void configuraRecyclerView(List<Pessoa> pessoas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycler_view);
        adapter = new ListaNotasAdapter(pessoas, this);
        listaNotas.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaNotas.setLayoutManager(manager);
    }
}
