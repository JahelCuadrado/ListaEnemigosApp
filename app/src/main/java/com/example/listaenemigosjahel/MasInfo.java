package com.example.listaenemigosjahel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MasInfo extends AppCompatActivity {

    TextView nombre_tv, sexo_tv, ofensa_tv; //DECLARO LAS VARIABLES
    ImageView iv_enemigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_info);

        nombre_tv = (TextView) findViewById(R.id.nombre_tv); //VINCULO LAS VARIABLES CON EL XML
        sexo_tv = (TextView) findViewById(R.id.sexo_tv);
        ofensa_tv = (TextView) findViewById(R.id.ofensa_tv);
        iv_enemigo = (ImageView) findViewById(R.id.iv_enemigo);


        Bundle bolsar = getIntent().getExtras(); //RECOJO LOS STRINGS ENVIADOS DESDE LA OTRA ACTIVIDAD

        /* CAMBIO EL CONTENIDO DE LOS TEXTVIEW POR LOS STRING QUE LLEGAN
         DESDE LA OTRA ACTIVIDAD*/
        nombre_tv.setText("Nombre: " + bolsar.getString("nombrekey"));
        sexo_tv.setText("Sexo: " + bolsar.getString("sexokey"));
        ofensa_tv.setText("Ofensa: " + bolsar.getString("ofensakey"));

        if(bolsar.getString("sexokey").equals("Masculino")){
            iv_enemigo.setImageResource(R.drawable.man);
        }else{
            iv_enemigo.setImageResource(R.drawable.woman);
        }

    }

    public void volverActividadAnterior(View vista) {
        finish();
    } //CIERRO LA ACTIVIDAD PARA QUE BI ESTÉ EN SEGUNDO PLANO
}