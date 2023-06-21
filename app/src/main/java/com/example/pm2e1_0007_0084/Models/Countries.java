package com.example.pm2e1_0007_0084.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.pm2e1_0007_0084.MainActivity;
import com.example.pm2e1_0007_0084.Settings.SQLite_conecction;
import com.example.pm2e1_0007_0084.Settings.Transactions;

public class Countries extends SQLite_conecction {

    public Countries(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Cursor show_countries(){//show_countries=mostrar paises
        try{
            SQLiteDatabase bd=this.getReadableDatabase();
            Cursor rows=bd.rawQuery(Transactions.select_table_countries, null);//rows=filas

            if(rows.moveToFirst()){
                return rows;
            }else{
                return null;
            }

        }catch(Exception e){
            return null;
        }
    }
}
