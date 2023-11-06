package com.example.ferreteriabd;

import androidx.appcompat.app.AppCompatActivity;



import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class        PedidoActivity extends AppCompatActivity {

    private EditText etcd,etd,etf,etcn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        etcd=findViewById(R.id.etCodigo);
        etd=findViewById(R.id.etdescripcion);
        etf=findViewById(R.id.etfecha);
        etcn=findViewById(R.id.etcantidad);

    }
    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);
    }
    public void registrar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo =etcd.getText().toString();
        String descripcion =etd.getText().toString();
        String fecha =etf.getText().toString();
        String cantidad =etcn.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !cantidad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("fecha",fecha);
            registro.put("cantidad",cantidad);

            BaseDatos.insert("pedido",null,registro);
            BaseDatos.close();

            etcn.setText("");
            etf.setText("");
            etd.setText("");
            etcd.setText("");


            Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public void consultaa (View view){

        AdminBD admin=new AdminBD(PedidoActivity.this,"BaseDatos",null,1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo1=etcd.getText().toString();

        if(!codigo1.isEmpty()){

            try (Cursor fill = BaseDatos.rawQuery("select descripcion,fecha,cantidad from pedido where codigo=" + codigo1, null)) {

                if (fill.moveToFirst()) {
                    etd.setText(fill.getString(0));
                    etf.setText(fill.getString(1));
                    etcn.setText(fill.getString(2));
                    BaseDatos.close();
                } else {
                    Toast.makeText(this, "No se encontro el PEDIDO", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"NO HAY PEDIDO",Toast.LENGTH_LONG).show();
        }
    }
    public void actualizar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo =etcd.getText().toString();
        String descripcion =etd.getText().toString();
        String fecha =etf.getText().toString();
        String cantidad =etcn.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !cantidad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("fecha",fecha);
            registro.put("cantidad",cantidad);

            int fila=BaseDatos.update("pedido",registro,"codigo="+codigo,null);

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
        etf.setText("");
        etd.setText("");
        etcd.setText("");
    }


    public void eliminar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,1);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();
        String codigo=etcd.getText().toString();

        if(!codigo.isEmpty()){

            int fila=BaseDatos.delete("pedido","codigo="+codigo,null);


            if (fila>0){
                Toast.makeText(this,"EL PEDIDO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL PEDIDO NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etcn.setText("");
        etf.setText("");
        etd.setText("");
        etcd.setText("");
    }
}