package com.nailson.ceeprecycler.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public interface BasicDAO<T> {

    long insere(T o);

    int altera(T o);

    boolean existe(T o);

    int deteta(T o);

    List<T> selectAll();

    T getObjectCursor(Cursor c);

    ContentValues getContentValues(T o);

    T getObjectById(int id);
}
