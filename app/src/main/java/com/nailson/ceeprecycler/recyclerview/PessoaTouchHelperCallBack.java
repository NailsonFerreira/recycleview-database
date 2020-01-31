package com.nailson.ceeprecycler.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

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
        int marcadorArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(marcadorArrastar, marcadorDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        int positionIni = viewHolder.getAdapterPosition();
        int positionFim = viewHolder1.getAdapterPosition();
        new PessoaDAO(context).troca(positionIni+1, positionFim+1);
        adapter.troca(positionIni, positionFim);

        return false;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        new PessoaDAO(context).deteta(position+1);
        adapter.remove(position);

    }
}
