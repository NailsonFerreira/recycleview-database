package com.nailson.ceeprecycler.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nailson.ceeprecycler.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

import static com.nailson.ceeprecycler.model.Pessoa.ID;
import static com.nailson.ceeprecycler.model.Pessoa.IDADE;
import static com.nailson.ceeprecycler.model.Pessoa.NOME;
import static com.nailson.ceeprecycler.model.Pessoa.TABELA_PESSOAS;

public class PessoaDAO extends BasicDAO implements com.nailson.ceeprecycler.interfaces.BasicDAO<Pessoa> {


    public static final String TAG = "PessoaDAO";
    private SQLiteDatabase db;

    public PessoaDAO(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY," +
                "%s TEXT NOT NULL," +
                "%S INTEGER NOT NULL" +
                ");", TABELA_PESSOAS, ID, NOME, IDADE);
        db.execSQL(sql);

        Log.i(TAG, sql);

    }


    @Override
    public long insere(Pessoa pessoa)throws SQLiteConstraintException {
        db = getWritableDatabase();
        ContentValues values = getContentValues(pessoa);
        long insert = db.insert(TABELA_PESSOAS, null, values);
        Log.i(TAG, String.format("insere():%s db: %s", pessoa.toString(), insert));

        return insert;
    }

    @Override
    public int altera(Pessoa pessoa) {
        db = getWritableDatabase();
        ContentValues values = getContentValues(pessoa);
        String where =  ID + " = ?";
        String[] args = new String[]{String.valueOf(pessoa.getId())};
        int update = db.update(TABELA_PESSOAS, values, where, args);
        Log.i(TAG, String.format("update():%s db: %s", pessoa.toString(), update));

        return update;
    }

    @Override
    public boolean existe(Pessoa pessoa) {
        db = getReadableDatabase();
        String existe = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABELA_PESSOAS, ID);

        Cursor c = db.rawQuery(existe, new String[]{String.valueOf(pessoa.getId())});
        int quantidade = c.getCount();
        Log.i(TAG, String.format("existe():%s db: %s", pessoa.toString(), quantidade));

        c.close();
        return quantidade > 0;
    }

    @Override
    public int deteta(Pessoa pessoa) {
        db = getWritableDatabase();
        String where = ID + " = ?";
        String[] args = new String[]{String.valueOf(pessoa.getId())};
        int delete = db.delete(TABELA_PESSOAS, where, args);
        Log.i(TAG, String.format("delete():%s db: %s", pessoa.toString(), delete));
        return delete;
    }

    public int deteta(int idPessoa) {
        db = getWritableDatabase();
        String where = ID + " = ?";
        String[] args = new String[]{String.valueOf(idPessoa)};
        int delete = db.delete(TABELA_PESSOAS, where, args);
        Log.i(TAG, String.format("delete():%s db: %s", idPessoa, delete));
        return delete;
    }

    @Override
    public List<Pessoa> selectAll() {
        db = getReadableDatabase();
        String select = "SELECT * FROM "+ TABELA_PESSOAS+";";
        Cursor c = db.rawQuery(select, null);
        List<Pessoa> pessoas = new ArrayList<>();
        while (c.moveToNext()){
            Pessoa p = getObjectCursor(c);
            pessoas.add(p);
        }
        c.close();

        Log.i(TAG, String.format("selectAll():%s size: %s", pessoas.toString(), pessoas.size()));
        return pessoas;
    }

    @Override
    public Pessoa getObjectCursor(Cursor c) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(c.getInt(c.getColumnIndex(ID)));
        pessoa.setNome(c.getString(c.getColumnIndex(NOME)));
        pessoa.setIdade(c.getInt(c.getColumnIndex(IDADE)));

        return pessoa;
    }

    @Override
    public ContentValues getContentValues(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(NOME, pessoa.getNome());
        values.put(IDADE, pessoa.getIdade());

        return values;
    }

    public ContentValues getContentValuesComId(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(ID, pessoa.getId());
        values.put(NOME, pessoa.getNome());
        values.put(IDADE, pessoa.getIdade());

        return values;
    }

    public int alteraComId(Pessoa pessoa) {
        db = getWritableDatabase();
        ContentValues values = getContentValuesComId(pessoa);
        String where =  ID + " = ?";
        String[] args = new String[]{String.valueOf(pessoa.getId())};
        int update = db.update(TABELA_PESSOAS, values, where, args);
        Log.i(TAG, String.format("update():%s db: %s", pessoa.toString(), update));

        return update;
    }

    public void troca(int inicio, int fim){
        Pessoa pessoaInicio = getObjectById(inicio);
        Pessoa pessoaFim = getObjectById(fim);

        if(pessoaInicio!=null && pessoaFim!=null){
            Log.i(TAG, String.format("troca(Inicio %s, Fim %s) pessoainicio: %s pessoaFim: %s",inicio, fim, pessoaInicio.toString(), pessoaFim.toString()));

            pessoaInicio.setId(fim);
            pessoaFim.setId(inicio);

            altera(pessoaInicio);
            altera(pessoaFim);
            Log.i(TAG, String.format("troca() pessoainicio: %s pessoaFim: %s", pessoaInicio.toString(), pessoaFim.toString()));
        }
    }

    @Override
    public Pessoa getObjectById(int id) {
        db = getReadableDatabase();
        String existe = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", TABELA_PESSOAS, ID);

        Cursor c = db.rawQuery(existe, new String[]{String.valueOf(id)});
        while (c.moveToNext()){
            Pessoa pessoa = getObjectCursor(c);
            Log.i(TAG, String.format("getObjectById():%s", pessoa.toString()));
            return pessoa;
        }
        c.close();
        return null;
    }
}
