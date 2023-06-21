package com.example.pm2e1_0007_0084;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pm2e1_0007_0084.Models.Contacts;
import com.example.pm2e1_0007_0084.Models.Countries;
import com.example.pm2e1_0007_0084.Models.Countries_data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner sp_countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp_countries=findViewById(R.id.cb_country);

        List<Countries_data> countries_data=fill_countries();
        ArrayAdapter<Countries_data> arrayAdapter=new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, countries_data);

        sp_countries.setAdapter(arrayAdapter);
    }

    private List<Countries_data> fill_countries(){//fill_countries=llenar paises
        List<Countries_data> list_countries_data=new ArrayList<>();
        Countries db_countries=new Countries(MainActivity.this, "db_contactos.db", null, 1);
        Cursor cursor=db_countries.show_countries();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Countries_data country_data=new Countries_data();
                    //country_data.setId(cursor.getInt(cursor.getColumnIndex("id_country")));
                    country_data.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                    list_countries_data.add(country_data);
                }while(cursor.moveToNext());
            }
        }
        db_countries.close();

        return list_countries_data;
    }
}