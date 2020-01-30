package com.nailson.ceeprecycler.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nailson.ceeprecycler.R;
import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.model.Pessoa;
import com.nailson.ceeprecycler.recyclerview.adapter.listener.OnItemClickListener;

import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter <ListaNotasAdapter.NotaViewHolder>{

    private List<Pessoa> pessoas;
    private Context context;
    private PessoaDAO dao;
    private OnItemClickListener onItemClickListener;


    public ListaNotasAdapter(List<Pessoa> pessoas, Context context) {
        this.pessoas = pessoas;
        this.context = context;
        dao = new PessoaDAO(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        viewHolder.vincula(pessoa);
    }


    // Retorna o tamanho da lista
    @Override
    public int getItemCount() {
        return pessoas.size();
    }


    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtTitulo;
        private final TextView txtDescricao;
        private Pessoa pessoa;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.item_nota_titulo);
            txtDescricao = itemView.findViewById(R.id.item_nota_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(pessoa, getAdapterPosition());
                }
            });
        }

        public void vincula(Pessoa pessoa) {
            this.pessoa = pessoa;
            txtTitulo.setText(String.format("%s - ID: %d", pessoa.getNome(), pessoa.getId()));
            txtDescricao.setText(String.valueOf(pessoa.getIdade()));
        }
    }

    public void altera(Pessoa pessoa, int posicao){
        pessoas.set(posicao, pessoa);
        notifyDataSetChanged();
    }

    public void adicionaPessoa(Pessoa pessoa){
        pessoas.add(pessoa);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        pessoas.remove(position);
        notifyDataSetChanged();
    }
}
