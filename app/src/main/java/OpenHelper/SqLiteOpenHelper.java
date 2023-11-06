package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqLiteOpenHelper extends SQLiteOpenHelper {


    public SqLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table cliente(cedula integer primary key, nombre text, direccion text,telefono int)");
        sqLiteDatabase.execSQL("create table pedido(codigo integer primary key autoincrement, nit_usuario integer not null, descripcion text, fecha text,cantidad int, foreign key (nit_usuario) references cliente (cedula))");
        sqLiteDatabase.execSQL("create table factura(numero integer primary key autoincrement, fecha text, total text, id_pedido integer not null, foreign key (id_pedido) references pedido (codigo))");
        sqLiteDatabase.execSQL("create table producto(codigo integer primary key, descripcion text, valor text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void abrirDB() {
        this.getWritableDatabase();
    }

    public void cerrarDB() {
        this.close();
    }


}
