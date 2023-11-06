package com.moviles.ferreteriadb;


import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import OpenHelper.SqLiteOpenHelper;


public class FacturaActivity extends AppCompatActivity {
   SqLiteOpenHelper dbFereteria  =  new SqLiteOpenHelper(this,"dbFereteria",null,1);


   private EditText etn,etf,ett,etCodp;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_factura);

       etn=findViewById(R.id.etfactura);
       etf=findViewById(R.id.etfecha1);
       ett=findViewById(R.id.ettotal);
       etCodp=findViewById(R.id.etCodigoPedido);
   }
   public void menu(View view){
       Intent Menu = new Intent(this,MainActivity.class);
       startActivity(Menu);
   }


   public void registrar (View view){

       String numero =etn.getText().toString();
       String fecha =etf.getText().toString();
       String total =ett.getText().toString();
       String codPedido = etCodp.getText().toString();


       if (!numero.isEmpty() && !fecha.isEmpty() && !total.isEmpty()){
           ContentValues registro = new ContentValues();
           registro.put("numero",numero);
           registro.put("id_pedido",codPedido);
           registro.put("fecha",fecha);
           registro.put("total",total);

           dbFereteria.abrirDB();
           dbFereteria.getWritableDatabase().insert("factura",null ,registro);
           dbFereteria.cerrarDB();


           etn.setText("");
           etn.setText("");
           etf.setText("");
           etCodp.setText("");




           Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
       }
       else {
           Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
       }
   }


   public void cosultar (View view){


       String numero=etn.getText().toString();


       if(!numero.isEmpty()){


           try (Cursor fila =  dbFereteria.getWritableDatabase().rawQuery("select id_pedido,fecha,total from factura where numero=" + numero, null)) {


               if (fila.moveToFirst()) {
                   etCodp.setText(fila.getString(0));
                   etf.setText(fila.getString(1));
                   ett.setText(fila.getString(2));
                   dbFereteria.cerrarDB();
               } else {
                   Toast.makeText(this, "No se encontro la factura", Toast.LENGTH_LONG).show();
               }
           }
       }else{
           Toast.makeText(this,"NO HAY FACTURA",Toast.LENGTH_LONG).show();
       }
   }


   public void actualizar (View view){


       String numero =etn.getText().toString();
       String codPedido= etCodp.getText().toString();
       String fecha =etf.getText().toString();
       String total =ett.getText().toString();


       if (!numero.isEmpty() && !fecha.isEmpty() && !total.isEmpty()){


           ContentValues registro = new ContentValues();


           registro.put("numero",numero);
           registro.put("id_pedido",codPedido);
           registro.put("fecha",fecha);
           registro.put("total",total);


           int fila= dbFereteria.getWritableDatabase().update("factura",registro,"numero="+numero,null);


           if (fila>0){
               Toast.makeText(this,"EL REGISTRO DEL FACTURA FUE EXITOSO",Toast.LENGTH_LONG).show();
           }
           else{
               Toast.makeText(this,"EL REGISTRO DEL FACTURA NO FUE EXITOSO",Toast.LENGTH_LONG).show();
           }


       }else{
           Toast.makeText(this,"EL REGISTRO DEL FACTURA NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
       }
       etn.setText("");
       etf.setText("");
       ett.setText("");
       etCodp.setText("");
   }




   public void eliminar (View view){

       String numero=etn.getText().toString();

       if(!numero.isEmpty()){

           int fila= dbFereteria.getWritableDatabase().delete("factura","numero="+numero,null);

           if (fila>0){
               Toast.makeText(this,"EL FACTURA FUE ELIMINADO",Toast.LENGTH_LONG).show();
           }
           else{
               Toast.makeText(this,"EL FACTURA NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
           }


       }
       etn.setText("");
       etf.setText("");
       ett.setText("");
       etCodp.setText("");
   }
}

