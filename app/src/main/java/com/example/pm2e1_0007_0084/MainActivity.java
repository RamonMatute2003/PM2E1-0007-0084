package com.example.pm2e1_0007_0084;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm2e1_0007_0084.Models.Contacts;
import com.example.pm2e1_0007_0084.Models.Countries;
import com.example.pm2e1_0007_0084.Models.Countries_data;
import com.example.pm2e1_0007_0084.Settings.SQLite_conecction;
import com.example.pm2e1_0007_0084.Settings.Transactions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner sp_countries;
    Button btn_save_contact;
    Button btn_saved_contacts;
    EditText txt_name,txt_phone,txt_note;

    static final int peticion_captura_imagen = 101;
    static final int peticion_acceso_camara = 102;
    String currentPhotoPath;
    ImageView Objetoimagen;
    Button btntomarfoto;
    String pathfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objetoimagen = (ImageView) findViewById(R.id.imageView);
        btntomarfoto = (Button) findViewById(R.id.btnfoto);

        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
            }
        });

        sp_countries=findViewById(R.id.cb_country);
        btn_save_contact=findViewById(R.id.btn_save_contact);
        btn_saved_contacts=findViewById(R.id.btn_saved_contacts);
        txt_name=findViewById(R.id.txt_name);
        txt_note=findViewById(R.id.txt_note);
        txt_phone=findViewById(R.id.txt_phone);


        btn_saved_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_list_view.class);
                startActivity(intent);
            }
        });

        List<Countries_data> countries_data=fill_countries();
        ArrayAdapter<Countries_data> arrayAdapter=new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, countries_data);
        sp_countries.setAdapter(arrayAdapter);

        btn_save_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()==true){
                    AgregarContacto();
                    ClearScreen();
                }
            }
        });

        /*sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int id_1=((Countries_data) parent.getSelectedItem()).getId();

                Toast.makeText(getApplicationContext(), "Id: "+id_1, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private boolean validate(){
        if(txt_name.getText().toString().isEmpty()){
            validate_message("nombre");
            return false;
        }else{
            if(txt_phone.getText().toString().isEmpty()){
                validate_message("telefeno");
                return false;
            }else{
                if(txt_note.getText().toString().isEmpty()){
                    validate_message("nota");
                    return false;
                }else{
                    return true;
                }
            }
        }
    }

    private void validate_message(String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Debe escribir un "+message);
        builder.setTitle("Alerta");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @SuppressLint("Range")
    private List<Countries_data> fill_countries(){//fill_countries=llenar paises
        List<Countries_data> list_countries_data=new ArrayList<>();
        Countries db_countries=new Countries(MainActivity.this, "db_contactos.db", null, 1);
        Cursor cursor=db_countries.show_countries();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Countries_data country_data=new Countries_data();
                    country_data.setId(cursor.getInt(cursor.getColumnIndex("id_country")));
                    country_data.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                    list_countries_data.add(country_data);
                }while(cursor.moveToNext());
            }
        }
        db_countries.close();

        return list_countries_data;
    }

    private void AgregarContacto() {
        SQLite_conecction conexion = new SQLite_conecction(   this, Transactions.name_database, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(Transactions.name ,txt_name.getText().toString());
        valores.put(Transactions.phone, txt_phone.getText().toString());
        valores.put(Transactions.note, txt_note.getText().toString());
        //valores.put(Transactions.photo, .getText().toString());


        Long resultado = db.insert(Transactions.table_contacts, Transactions.id, valores);
        Toast.makeText(getApplicationContext(), "Registro Ingresado:" + resultado.toString(),Toast.LENGTH_LONG).show();

        db.close();

    }
    private void ClearScreen()
    {
        txt_name.setText("");
        txt_phone.setText("");
        txt_note.setText("");
    }

    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},peticion_acceso_camara);
        }
        else
        {
            dispatchTakePictureIntent();
            //TomarFoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara )
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            {
                dispatchTakePictureIntent();
                //TomarFoto();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Se necesita el permiso para accder a la camara", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void TomarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intent, peticion_captura_imagen );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  == peticion_captura_imagen)
        {
            /*
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");
            Objetoimagen.setImageBitmap(imagen);
             */

            try {
                File foto = new File(currentPhotoPath);
                Objetoimagen.setImageURI(Uri.fromFile(foto));
            }
            catch (Exception ex)
            {
                ex.toString();
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pm2e1_0007_0084.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, peticion_captura_imagen);
            }
        }
    }

}