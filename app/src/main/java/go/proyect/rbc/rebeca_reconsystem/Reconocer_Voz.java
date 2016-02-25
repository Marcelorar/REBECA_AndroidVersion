package go.proyect.rbc.rebeca_reconsystem;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Reconocer_Voz extends Activity implements TextToSpeech.OnInitListener {
    private static final int codigo_de_reconocimiento = 1;
    DataBaseREBECA REBECA_IA =
            new DataBaseREBECA(this, "Reconocimiento", null, 1);
    //escuchar
    @SuppressWarnings("FieldCanBeLocal")
    private Button escuchar;
    private TextView prueba;
    private ArrayList<String> text;
    @SuppressWarnings("FieldCanBeLocal")
    private Intent escuchado;
    //private Button habla;


    //base de datos
    // Hablar
    private TextToSpeech hablar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconocer__voz);

        hablar = new TextToSpeech(this, this);
        //habla = (Button) findViewById(R.id.button);
        escuchar = (Button) findViewById(R.id.button2);
        prueba = (TextView) findViewById(R.id.textoMuestra);

// ejecuccion

        escuchar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                escuche();

            }
        });


     /*   habla.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speak();
            }

        });
*/

        //base de datos IA

        SQLiteDatabase db = REBECA_IA.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Hola','Hola, ¿en qué te puedo ayudar?',1)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Hola siri','El errar es de humanos, te perdono',2)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Hola cortana','Es un chiste.¿Verdad?',3)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Cómo te llamas','Rebeca para los amigos',4)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Cuántos años tienes','2 meses y contando',5)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Te amo','Critical Error expected: Java.Lang.ContentNotFound',6)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Te quiero','Yo también. Me quiero',7)");
            db.execSQL("INSERT INTO referencias(request, respuesta) VALUES('Cuánto es','Es ',8)");
            //Cerramos la base de datos
            db.close();
        }


    }

    @Override
    public void onDestroy() {
        // Don’t forget to shutdown tts!
        if (hablar != null) {
            hablar.stop();
            hablar.shutdown();
        }
        super.onDestroy();
    }

    private void escuche() {
        escuchado = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        escuchado.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        startActivityForResult(escuchado, codigo_de_reconocimiento);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == codigo_de_reconocimiento && resultCode == RESULT_OK) {
            transformar(data);
        }

    }

    private void transformar(Intent esq) {
        text = esq
                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        prueba.setText(text.get(0));
        speak();
    }

    private void speak() {


        String frase = REBECA_IA.Respuesta(REBECA_IA,text.get(0));

        hablo(frase);
    }


    public void hablo(String decir) {
        //noinspection deprecation
        hablar.speak(decir, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int status)

    {

        if (status == TextToSpeech.SUCCESS)

        {

            int result = hablar.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }/* else

            {
                // habla.setEnabled(true);

            }*/

        } else

        {
            Log.e("TTS", "Initilization Failed!");
        }

    }


}






