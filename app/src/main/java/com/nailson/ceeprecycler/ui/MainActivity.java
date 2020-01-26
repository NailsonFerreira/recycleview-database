package com.nailson.ceeprecycler.ui;

import android.content.Intent;
import android.os.Bundle;
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

    private BasicDAO pessoaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        pessoaDAO = new PessoaDAO(this);

        List<Pessoa> pessoas = pessoaDAO.selectAll();

        configuraRecyclerView(pessoas);

        TextView txtInsereNota = findViewById(R.id.lista_notas_insere_nota);
        txtInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(i);
            }
        });
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
        listaNotas.setAdapter( new ListaNotasAdapter(pessoas, this));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaNotas.setLayoutManager(manager);
    }
}
