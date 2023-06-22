package com.example.pm2e1_0007_0084;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm2e1_0007_0084.Models.Contacts;
import com.example.pm2e1_0007_0084.Settings.SQLite_conecction;
import com.example.pm2e1_0007_0084.Settings.Transactions;

import java.util.ArrayList;

public class Activity_list_view extends AppCompatActivity {
 SQLite_conecction conexion;

 ListView listacontactos;

 EditText id;

 private Button btnllamar;

ArrayList<Contacts> lista;

ArrayList<String> ArregloContactos;

private String telefono;

private static final int REQUEST_CALL = 1;

private Boolean Selected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        conexion= new SQLite_conecction(this, Transactions.name_database, null, 1);
        listacontactos= (ListView) findViewById(R.id.listaContactos);
        id = (EditText) findViewById(R.id.txtcid);

        Button btnregresar = (Button)findViewById(R.id.btnregresar);
        Button btneliminar = (Button)findViewById(R.id.btneliminar);
        Button btnactualizar = (Button)findViewById(R.id.btnactualizar);

        btnllamar = (Button)findViewById(R.id.btnllamar);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actualizar();

            }
        });

        ObtenerListaContactos();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, ArregloContactos);
        listacontactos.setAdapter(adp);


        listacontactos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick (AdapterView<?> sele1, View selec2, int posicion, long select3){

                telefono = "+"+lista.get(posicion).getPhone();
                Selected = true;


                btneliminar.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        selec2.setSelected(true);
                        SQLiteDatabase db = conexion.getWritableDatabase();
                        String sql = "DELETE FROM contactos WHERE id="+lista.get(posicion).getCountry();
                        db.execSQL(sql);
                        Intent i = new Intent(Activity_list_view.this, Activity_list_view.class);
                        startActivity(i);
                        finish();
                    }
                });

                btnllamar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selec2.setSelected(true);

                        AlertDialog.Builder builder= new AlertDialog.Builder(Activity_list_view.this);
                        builder.setMessage("¿Quiere realizar una llamada?");
                        builder.setTitle("LLAMADA");

                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                mostrarnumero();

                                /*Intent t = new Intent(Intent.ACTION_CALL, Uri.parse("tel:96603565"));
                                if (ActivityCompat.checkSelfPermission(ActivityListView.this, Manifest.permission.CALL_PHONE)!=
                                        PackageManager.PERMISSION_GRANTED)
                                    return;
                                startActivity(t);*/

                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Activity_list_view.this,"LLamada no realizada", Toast.LENGTH_LONG).show();

                            }
                        });

                        AlertDialog dialog= builder.create();
                        dialog.show();
                    }
                });


            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void ObtenerListaContactos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contacts listContactos= null;
        lista = new ArrayList<Contacts>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transactions.table_contacts, null);

        while (cursor.moveToNext()){
            listContactos = new Contacts();
            listContactos.setCountry(cursor.getString(0));
            listContactos.setName(cursor.getString(2));
            listContactos.setPhone(cursor.getInt(3));

            lista.add(listContactos);

        }
        cursor.close();
        fillList();

    }
    private void fillList() {

        ArregloContactos = new ArrayList<String>();

        for (int i = 0;  i < lista.size(); i++){

            ArregloContactos.add(lista.get(i).getCountry() + " | "
                    +lista.get(i).getName() + " | "
                    +lista.get(i).getPhone());

        }
    }

    private void mostrarnumero() {
        String numero = telefono;
        if (Selected==true){
            if(ContextCompat.checkSelfPermission(Activity_list_view.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Activity_list_view.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String n = "tel:" + numero;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(n)));
            }
        }

        else{
            Toast.makeText(Activity_list_view.this, "Seleccione Un Contacto", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mostrarnumero();
            }else{
                Toast.makeText(this, "NO TIENE ACCESO", Toast.LENGTH_SHORT).show();
            }
        }
    }

}