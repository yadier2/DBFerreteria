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


       etcp=findViewById(R.id.etcodigo);
       etdp=findViewById(R.id.etdescripcion1);
       etv=findViewById(R.id.etvalor);


   }
   public void menu(View view){
       Intent Menu = new Intent(this,MainActivity.class);
       startActivity(Menu);
   }


   public void registrar (View view){


       String codigo =etcp.getText().toString();
       String descripcion =etdp.getText().toString();
       String valor =etv.getText().toString();


       if (!codigo.isEmpty() && !descripcion.isEmpty() && !valor.isEmpty()){


           ContentValues registro = new ContentValues();


           registro.put("codigo",codigo);
           registro.put("descripcion",descripcion);
           registro.put("valor",valor);


           dbFereteria.abrirDB();
           dbFereteria.getWritableDatabase().insert("producto",null,registro);
           dbFereteria.cerrarDB();


           etcp.setText("");
           etdp.setText("");
           etv.setText("");




           Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
       }
       else {
           Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
       }
   }


   public void consult (View view){


       String codigo=etcp.getText().toString();


       if(!codigo.isEmpty()){


           try (Cursor filla =
                        dbFereteria.getWritableDatabase().rawQuery("select descripcion,valor from producto where codigo=" + codigo, null)) {


               if (filla.moveToFirst()) {
                   etdp.setText(filla.getString(0));
                   etv.setText(filla.getString(1));
                   dbFereteria.cerrarDB();
               } else {
                   Toast.makeText(this, "No se encontro el PRODUCTO", Toast.LENGTH_LONG).show();
               }
           }
       }else{
           Toast.makeText(this,"NO HAY PRODUCTO",Toast.LENGTH_LONG).show();
       }
   }


   public void actualizar (View view){


       String codigo =etcp.getText().toString();
       String descripcion =etdp.getText().toString();
       String valor =etv.getText().toString();


       if (!codigo.isEmpty() && !descripcion.isEmpty() && !valor.isEmpty()){


           ContentValues registro = new ContentValues();


           registro.put("codigo",codigo);
           registro.put("descripcion",descripcion);
           registro.put("valor",valor);




           int fila=dbFereteria.getWritableDatabase().update("producto",registro,"codigo="+codigo,null);


           if (fila>0){
               Toast.makeText(this,"EL REGISTRO DEL PRODUCTO FUE EXITOSO",Toast.LENGTH_LONG).show();
           }
           else{
               Toast.makeText(this,"EL REGISTRO DEL PRODUCTO NO FUE EXITOSO",Toast.LENGTH_LONG).show();
           }


       }else{
           Toast.makeText(this,"EL REGISTRO DEL PRODUCTO NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
       }
       etcp.setText("");
       etdp.setText("");
       etv.setText("");
   }




   public void eliminar (View view){


       String codigo=etcp.getText().toString();


       if(!codigo.isEmpty()){


           int fila=dbFereteria.getWritableDatabase().delete("producto","codigo="+codigo,null);




           if (fila>0){
               Toast.makeText(this,"EL PRODUCTO FUE ELIMINADO",Toast.LENGTH_LONG).show();
           }
           else{
               Toast.makeText(this,"EL PRODUCTO NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
           }


       }
       etcp.setText("");
       etdp.setText("");
       etv.setText("");
   }
}
