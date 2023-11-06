package com.moviles.ferreteriadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cliente(View view){
        Intent Cliente = new Intent(this,ClienteActivity.class);
        startActivity(Cliente);
    }

   public void factura(View view){
   Intent Factura = new Intent(this,FacturaActivity.class);
   startActivity(Factura);
}



}