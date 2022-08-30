package com.example.listaenemigosjahel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    RadioButton masculino, femenino; //DECLARACION DE TODAS LAS VARIABLES
    RadioGroup grupo_sexo;
    EditText nombre, ofensa;
    ListView lista;
    String sexo;
    Boolean sexo_comprobacion = false;

    SharedPreferences sharedPreferences;
    ListaPersistente listaPersistente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grupo_sexo = (RadioGroup) findViewById(R.id.grupo_sexo); //VINCULACION DE LAS VARIABLES AL XML
        masculino = (RadioButton) findViewById(R.id.masculino);
        femenino = (RadioButton) findViewById(R.id.femenino);
        nombre = (EditText) findViewById(R.id.nombre);
        ofensa = (EditText) findViewById(R.id.ofensa);
        lista = (ListView) findViewById(R.id.lista);

        sharedPreferences = getSharedPreferences("ofensas", MODE_PRIVATE);  //PONGO NOMBRE AL SHAREDPREFERENCES Y LO PONGO EN MODO PRIVADO

        String json = sharedPreferences.getString("ofensas", "");  //DECLARACIÓN CON LA QUE RECOGEMOS LOS DATOS GUARDADOS EN JSON
        if (!json.isEmpty()) {
            listaPersistente = new ListaPersistente();
            listaPersistente = listaPersistente.fromJson(json);
        } else {
            listaPersistente = new ListaPersistente();
        }

        AdapterLista adapter = new AdapterLista(this, R.layout.item, listaPersistente.listaDeOfensas); //ADAPTER DEL LISTVIEW, DONDE INDICO MI PLANTILLA ITEM.XML Y LA LISTA PERSISTENTE
        lista.setAdapter(adapter);

        grupo_sexo.setOnCheckedChangeListener(this); //DECLARACION QUE HARÁ QUE OCURRA ALGO AL SELECCIONAR UNO DE LOS RADIOBUTTONS
        lista.setOnItemClickListener(this); //DECLARACION QUE HARÁ QUE OCURRA ALGO AL PULSAR SOBRE UNO DE LOS ELEMENTOS DE LA LISTA

    }


    public void anadirEnemigo(View vista) {  //METODO QUE AÑADIRÁ LOS ELEMENTOS AL ARRAY DEL LISTVIEW

        String nombreEnemigo = nombre.getText().toString(); //GUARDO EL TEXTO DE LOS EDITTEXT NOMBRE Y OFENSA EN VARIABLES
        String ofensaEnemigo = ofensa.getText().toString();

        if (masculino.isChecked() || femenino.isChecked()) { //COMPRUEBLO SI UNO DE LOS RADIOBUTTONS ESTÁ SELECCIONADO, SI ES ASI LA VARIABLE SEXOCOMPROBACION PASARÁ A SER TRUE
            sexo_comprobacion = true;
        }

        /*COMPRUEBO SI EL NOMBRE ESTÁ VACIO O SI LA VARIABLE SEXO_COMPROBACION ES TRUE, SI ES ASI,
        MANDARÁ UN TOAST ERROR Y SI NO ES ASI SE AÑADIRÁN LOS ELEMENTOS A LA LISTA*/

        if (nombreEnemigo.isEmpty() || !sexo_comprobacion) {
            Toast.makeText(this, "Nombre vacío o sexo no seleccionado.", Toast.LENGTH_SHORT).show();
        } else {
            listaPersistente.listaDeOfensas.add(new ObjetoOfensa(nombreEnemigo, sexo, ofensaEnemigo)); //AÑADO EL ELEMENTO A LA LISTA
            ArrayAdapter adapter = (ArrayAdapter) lista.getAdapter();
            adapter.notifyDataSetChanged(); //NOTIFICO EL CAMBIO PARA QUE SE ACTUALICE

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ofensas", listaPersistente.toJson()); //GUARDO EL DATO EN FORMATO JSON
            editor.apply();

            nombre.setText("");  //RESETEO CAMPOS DE TEXTO Y RADIOBUTTONS SI SE HA AÑADIDO EL ELEMENTO CORRECTAMENTE
            ofensa.setText("");
            grupo_sexo.clearCheck();
            sexo_comprobacion = false;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {  //METODO QUE HACE QUE LA VARIABLE SEXO VALGA "MASCULINO" O "FEMENIDO" EN FUNCION DEL ID DEL RADIOBUTTON SELECCIONADO
        switch (checkedId) {
            case R.id.masculino:
                sexo = "Masculino";
                break;
            case R.id.femenino:
                sexo = "Femenino";
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //METODO QUE MANDARÁ TODA LA INFO A LA OTRA ACTIVIDAD Y LA ABRIRÁ
        Intent intent = new Intent(this, MasInfo.class);
        Bundle bolsa = new Bundle();
        bolsa.putString("nombrekey", listaPersistente.listaDeOfensas.get(position).nombre);  //ENVIO CADA UNO DE LOS VALORES DE EL OBJETO QUE ESTÉ EN LA POSICION DEL ELEMENTO PULSADO
        bolsa.putString("sexokey", listaPersistente.listaDeOfensas.get(position).sexo);
        bolsa.putString("ofensakey", listaPersistente.listaDeOfensas.get(position).ofensa);
        intent.putExtras(bolsa);
        startActivity(intent); //ABRO LA ACTIVIDAD
    }
}