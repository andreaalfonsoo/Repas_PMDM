package com.example.andrea.repas_pmdm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;

public class Activity2 extends Activity {

    Button continua;
    EditText nombre;
    Switch notifica;
    Spinner provincia, localidad;
    ArrayAdapter<String> adapt_provincia;
    ArrayAdapter<CharSequence> adapt_localidad;
    String provin = "";
    String local = "";
    private final int activity1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        continua = (Button) findViewById(R.id.bt_continua);
        nombre = (EditText) findViewById(R.id.et_nombre);
        notifica = (Switch) findViewById(R.id.sw_notif);
        provincia = (Spinner) findViewById(R.id.sp_provincia);
        localidad = (Spinner) findViewById(R.id.sp_localidad);
        continua = (Button) findViewById(R.id.bt_continua);

        //PROVINCIAS
        //se rellenara un array a partir del xml
        String array_provincia[] = getResources().getStringArray(R.array.provincias);
        //adaptador para spinner
        adapt_provincia = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array_provincia);
        //implementacion del adaptador en el spinner
        provincia.setAdapter(adapt_provincia);

        //LOCALIDADES
        //adaptador para spinner
        adapt_localidad = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item);

        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //se recogera las id de los arrays almacenados en el xml
                TypedArray ta = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);
                //se llenara el array con la informacion que se ha almacenado
                CharSequence arraylocalidades[] = ta.getTextArray(position);
                //se recogera en una variable la provincia seleccionada
                provin = provincia.getSelectedItem().toString();

                //se llenara el spinner
                adapt_localidad.clear();
                adapt_localidad.addAll(arraylocalidades);

                //se colocara el adaptador en el spinner
                localidad.setAdapter(adapt_localidad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        localidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //se recoge en una variable la localidad seleccionada
                local = localidad.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //BOTON CONTINUA
        //recoge la información para pasarla al activity1
        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notifica.isChecked()){
                    notificaciones();
                    infoActivity1();
                    finish();
                }else{
                    infoActivity1();
                    finish();
                }
            }
        });
    }

    public void infoActivity1(){
        Intent i2 = new Intent(getApplicationContext(), Activity1.class);
        Bundle b = new Bundle();

        b.putString("nombre",nombre.getText().toString());
        b.putString("provincia", provin);
        b.putString("localidad", local);
        b.putBoolean("notificacion", notifica.isChecked());
        i2.putExtras(b);
        startActivityForResult(i2, activity1);
    }

    public void notificaciones(){
        //creacion de la notificación
        Notification.Builder bn = new Notification.Builder(getApplicationContext());
        bn.setContentTitle("Alert");
        bn.setContentText("¿Desea continuar?");
        bn.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        bn.setSmallIcon(R.mipmap.ic_launcher);
        //definir estilo de la notificacion
        Notification.InboxStyle is = new Notification.InboxStyle();
        is.addLine(nombre.getText());
        is.addLine(provincia.getSelectedItem().toString());
        is.addLine(localidad.getSelectedItem().toString());
        bn.setStyle(is);
        //accion de notificación
        Bundle b = new Bundle();
        Intent i3 = new Intent();
        PendingIntent pI = PendingIntent.getActivity(getApplicationContext(), activity1, i3, PendingIntent.FLAG_UPDATE_CURRENT);
        bn.addAction(R.mipmap.ic_launcher, "Continuar", pI);

        Intent i4 = new Intent(getApplicationContext(),Activity1.class);
        PendingIntent pI2 = PendingIntent.getActivity(getApplicationContext(), 0, i4, PendingIntent.FLAG_UPDATE_CURRENT);
        bn.addAction(R.mipmap.ic_launcher, "Reiniciar", pI2);

        i3.putExtras(b);
        i4.putExtras(b);
        bn.setContentIntent(pI);
        bn.setContentIntent(pI2);

        //lanza la notificacion
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2, bn.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
