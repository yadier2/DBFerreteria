package com.moviles.ferreteriadb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import OpenHelper.SqLiteOpenHelper;
import androidx.appcompat.app.AppCompatActivity;

public class PedidoLineaActivity extends AppCompatActivity {
    SqLiteOpenHelper dbFereteria  =  new SqLiteOpenHelper(this,"dbFereteria",null,1);

    private EditText etCodigoLinea,etCodigoPedido,etCodigoProducto,etcantidadProdc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_linea);
        etCodigoLinea = findViewById(R.id.etCodigoLinea);
        etCodigoPedido = findViewById(R.id.etCodigoPedido);
        etCodigoProducto = findViewById(R.id.etCodigoProducto);
        etcantidadProdc = findViewById(R.id.etcantidadProdc);
    }

    public void regresar(View view){
        Intent Pedido = new Intent(this,PedidoActivity.class);
        startActivity(Pedido);
    }
    public void registrar (View view){

        String codigoLinea =etCodigoLinea.getText().toString();
        String codigoPedido =etCodigoPedido.getText().toString();
        String codigoProducto =etCodigoProducto.getText().toString();
        String cantidadProdc =etcantidadProdc.getText().toString();

        if (!codigoLinea.isEmpty() && !codigoPedido.isEmpty() && !codigoProducto.isEmpty() && !cantidadProdc.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigoLinea);
            registro.put("codigo_pedido",codigoPedido);
            registro.put("cantidad",cantidadProdc);
            registro.put("codigo_producto",codigoProducto);

            dbFereteria.abrirDB();
            dbFereteria.getWritableDatabase().insert("pedidoLinea",null,registro);
            dbFereteria.cerrarDB();

            etCodigoPedido.setText("");
            etCodigoProducto.setText("");
            etcantidadProdc.setText("");

            Toast.makeText(this,"Registro guardado correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public void consulta (View view){

        String codigo1=etCodigoLinea.getText().toString();

        if(!codigo1.isEmpty()){

            try (Cursor fill =  dbFereteria.getWritableDatabase().rawQuery("select codigo_pedido,codigo_producto,cantidad from pedidoLinea where codigo=" + codigo1, null)) {

                if (fill.moveToFirst()) {
                    etCodigoPedido.setText(fill.getString(0));
                    etCodigoProducto.setText(fill.getString(1));
                    etcantidadProdc.setText(fill.getString(2));
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

        String codigoLinea =etCodigoLinea.getText().toString();
        String codigoPedido =etCodigoPedido.getText().toString();
        String codigoProducto =etCodigoProducto.getText().toString();
        String cantidadProdc =etcantidadProdc.getText().toString();

        if (!codigoLinea.isEmpty() && !codigoPedido.isEmpty() && !codigoProducto.isEmpty() && !cantidadProdc.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigoLinea);
            registro.put("codigo_pedido",codigoPedido);
            registro.put("cantidad",cantidadProdc);
            registro.put("codigo_producto",codigoProducto);

            int fila= dbFereteria.getWritableDatabase().update("pedidoLinea",registro,"codigo="+codigoLinea,null);

            if (fila>0){
                Toast.makeText(this,"EL REGISTRO DEL PEDIDO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL REGISTRO DEL PEDIDO NO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"EL REGISTRO DEL PEDIDO NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
        }

        etCodigoPedido.setText("");
        etCodigoProducto.setText("");
        etcantidadProdc.setText("");
    }


    public void eliminar (View view){

        String codigo=etCodigoLinea.getText().toString();

        if(!codigo.isEmpty()){

            int fila= dbFereteria.getWritableDatabase().delete("pedidoLinea","codigo="+codigo,null);


            if (fila>0){
                Toast.makeText(this,"EL PEDIDO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL PEDIDO NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etCodigoLinea.setText("");
        etCodigoPedido.setText("");
        etCodigoProducto.setText("");
        etcantidadProdc.setText("");
    }
}