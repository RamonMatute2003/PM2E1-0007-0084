package com.example.pm2e1_0007_0084.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_conecction extends SQLiteOpenHelper {
    public SQLite_conecction(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Transactions.create_table_countries);
        db.execSQL(Transactions.create_table_contacts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Transactions.drop_table_contacts);
        db.execSQL(Transactions.drop_table_countries);
        onCreate(db);
    }
}
