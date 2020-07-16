package com.example.proyectoventa_autos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText jetusuario,jetclave;
    Button jbtingresar,jbtregistrarse,jbtautos;
    //instanciar la clase MainSQLiteOpenHelper
    MainSQLiteOpenHelper Admin = new MainSQLiteOpenHelper(this, "empresa.db", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jetusuario=findViewById(R.id.etusuario);
        jetclave=findViewById(R.id.etclave);
        jbtingresar=findViewById(R.id.btingresar);
        jbtregistrarse=findViewById(R.id.btregistrarse);
        jbtautos=findViewById(R.id.btautos);
    }

    public void ventas(View v){
        SQLiteDatabase db = Admin.getReadableDatabase();
        String usuario, clave;
        usuario=jetusuario.getText().toString();
        clave=jetclave.getText().toString();

        if(usuario.isEmpty() || clave.isEmpty()){
            Toast.makeText(this, "Usuario y Clave son Obligatorios", Toast.LENGTH_LONG).show();
            jetusuario.requestFocus();
        }
        else{
            Cursor fila = db.rawQuery("select * from cliente where usuario='"+usuario+"'and clave='"+clave+"'", null);
            if(fila.moveToFirst()){

                Intent IntVentas = new Intent(this, VentaActivity.class);
                IntVentas.putExtra("dato", usuario);

                startActivity((IntVentas));
            }
            else{
                Toast.makeText(this, "Usuario o clave invalida", Toast.LENGTH_LONG).show();
                jetclave.setText("");
                jetusuario.requestFocus();
            }
        }
        db.close();
    }

    //funcionalidad de los botones registrarse, registrar auto
    public void clientes (View v)
    {
        Intent IntClientes=new Intent(this,ClientesActivity.class);
        startActivity(IntClientes);
    }

    public void autos (View v)
    {
        Intent IntAuto=new Intent(this,AutoActivity.class);
        startActivity(IntAuto);
    }
}