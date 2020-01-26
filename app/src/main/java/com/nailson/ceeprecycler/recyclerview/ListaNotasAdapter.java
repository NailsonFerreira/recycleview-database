package com.nailson.ceeprecycler.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.model.Pessoa;

import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter <ListaNotasAdapter.NotaViewHolder>{

    private List<Pessoa> pessoas;
    private Context context;

    public ListaNotasAdapter(List<Pessoa> pessoas, Context context) {
        this.pessoas = pessoas;
        this.context = context;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false);

        return new NotaViewHolder(view);
    }

    // Faz o bing da informação com a tela
    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder viewHolder, int i) {
        Pessoa pessoa = pessoas.get(i);
        NotaViewHolder notaViewHolder = viewHolder;
        notaViewHolder.vincula(pessoa);


    }

    // Retorna o tamanho da lista
    @Override
    public int getItemCount() {
        return pessoas.size();
    }



    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtTitulo;
        private final TextView txtDescricao;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.item_nota_titulo);
            txtDescricao = itemView.findViewById(R.id.item_nota_descricao);
        }

        public void vincula(Pessoa pessoa) {
            txtTitulo.setText(String.format("%s - ID: %d", pessoa.getNome(), pessoa.getId()));
            txtDescricao.setText(String.valueOf(pessoa.getIdade()));
        }
    }
}