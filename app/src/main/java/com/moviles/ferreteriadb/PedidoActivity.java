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

public class PedidoActivity extends AppCompatActivity {
    SqLiteOpenHelper dbFereteria  =  new SqLiteOpenHelper(this,"dbFereteria",null,1);

    private EditText etcd,etd,etf,etcn,etnitU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        etcd=findViewById(R.id.etCodigo);
        etd=findViewById(R.id.etdescripcion);
        etf=findViewById(R.id.etfecha);
        etcn=findViewById(R.id.etcantidad);
        etnitU=findViewById(R.id.etNitUsuario);


    }
    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);
    }
    public void registrar (View view){

        String codigo =etcd.getText().toString();
        String nitUsuario =etnitU.getText().toString();
        String descripcion =etd.getText().toString();
        String fecha =etf.getText().toString();
        String cantidad =etcn.getText().toString();

        if (!codigo.isEmpty() && !nitUsuario.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !cantidad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("nit_usuario",nitUsuario);
            registro.put("descripcion",descripcion);
            registro.put("fecha",fecha);
            registro.put("cantidad",cantidad);

            dbFereteria.abrirDB();
            dbFereteria.getWritableDatabase().insert("pedido",null,registro);
            dbFereteria.cerrarDB();

            etcn.setText("");
            etnitU.setText("");
            etf.setText("");
            etd.setText("");
            etcd.setText("");


            Toast.makeText(this,"Registro guardado correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public void consultaa (View view){

        String codigo1=etcd.getText().toString();

        if(!codigo1.isEmpty()){

            try (Cursor fill =  dbFereteria.getWritableDatabase().rawQuery("select nit_usuario,descripcion,fecha,cantidad from pedido where codigo=" + codigo1, null)) {

                if (fill.moveToFirst()) {
                    etnitU.setText(fill.getString(0));
                    etd.setText(fill.getString(1));
                    etf.setText(fill.getString(2));
                    etcn.setText(fill.getString(3));
                    dbFereteria.cerrarDB();
                } else {
                    Toast.makeText(this, "No se encontro el PEDIDO", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"NO HAY PEDIDO",Toast.LENGTH_LONG).show();
        }
    }
    public void actualizar (View view){

        String codigo =etcd.getText().toString();
        String nitUsuario =etnitU.getText().toString();
        String descripcion =etd.getText().toString();
        String fecha =etf.getText().toString();
        String cantidad =etcn.getText().toString();

        if (!codigo.isEmpty() && !nitUsuario.isEmpty()  && !descripcion.isEmpty() && !fecha.isEmpty() && !cantidad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("nit_usuario",nitUsuario);
            registro.put("descripcion",descripcion);
            registro.put("fecha",fecha);
            registro.put("cantidad",cantidad);

            int fila= dbFereteria.getWritableDatabase().update("pedido",registro,"codigo="+codigo,null);

            if (fila>0){
                Toast.makeText(this,"EL REGISTRO DEL PEDIDO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL REGISTRO DEL PEDIDO NO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"EL REGISTRO DEL PEDIDO NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
        }
        etcn.setText("");
        etnitU.setText("");
        etf.setText("");
        etd.setText("");
        etcd.setText("");
    }


    public void eliminar (View view){

        String codigo=etcd.getText().toString();

        if(!codigo.isEmpty()){

            int fila= dbFereteria.getWritableDatabase().delete("pedido","codigo="+codigo,null);


            if (fila>0){
                Toast.makeText(this,"EL PEDIDO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL PEDIDO NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etcn.setText("");
        etnitU.setText("");
        etf.setText("");
        etd.setText("");
        etcd.setText("");
    }
}