package com.example.pm2e1_0007_0084;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.pm2e1_0007_0084.Settings.SQLite_conecction;
import com.example.pm2e1_0007_0084.Settings.Transactions;

public class Activity_show_image extends AppCompatActivity {
    SQLite_conecction connection;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        image = (ImageView) findViewById(R.id.imageView2);

        connection = new SQLite_conecction(this, Transactions.name_database, null, 2);
        obtenerImagen(getIntent().getStringExtra("id"));
    }

    private void obtenerImagen(String id) {
        SQLiteDatabase db = connection.getReadableDatabase();
        Bitmap bitmap;
        String selectQuery = "SELECT image FROM contacts WHERE id_contact = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
        else{
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        }
        image.setImageBitmap(bitmap);
        cursor.close();
        db.close();
    }

}