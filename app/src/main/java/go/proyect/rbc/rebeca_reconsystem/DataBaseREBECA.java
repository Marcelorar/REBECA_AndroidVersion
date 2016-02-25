package go.proyect.rbc.rebeca_reconsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marcelo on 1/12/2015.
 */
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
        Cursor c = db.rawQuery(" SELECT respuesta,request,id FROM referencias WHERE request LIKE ?", args);

        if (c.moveToFirst()) {
          respuestaAnls  = c.getString(0);
        }
        if (Integer.parseInt(c.getString(2)) == 8) {
            int res = 0;
            int i;
            String oper1 = null, oper2 = null;
            int op1 = Integer.parseInt(oper1);
            int op2 = Integer.parseInt(oper2);


            for (i = 0; i <= texto.indexOf("más") || i <= texto.indexOf("menos") || i <= texto.indexOf("para") || i <= texto.indexOf("por"); i++) {
                char caracter = texto.charAt(i);

                if (Character.isLetter(caracter)) {
                } else {
                    oper1 += caracter;
                }
            }

            for (int j = i; j <= texto.length(); i++) {
                char caracter = texto.charAt(i);

                if (Character.isLetter(caracter)) {
                } else {
                    oper2 += caracter;
                }
            }
            String val = null;
            for (int a = texto.indexOf(op1); a <= texto.indexOf(op2); a++) {
                if (Character.isLetter(texto.charAt(a))) {
                    val += texto.charAt(a);
                }
            }
            switch (val) {
                case "más":
                    res = op1 + op2;
                    break;
                case "menos":
                    res = op1 - op2;
                    break;
                case "por":
                    res = op1 * op2;
                    break;
                case "para":
                    res = op1 / op2;
                    break;
            }
            respuestaAnls += res;
        }


    return respuestaAnls;
    }
}



