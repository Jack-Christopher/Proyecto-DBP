package com.example.administradordetareas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class Modificar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener
{
    EditText evento, fecha, descripcion;
    Button modificar, eliminar, recordatorio;
    String oldEvento, oldFecha, oldDescripcion;
    private NotificationHelper notificationHelper;
    String hora = "Hora no establecida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            oldEvento = b.getString("oldEvento");
            oldFecha = b.getString("oldFecha");
            oldDescripcion = b.getString("oldDescripcion");
        }

        evento = (EditText) findViewById(R.id.editTextEvento);
        fecha = (EditText) findViewById(R.id.editTextFecha);
        descripcion = (EditText) findViewById(R.id.editTextDescripcion);

        evento.setText(oldEvento);
        fecha.setText(oldFecha);
        descripcion.setText(oldDescripcion);


        recordatorio = (Button) findViewById(R.id.buttonRecordatorio);
        modificar = (Button) findViewById(R.id.buttonModificar);
        eliminar = (Button) findViewById(R.id.buttonEliminar);

        notificationHelper = new NotificationHelper(this);

        recordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar(oldEvento, evento.getText().toString(), fecha.getText().toString(), hora, descripcion.getText().toString());
                onBackPressed();
            }
        });


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar(oldEvento);
                onBackPressed();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);

        }
    }

    private void Modificar(String oldEvento, String newEvento, String newFecha, String newHora,String newDescripcion) {
        if (newEvento.isEmpty() || (newFecha.isEmpty() ||newDescripcion.isEmpty()))
        {
            Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        BaseHelper helper = new BaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_EVENTO, newEvento);
        values.put(BaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_FECHA, newFecha);
        values.put(BaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_HORA, newHora);
        values.put(BaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPCION, newDescripcion);

        String selection =  BaseHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_EVENTO + " LIKE ?";
        String[] selectionArgs = { oldEvento };
        int count = db.update(
                BaseHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);


        db.close();
    }

    private void Eliminar(String Evento) {
        BaseHelper helper = new BaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DELETE FROM TAREAS WHERE EVENTO='" + Evento + "'";

        db.execSQL(sql);
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

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before(Calendar.getInstance()))
        {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


}
