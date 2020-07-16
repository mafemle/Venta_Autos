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

public class AutoActivity extends AppCompatActivity {
    EditText jetplaca, jetmodelo, jetmarca, jetvalor;
    Button jbtnconsultar, jbtnadicionar, jbtnmodificar, jbtneliminar, jbtnregresar, jbtncancelar;
    MainSQLiteOpenHelper Admin = new MainSQLiteOpenHelper(this, "empresa.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        jetplaca  =findViewById(R.id.etplaca);
        jetmodelo = findViewById(R.id.etmodelo);
        jetmarca = findViewById(R.id.etmarca);
        jetvalor  = findViewById(R.id.etvalor);

        jbtnconsultar = findViewById(R.id.btconsultar);
        jbtnadicionar = findViewById(R.id.btadicionar);
        jbtnmodificar = findViewById(R.id.btmodificar);
        jbtneliminar = findViewById(R.id.bteliminar);
        jbtnregresar = findViewById(R.id.btregresar);
        jbtncancelar = findViewById(R.id.btcancelar);
    }

    public void limpiar_campos(){
        jetplaca.setText("");
        jetmodelo.setText("");
        jetmarca.setText("");
        jetvalor.setText("");
        jetplaca.requestFocus();
    }

    public  void adicionar (View v){
        SQLiteDatabase db = Admin .getWritableDatabase();
        String placa, modelo, marca, valor;
        placa = jetplaca.getText().toString();
        modelo = jetmodelo.getText().toString();
        marca = jetmarca.getText().toString();
        valor = jetvalor.getText().toString();

        if(placa.isEmpty() || modelo.isEmpty() || marca.isEmpty() || valor.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_LONG).show();
            jetplaca.requestFocus();
        }
        else{
            ContentValues dato  = new ContentValues();
            dato.put("placa", placa);
            dato.put("modelo", modelo);
            dato.put("marca", marca);
            dato.put("valor", valor);
            long resp=db.insert("auto", null, dato);
            if(resp>0){
                Toast.makeText(this, "Registro guardado ", Toast.LENGTH_LONG).show();
                limpiar_campos();
            }else{
                Toast.makeText(this, "Error. No se pudo guardar ", Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }

    public  void modificar (View v){
        SQLiteDatabase db = Admin.getWritableDatabase();
        String placa, modelo, marca, valor;
        placa = jetplaca.getText().toString();
        modelo = jetmodelo.getText().toString();
        marca = jetmarca.getText().toString();
        valor = jetvalor.getText().toString();

        if(placa.isEmpty() || modelo.isEmpty() || marca.isEmpty() ||  valor.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos para actualizar el auto", Toast.LENGTH_LONG).show();
            jetplaca.requestFocus();
        }else {
            ContentValues dato = new ContentValues();
            dato.put("placa", placa);
            dato.put("modelo", modelo);
            dato.put("marca", marca);
            dato.put("valor", valor);
            long resp = db.update("auto", dato, "placa='" + placa + "'", null);
            if (resp > 0) {
                Toast.makeText(this, "Se actualizaron los datos correctamente ", Toast.LENGTH_LONG).show();
                limpiar_campos();
            } else {
                Toast.makeText(this, "Error. No se pudo guardar ", Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }

    public void consultar(View v){
        SQLiteDatabase db = Admin .getReadableDatabase();
        String placa;
        placa = jetplaca.getText().toString();
        if(placa.isEmpty()){
            Toast.makeText(this, "La placa es requerida para la busqueda", Toast.LENGTH_LONG).show();
            jetplaca.requestFocus();
        }else{
            Cursor fila  = db.rawQuery("SELECT * FROM auto WHERE placa='" + placa + "'", null );
            if(fila.moveToFirst()){

                jetmodelo.setText(fila.getString(1));
                jetmarca.setText(fila.getString(2));
                jetvalor.setText(fila.getString(3));

            }else{
                Toast.makeText(this, "La placa esta disponible", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
    }

    public void eliminar(View v){
        SQLiteDatabase db = Admin .getWritableDatabase();
        String placa;
        placa = jetplaca.getText().toString();


        if(placa.isEmpty()){
            Toast.makeText(this, "La placa es requerida para poder eliminar", Toast.LENGTH_LONG).show();
            jetplaca.requestFocus();
        }
        else{
            long resp=db.delete("auto", "placa = '" + placa + "'", null );
            if(resp>0){
                Toast.makeText(this, "Se eliminaron los datos correctamente ", Toast.LENGTH_LONG).show();
                limpiar_campos();
            }else{
                Toast.makeText(this, "Error. No se pudo eliminar ", Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }

    public void cancelar(View v){
        limpiar_campos();
    }

    public void regresar (View v){
        Intent IntRegresar = new Intent(this,MainActivity.class);
        startActivity(IntRegresar);
    }

}