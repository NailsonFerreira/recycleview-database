package com.nailson.ceeprecycler.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nailson.ceeprecycler.model.Contato;

import java.util.ArrayList;
import java.util.List;

import static com.nailson.ceeprecycler.model.Contato.*;

public class ContatoDAO extends BasicDAO implements interfaces.BasicDAO<Contato> {

    public static final String TAG = "ContatoDAO";
    private SQLiteDatabase db;

    public ContatoDAO(Context context) {
        super(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER NOT NULL," +
                "%s TEXT NOT NULL," +
                "%s TEXT NOT NULL" +
                ");", TABELA_CONTATOS, ID_PESSOA, TELEFONE, EMAIL);
        db.execSQL(sql);

        Log.i(TAG, sql);
    }

    @Override
    public long insere(Contato contato) {
        db = getWritableDatabase();
        ContentValues values = getContentValues(contato);
        long insert = db.insert(TABELA_CONTATOS, null, values);
        Log.i(TAG, String.format("insere():%s db: %s", contato.toString(), insert));

        return insert;
    }

    @Override
    public int altera(Contato contato) {
        db = getWritableDatabase();
        ContentValues values = getContentValues(contato);
        String where =  ID_PESSOA + " = ?";
        String[] args = new String[]{String.valueOf(contato.getIdPessoa())};
        int update = db.update(TABELA_CONTATOS, values, where, args);
        Log.i(TAG, String.format("update():%s db: %s", contato.toString(), update));

        return update;
    }

    @Override
    public boolean existe(Contato contato) {
        db = getReadableDatabase();
        String existe = String.format("SELECT * %s FROM %s WHERE %s = ? LIMIT 1;", ID_PESSOA, TABELA_CONTATOS, ID_PESSOA);

        Cursor c = db.rawQuery(existe, new String[]{String.valueOf(contato.getIdPessoa())});
        int quantidade = c.getCount();
        Log.i(TAG, String.format("existe():%s db: %s", contato.toString(), quantidade));

        c.close();
        return quantidade > 0;
    }

    @Override
    public int deteta(Contato contato) {
        db = getWritableDatabase();
        String where = ID_PESSOA + " = ?";
        String[] args = new String[]{String.valueOf(contato.getIdPessoa())};
        int delete = db.delete(TABELA_CONTATOS, where, args);
        Log.i(TAG, String.format("delete():%s db: %s", contato.toString(), delete));
        return delete;
    }

    @Override
    public List<Contato> selectAll() {
        db = getReadableDatabase();
        String select = "SELECT * FROM "+ TABELA_CONTATOS+";";
        Cursor c = db.rawQuery(select, null);
        List<Contato> contatoes= new ArrayList<>();
        while (c.moveToNext()){
            Contato contato = getObjectCursor(c);
            contatoes.add(contato);
        }
        c.close();

        Log.i(TAG, String.format("selectAll():%s size: %s", contatoes.toString(), contatoes.size()));
        return contatoes;
    }

    @Override
    public Contato getObjectCursor(Cursor c) {
        Contato contato = new Contato();
        contato.setIdPessoa(c.getInt(c.getColumnIndex(ID_PESSOA)));
        contato.setTelefone(c.getString(c.getColumnIndex(TELEFONE)));
        contato.setEmail(c.getString(c.getColumnIndex(EMAIL)));

        return contato;
    }

    @Override
    public ContentValues getContentValues(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(ID_PESSOA, contato.getIdPessoa());
        values.put(TELEFONE, contato.getTelefone());
        values.put(EMAIL, contato.getEmail());

        return values;
    }

    @Override
    public Contato getObjectById(Contato contato) {
        db = getReadableDatabase();
        String select = "SELECT * FROM "+ TABELA_CONTATOS +
                " WHERE " + ID_PESSOA + " = ? ;";
        String[] args = new String[]{String.valueOf(contato.getIdPessoa())};
        Cursor c = db.rawQuery(select, args);
        Contato contatot = new Contato();
        while (c.moveToNext()){
            contatot = getObjectCursor(c);
            return contatot;
        }
        c.close();

        Log.i(TAG, String.format("getObjectById():%s ", contatot.toString()));
        return contatot;
    }
}
