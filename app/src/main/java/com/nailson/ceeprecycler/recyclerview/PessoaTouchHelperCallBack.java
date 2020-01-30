package com.nailson.ceeprecycler.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.nailson.ceeprecycler.dao.PessoaDAO;
import com.nailson.ceeprecycler.recyclerview.adapter.ListaNotasAdapter;
import com.nailson.ceeprecycler.ui.MainActivity;

/**
 * Created by nailson.ferreira on 30/01/2020.
 */

public class PessoaTouchHelperCallBack extends ItemTouchHelper.Callback {
    private Context context;
    private ListaNotasAdapter adapter;

    public PessoaTouchHelperCallBack(Context context, ListaNotasAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcadorDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, marcadorDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        new PessoaDAO(context).deteta(position+1);
        adapter.remove(position);

    }
}
