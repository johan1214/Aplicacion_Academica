package com.example.g10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Declaración de las variables a utilizar
     */
    TextView beca;
    EditText matricula,nombres,apellidos,plata;
    Button btnRegistrate,btnlimpiar,btnConsultar;
    public AdminSQLiteOpenHelper admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *Obtener los objetos EditText
         */
        matricula=(EditText) findViewById(R.id.matricula);
        nombres=(EditText)findViewById(R.id.nombres);
        apellidos=(EditText)findViewById(R.id.apellidos);
        plata=(EditText)findViewById(R.id.plata);

        /**
         * Crea la base de datos con el nombre "administrass.db"
         */

        admin=new AdminSQLiteOpenHelper(this,"administrass.db",null,1);


        btnRegistrate=(Button) findViewById(R.id.registrate);
        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * Método para el botón Registrar, recibe los datos ingresados por el usuario y los
             * guarda en la base de datos.
             */
            @Override
            public void onClick(View v) {

                SQLiteDatabase db= admin.getWritableDatabase();
                String matriculaText =matricula.getText().toString();
                String nombresText=nombres.getText().toString();
                String apellidosText=apellidos.getText().toString();
                String plataText=plata.getText().toString();

                if(!matriculaText.isEmpty()&&!nombresText.isEmpty()&&!apellidosText.isEmpty()&&
                        !plataText.isEmpty()){
                    ContentValues registro= new ContentValues();
                    registro.put("matricula_usuario",matriculaText);
                    registro.put("nom_usuario",nombresText);
                    registro.put("ap_usuario",apellidosText);
                    registro.put("sueldo",plataText);
                    db.insert("usuariosRegistrados",null,registro);
                    db.close();
                    Toast.makeText(MainActivity.this,"Usuario registrado",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this,"Llene todos los datos del campo" +
                            "",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnlimpiar=(Button)findViewById(R.id.limpiar);
        btnlimpiar.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * Método para el botón Eliminar
             * que reemplaza el contenido de los objetos EditText por un String vacío
             */
            @Override
            public void onClick(View v) {
                matricula.setText("");
                nombres.setText("");
                apellidos.setText("");
                plata.setText("");

            }
        });
        btnConsultar =(Button)findViewById(R.id.consultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * Método del botón Consultar, que revisa la matricula ingresada y consulta si el
             * estudiante con esa matricula está en el sistema, si sí se encuentra, compara su
             * factor económico, si es A2 o A1 es aplicable para la beca, caso contrario no.
             */
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(MainActivity.this,"administrass.db",null,1);
                SQLiteDatabase db=admin.getWritableDatabase();

                String matriculainrgesadaText=matricula.getText().toString();

                if(!matriculainrgesadaText.isEmpty()){
                    Cursor cursor=db.rawQuery("SELECT * FROM  usuariosRegistrados  where matricula_usuario='"+matriculainrgesadaText+"' LIMIT 1" , null);

                    if(cursor.moveToFirst()){

                        if ("A1".equals(cursor.getString(3))) {
                            nombres.setText(cursor.getString(1));
                            apellidos.setText(cursor.getString(2));
                            plata.setText(cursor.getString(3));
                            beca=(TextView)findViewById(R.id.beca);
                            beca.setText("Sí puede aplicar para una beca");
                            db.close();

                        }
                        if ("A2".equals(cursor.getString(3))) {
                            nombres.setText(cursor.getString(1));
                            apellidos.setText(cursor.getString(2));
                            plata.setText(cursor.getString(3));
                            beca=(TextView)findViewById(R.id.beca);
                            beca.setText("Sí puede aplicar para una beca");
                            db.close();

                        }
                        else{
                            beca=(TextView)findViewById(R.id.beca);
                            beca.setText("No puede aplicar a una beca");
                            db.close();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"Este estudiante no existe",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Ingrese matricula",Toast.LENGTH_SHORT).show();

                }


            }
        });
            }



    }
