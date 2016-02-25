package go.proyect.rbc.rebeca_reconsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marcelo on 1/12/2015.
 */
@SuppressWarnings("ALL")
public class DataBaseREBECA extends SQLiteOpenHelper{

    //Sentencia SQL para crear la tabla de referencias
    String sqlCreate = "CREATE TABLE referencias(request TEXT, respuesta TEXT,id INTEGER)";

    public DataBaseREBECA(Context contexto, String nombre,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {

        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS referencias");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

    public String Respuesta(DataBaseREBECA ref,String texto){
        SQLiteDatabase db = ref.getWritableDatabase();
        String respuestaAnls= "Aún no aprendo que significa eso";
        String[] args = new String[] {texto};
        @SuppressLint("Recycle") Cursor c = db.rawQuery(" SELECT respuesta,request,id FROM referencias WHERE request LIKE ?", args);

        if (c.moveToFirst()) {
          respuestaAnls  = c.getString(0);
        }
        if (Integer.parseInt(c.getString(2)) == 8) {
            String res;
            String operacion = "";
            String oper1 = "", oper2 = "";


            for (int i = 0; i <= texto.indexOf("más") || i <= texto.indexOf("menos") || i <= texto.indexOf("para") || i <= texto.indexOf("por"); i++) {
                char caracter = texto.charAt(i);

                if (!Character.isLetter(caracter)) {

                    oper1 += caracter;
                }
            }

            for (int i = 0; i >= texto.indexOf("más") || i >= texto.indexOf("menos") || i >= texto.indexOf("para") || i >= texto.indexOf("por"); i++) {
                char caracter = texto.charAt(i);

                if (!Character.isLetter(caracter)) {

                    oper2 += caracter;
                }
            }

            if (texto.indexOf("más") != 0) {
                operacion = "suma";
            }
            if (texto.indexOf("menos") != 0) {
                operacion = "rest";
            }
            if (texto.indexOf("para") != 0) {
                operacion = "div";
            }
            if (texto.indexOf("por") != 0) {
                operacion = "mult";
            }

            switch (operacion) {
                case "suma":
                    res = Integer.toString(Integer.parseInt(oper1) + Integer.parseInt(oper2));
                    break;
                case "rest":
                    res = Integer.toString(Integer.parseInt(oper1) - Integer.parseInt(oper2));
                    break;
                case "div":
                    res = Integer.toString(Integer.parseInt(oper1) / Integer.parseInt(oper2));
                    break;
                case "mult":
                    res = Integer.toString(Integer.parseInt(oper1) * Integer.parseInt(oper2));
                    break;
                default:
                    res = " Indeterminado, no entiendo ese tipo de operacion.";
                    break;
            }

            res = c.getString(0) + res;
        }


    return respuestaAnls;
    }
}



