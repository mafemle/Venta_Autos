package com.example.proyectoventa_autos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VentaActivity extends AppCompatActivity {
    EditText jetcolor, jetvalor, jetmarca, jetmodelo, jetplaca, jeusuario;
    Button jbtcancelar, jbtregresar, jbtcomprar;
    MainSQLiteOpenHelper Admin = new MainSQLiteOpenHelper(this, "empresa.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        jetcolor=findViewById(R.id.etcolor);
        jetvalor=findViewById(R.id.etvalor);
        jetmarca=findViewById(R.id.etmarca);
        jetmodelo=findViewById(R.id.etmodelo);
        jetplaca=findViewById(R.id.etplaca);
        jeusuario=findViewById(R.id.etusuario);

        jbtcancelar=findViewById(R.id.btcancelar);
        jbtcomprar=findViewById(R.id.btcomprar);
        jbtregresar=findViewById(R.id.btregresar);

        //recuperar el contenidp de la variable session

        String usuario;
        usuario = getIntent().getStringExtra("dato");
        jeusuario.setText(usuario);
        jetplaca.requestFocus();
    }
    public void buscarplaca(View v){
        SQLiteDatabase db=Admin.getReadableDatabase(); // solo hacer lectura se los datos de la tabla
        String placa;
        placa=jetplaca.getText().toString();

        if(placa.isEmpty()){
            Toast.makeText(this,"La placa es requerida", Toast.LENGTH_LONG).show();
        }
        else{
            Cursor fila=db.rawQuery("select * from auto where placa='"+placa+"'", null);
            if(fila.moveToFirst()){

                jetmodelo.setText(fila.getString(1));
                jetmarca.setText(fila.getString(2));
                jetvalor.setText(fila.getString(3));
            }
            else{
                Toast.makeText(this,"El registro no se encontro", Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }
    public void comprar(View v){
        SQLiteDatabase db = Admin.getWritableDatabase();
        String placa, usuario, color, valor;

        placa = jetplaca.getText().toString();
        usuario=jeusuario.getText().toString();
        color = jetcolor.getText().toString();
        valor=jetvalor.getText().toString();

        if(usuario.isEmpty() || placa.isEmpty() || valor.isEmpty() || color.isEmpty()){
            Toast.makeText(this, "Todos los datos son obligatorios", Toast.LENGTH_LONG).show();
            jetplaca.requestFocus();
        }
        else{
            ContentValues dato = new ContentValues();
            dato.put("placa", placa);
            dato.put("usuario", usuario);
            dato.put("color", color);
            dato.put("valor", valor);

            long resp = db.insert("venta", null, dato);
            if(resp>0){
                Toast.makeText(this, "Compra exitosa", Toast.LENGTH_LONG).show();
                limpiarCampos();
            }
            else{
                Toast.makeText(this, "Error. No se pudo realizar la compra ", Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }

    public void regresarventas(View v){
        Intent IntregVentas = new Intent(this, MainActivity.class);
        startActivity((IntregVentas));
    }
    public void limpiarCampos(){

        jetcolor.setText("");
        jetvalor.setText("");
        jetmarca.setText("");
        jetmodelo.setText("");
        jetplaca.setText("");
    }

    public void cancelarventas(View v){
        limpiarCampos();
    }

}