package com.example.lilianpaola.grabaraudio;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btRec, btStop, btPlay, btNuevo;
   private MediaRecorder grabacion;
    private  String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btRec = (Button) findViewById(R.id.btRec);
        btStop = (Button) findViewById(R.id.btStop);
        btPlay = (Button) findViewById(R.id.btPlay);
        btNuevo= (Button) findViewById(R.id.btNuevo);

        btStop.setEnabled(false);
        btPlay.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() +"/Audio.3gp";

        grabacion = new MediaRecorder();
        grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
        grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        grabacion.setOutputFile(outputFile);

        btRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    grabacion.prepare();
                    grabacion.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                btRec.setEnabled(false);
                btStop.setEnabled(true);

                Toast.makeText(getApplicationContext(),"Comenzar con el diagnóstico",Toast.LENGTH_LONG).show();
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabacion.stop();
                grabacion.release();
                grabacion = null;

                btStop.setEnabled(false);
                btPlay.setEnabled(true);

                Toast.makeText(getApplicationContext(),"Diagnóstico grabado", Toast.LENGTH_LONG).show();
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws
                    IllegalArgumentException,SecurityException,
                    IllegalStateException{

                MediaPlayer m = new MediaPlayer();
                try{
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(),"Reproduciendo audio",Toast.LENGTH_LONG).show();
            }
        });
        btNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //reinicia una Activity
    public static void reiniciarActivity(Activity actividad){
        Intent intent=new Intent();
        intent.setClass(actividad, actividad.getClass());
        //llamamos a la actividad
        actividad.startActivity(intent);
        //finalizamos la actividad actual
        actividad.finish();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it
        is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
    }*/
}
