package com.moviles.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import OpenHelper.SqLiteOpenHelper;

public class ClienteActivity extends AppCompatActivity {
    private EditText etCedula,etNombre,etDireccion,etTelefono;

    SqLiteOpenHelper dbFereteria  =  new SqLiteOpenHelper(this,"dbFereteria",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        etCedula=findViewById(R.id.etcedula);
        etNombre=findViewById(R.id.etnombre);
        etDireccion=findViewById(R.id.etdireccion);
        etTelefono=findViewById(R.id.ettelefono);

    }
    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);

    }


    public void registrar (View view){

        String cedula =etCedula.getText().toString();
        String nombre =etNombre.getText().toString();
        String direccion =etDireccion.getText().toString();
        String telefono =etTelefono.getText().toString();

        if (!cedula.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()){

            ContentValues cliente = new ContentValues();

            cliente.put("cedula",cedula);
            cliente.put("nombre",nombre);
            cliente.put("direccion",direccion);
            cliente.put("telefono",telefono);

            dbFereteria.abrirDB();
            dbFereteria.getWritableDatabase().insert("cliente",null ,cliente);
            dbFereteria.cerrarDB();

            etDireccion.setText("");
            etTelefono.setText("");
            etNombre.setText("");
            etCedula.setText("");

            Toast.makeText(this,"Cliente registrado correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese todos los datos del cliente",Toast.LENGTH_LONG).show();
        }


    }

    public void consultar (View view) {

        String cedula = etCedula.getText().toString();

        if (!cedula.isEmpty()) {
            try (Cursor fila = dbFereteria.getReadableDatabase()
                    .rawQuery("select nombre,direccion,telefono from cliente where cedula=" + cedula, null)) {

                if (fila.moveToFirst()) {
                    etNombre.setText(fila.getString(0));
                    etDireccion.setText(fila.getString(1));
                    etTelefono.setText(fila.getString(2));
                    dbFereteria.cerrarDB();

                } else {
                    Toast.makeText(this, "No se encontro el cliente: " + cedula, Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"Debes ingresar la cedula del cliente",Toast.LENGTH_LONG).show();
        }


    }


    public void actualizar (View view){

        String cedula =etCedula.getText().toString();
        String nombre =etNombre.getText().toString();
        String direccion =etDireccion.getText().toString();
        String telefono =etTelefono.getText().toString();

        if (!cedula.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()){

            ContentValues cliente = new ContentValues();

            cliente.put("cedula",cedula);
            cliente.put("nombre",nombre);
            cliente.put("direccion",direccion);
            cliente.put("telefono",telefono);
            dbFereteria.abrirDB();
            int fila = dbFereteria.getWritableDatabase().update("cliente",cliente ,"cedula="+cedula,null);
            dbFereteria.cerrarDB();

            if (fila>0){
                Toast.makeText(this,"Se ha actualizado el cliente",Toast.LENGTH_LONG).show();
                etDireccion.setText("");
                etTelefono.setText("");
                etNombre.setText("");
                etCedula.setText("");
            }
            else{
                Toast.makeText(this,"Ocurrio un error al intentar actualizar",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"Debes ingresar los datos del cliente",Toast.LENGTH_LONG).show();
        }

    }


    public void eliminar (View view){

        String cedula=etCedula.getText().toString();

        if(!cedula.isEmpty()){

            dbFereteria.abrirDB();
            int fila = dbFereteria.getWritableDatabase().delete("cliente","cedula="+cedula,null);
            dbFereteria.cerrarDB();
            if (fila>0){
                Toast.makeText(this,"El cliente fue eliminado",Toast.LENGTH_LONG).show();
                etDireccion.setText("");
                etTelefono.setText("");
                etNombre.setText("");
                etCedula.setText("");
            }
            else{
                Toast.makeText(this,"No se encontro el cliente",Toast.LENGTH_LONG).show();
            }

        }

    }

}