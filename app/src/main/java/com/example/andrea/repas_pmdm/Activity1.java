package com.example.andrea.repas_pmdm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity1 extends Activity {

    Button botonPul;
    TextView tv;
    TextView textNombre;
    TextView textProvincia;
    TextView textLocalidad;

    int contador = 0;
    int contador2 = 0;

    final int activity2=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        botonPul = (Button) findViewById(R.id.bt_pulsa);
        tv = (TextView) findViewById(R.id.tv_contador);
        textNombre = (TextView) findViewById(R.id.textNombre);
        textProvincia = (TextView) findViewById(R.id.textProvincia);
        textLocalidad = (TextView) findViewById(R.id.textLocalidad);

        botonPul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contador2 < 5) {
                    contador = contador + 1;
                    contador2 = contador2 +1;
                    tv.setText(contador+ " veces pulsado");
                    notificaPulsacion(contador2);
                }else{
                    contador2 = 0;
                    Intent i = new Intent(getApplicationContext(), Activity2.class);
                    startActivityForResult(i, activity2);
                    finish();
                }
            }
        });

        //Llenar datos de activity2
        Bundle b = getIntent().getExtras();
        if(b != null) {
            textNombre.setText("" + b.getString("nombre"));
            textProvincia.setText("Provincia: " + b.getString("provincia"));
            textLocalidad.setText("Localidad: " + b.getString("localidad"));
        }
    }

    public void notificaPulsacion(int contador2){
        Notification.Builder ncb = new Notification.Builder(this);

        ncb.setContentTitle("Pulsaciones");
        ncb.setContentText(String.valueOf(contador2));
        ncb.setSmallIcon(R.mipmap.ic_launcher);
        //lanza la notificacion
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, ncb.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity1, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }






}
