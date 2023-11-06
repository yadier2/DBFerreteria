package com.moviles.ferreteriadb;


import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import OpenHelper.SqLiteOpenHelper;

public class ProductoActivity extends AppCompatActivity {
    SqLiteOpenHelper dbFereteria  =  new SqLiteOpenHelper(this,"dbFereteria",null,1);

    private EditText etcp,etdp,etv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
    }

}