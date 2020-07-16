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

public class ClientesActivity extends AppCompatActivity {
    Button jbtregresar,jbtconsultar,jbtadicionar,jbtmodificar,jbteliminar,jbtcancelar;
    EditText jetnombre,jetusuario,jetclave1,jetclave2,jetciudad;
    MainSQLiteOpenHelper Admin = new MainSQLiteOpenHelper(this, "empresa.db", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        jbtconsultar=findViewById(R.id.btconsultar);
        jbtadicionar=findViewById(R.id.btadicionar);
        jbtmodificar=findViewById(R.id.btmodificar);
        jbteliminar=findViewById(R.id.bteliminar);
        jbtcancelar=findViewById(R.id.btcancelar);
        jbtregresar=findViewById(R.id.btregresar);

        jetnombre=findViewById(R.id.etnombre);
        jetusuario=findViewById(R.id.etusuario);
        jetclave1=findViewById(R.id.etclave1);
        jetclave2=findViewById(R.id.etclave2);
        jetciudad=findViewById(R.id.etciudad);
    }
    public void limpiar_campos()
    {
        jetnombre.setText("");
        jetusuario.setText("");
        jetclave1.setText("");
        jetclave2.setText("");
        jetciudad.setText("");
        jetnombre.requestFocus();
    }
    public void consultar(View v)
    {
        SQLiteDatabase db=Admin.getReadableDatabase();
        String nombre;
        nombre=jetnombre.getText().toString();
        if (nombre.isEmpty())
        {
            Toast.makeText(this,"El nombre es requerido para la busqueda",Toast.LENGTH_LONG).show();
            jetnombre.requestFocus();
        }
        else
        {
            Cursor fila=db.rawQuery("select * from cliente where nombre='" + nombre + "'",null);
            if (fila.moveToFirst())
            {
                jetusuario.setText(fila.getString(1));
                jetclave1.setText(fila.getString(2));
                jetciudad.setText(fila.getString(3));
            }
            else
            {
                Toast.makeText(this,"Registro no hallado",Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }


    public void adicionar(View v)
    {
        SQLiteDatabase db=Admin.getWritableDatabase();
        String nombre,usuario,clave1,clave2,ciudad;
        nombre=jetnombre.getText().toString();
        usuario=jetusuario.getText().toString();
        clave1=jetclave1.getText().toString();
        clave2=jetclave2.getText().toString();
        ciudad=jetciudad.getText().toString();
        if (nombre.isEmpty() || usuario.isEmpty() || clave1.isEmpty() || clave2.isEmpty() || ciudad.isEmpty())
        {
            Toast.makeText(this,"Todos los datos son obligatorios",Toast.LENGTH_LONG).show();
            jetnombre.requestFocus();
        }
        else
        {
            if (!clave1.equals(clave2))
            {
                Toast.makeText(this,"Verifique clave",Toast.LENGTH_LONG).show();
                jetclave1.requestFocus();
            }
            else
            {
                ContentValues dato=new ContentValues();
                dato.put("nombre",nombre);
                dato.put("usuario",usuario);
                dato.put("clave",clave1);
                dato.put("ciudad",ciudad);
                long resp=db.insert("cliente",null,dato);
                if (resp > 0)
                {
                    Toast.makeText(this,"Registro guardado",Toast.LENGTH_LONG).show();
                    limpiar_campos();
                }
                else
                {
                    Toast.makeText(this,"Error guardando registro",Toast.LENGTH_LONG).show();
                }
            }
        }
        db.close();
    }

    public void modificar(View v)
    {
        SQLiteDatabase db=Admin.getWritableDatabase();
        String nombre,usuario,clave1,clave2,ciudad;
        nombre=jetnombre.getText().toString();
        usuario=jetusuario.getText().toString();
        clave1=jetclave1.getText().toString();
        clave2=jetclave2.getText().toString();
        ciudad=jetciudad.getText().toString();
        if (nombre.isEmpty() || usuario.isEmpty() || clave1.isEmpty() || clave2.isEmpty() || ciudad.isEmpty())
        {
            Toast.makeText(this,"Todos los datos son obligatorios",Toast.LENGTH_LONG).show();
            jetnombre.requestFocus();
        }
        else
        {
            if (!clave1.equals(clave2))
            {
                Toast.makeText(this,"Verifique clave",Toast.LENGTH_LONG).show();
                jetclave1.requestFocus();
            }
            else
            {
                ContentValues dato=new ContentValues();
                dato.put("nombre",nombre);
                dato.put("usuario",usuario);
                dato.put("clave",clave1);
                dato.put("ciudad",ciudad);
                long resp=db.update("cliente",dato,"nombre='" + nombre + "'",null);
                if (resp > 0)
                {
                    Toast.makeText(this,"Registro guardado",Toast.LENGTH_LONG).show();
                    limpiar_campos();
                }
                else
                {
                    Toast.makeText(this,"Error guardando registro",Toast.LENGTH_LONG).show();
                }
            }
        }
        db.close();
    }

    public void eliminar(View v)
    {
        SQLiteDatabase db=Admin.getWritableDatabase();
        String nombre;
        nombre=jetnombre.getText().toString();
        if (nombre.isEmpty())
        {
            Toast.makeText(this,"Nombre es obligatorio",Toast.LENGTH_LONG).show();
            jetnombre.requestFocus();
        }
        else
        {
            long resp=db.delete("cliente","nombre='" + nombre + "'",null);
            if (resp > 0)
            {
                Toast.makeText(this,"Registro eliminado",Toast.LENGTH_LONG).show();
                limpiar_campos();
            }
            else
            {
                Toast.makeText(this,"Error eliminando registro",Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }

    public void cancelar(View v)
    {
        limpiar_campos();
    }

    public void regresar (View v)
    {
        Intent IntPrincipal=new Intent(this,MainActivity.class);
        startActivity(IntPrincipal);
    }
}