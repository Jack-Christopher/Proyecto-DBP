package com.example.administradordetareas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener
{
    EditText evento, fecha, descripcion;
    Button guardar, mostrar, recordatorio;
    String hora = "";

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        evento = (EditText) findViewById(R.id.editTextEvento);
        fecha = (EditText) findViewById(R.id.editTextFecha);
        descripcion = (EditText) findViewById(R.id.editTextDescripcion);

        recordatorio = (Button) findViewById(R.id.buttonRecordatorio);
        guardar = (Button) findViewById(R.id.buttonGuardar);
        mostrar = (Button) findViewById(R.id.buttonMostrar);

        notificationHelper = new NotificationHelper(this);

        recordatorio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        guardar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Guardar(evento.getText().toString(), fecha.getText().toString(), hora,descripcion.getText().toString());
                evento.setText("");
                fecha.setText("");
                descripcion.setText("");
            }
        });

        mostrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this,  ListadoDeTareas.class));
            }
        });
    }

    private void Guardar(String newEvento, String newFecha, String newHora,String newDescripcion)
    {
        BaseHelper helper = new BaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            ContentValues c = new ContentValues();
            c.put("EVENTO", newEvento);
            c.put("FECHA", newFecha);
            c.put("HORA", newHora);
            c.put("DESCRIPCION", newDescripcion);

            db.insert("TAREAS",null, c);
            Toast.makeText(this, "Tarea registrada", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        db.close();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1)
    {
        hora =  i +":"+ i1;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, i);
        c.set(Calendar.MINUTE, i1);
        c.set(Calendar.SECOND, 0);

        startAlarm(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("Titulo",evento.toString() );
        intent.putExtra("Mensaje", descripcion.toString());

        sendBroadcast(intent);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
}