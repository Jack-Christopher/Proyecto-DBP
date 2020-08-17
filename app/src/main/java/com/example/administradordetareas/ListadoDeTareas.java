package com.example.administradordetareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoDeTareas extends AppCompatActivity
{
    ListView listView;
    ArrayList<String> Listado;

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        CargarListado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_de_tareas);

        listView = (ListView) findViewById(R.id.listView);

        CargarListado();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String oldEvento = Listado.get(i).split("  -->  ")[0];
                String oldFecha = Listado.get(i).split("  -->  ")[1];
                oldFecha =  oldFecha.split(" \\(")[0];
                String oldDescripcion = Listado.get(i).split(":  ")[1];


                Intent intent = new Intent(ListadoDeTareas.this, Modificar.class);
                intent.putExtra("oldEvento", oldEvento);
                intent.putExtra("oldFecha", oldFecha);
                intent.putExtra("oldDescripcion", oldDescripcion);
                startActivity(intent);
            }
        });

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void CargarListado()
    {
        Listado = ListaDeTareas();
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Listado);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> ListaDeTareas()
    {
        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        try
        {
            String sql = "SELECT EVENTO, FECHA, HORA, DESCRIPCION FROM TAREAS";
            Cursor c = db.rawQuery(sql, null);
            if (c.moveToFirst()) {
                do {
                    String newHora= c.getString(2).split(":")[0] + ":";
                    String minutos = c.getString(2).split(":")[1];
                    if(Integer.parseInt(minutos) < 10)
                    {
                        newHora += "0"+ minutos;
                    }

                    String linea = c.getString(0) + "  -->  " + c.getString(1) +
                            " ( " + newHora + " )"  + " ::  " + c.getString(3);
                    datos.add(linea);
                }
                while (c.moveToNext());
            }
            c.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return datos;
    }
}