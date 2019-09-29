package com.apps.aggr.roomapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.apps.aggr.roomapp.db.database.db;
import com.apps.aggr.roomapp.db.entity.Profesor;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterProfesores.OnClickProfesor{

    Button agregarProfesor;
    RecyclerView listaProfesores;
    LinearLayoutManager layoutManager;
    AdapterProfesores adapterProfesores;

    //Initialize Profesor object
    Profesor profesor = new Profesor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Casting
        agregarProfesor = findViewById(R.id.btn_addProfesor);
        listaProfesores = findViewById(R.id.rv_Profesores);

        adapterProfesores = new AdapterProfesores(this, this);
        layoutManager = new LinearLayoutManager(this);
        listaProfesores.setLayoutManager(layoutManager);
        listaProfesores.setAdapter(adapterProfesores);
        listaProfesores.setHasFixedSize(true);


        //OnClickListeners
        agregarProfesor.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        verTodosProfesores();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterProfesores.deleteAll();
        db.destroyInstance();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_addProfesor){
            guardarDatos();
        }
    }

    @Override
    public void onClickProfesor(Profesor profesor) {

        verProfesorPorId(profesor);
    }

    private void guardarDatos() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View add_profesor = inflater.inflate(R.layout.layout_agregar_profesor, null);

        final EditText nombre_profesor = add_profesor.findViewById(R.id.edt_nombre_profesor);
        final EditText email_profesor = add_profesor.findViewById(R.id.edt_email_profesor);
        final Switch isActiveProfesor = add_profesor.findViewById(R.id.swt_isActiveProfesor);
        final Spinner spinner_areas = add_profesor.findViewById(R.id.spn_type_profesor);

        dialog.setView(add_profesor);


        //
        final String[] PROFESOR_AREAS = {"MATEMATICA", "FISICA", "COMPUTO"};
        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, PROFESOR_AREAS);
        spinner_areas.setAdapter(areasAdapter);

        dialog.setPositiveButton(getResources().getString(R.string.guardar_profesor), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                //Validate editexts
                if(!TextUtils.isEmpty(nombre_profesor.getText()) && !TextUtils.isEmpty(email_profesor.getText())){
                    profesor.setName(nombre_profesor.getText().toString());
                    profesor.setEmail(email_profesor.getText().toString());
                    if(isActiveProfesor.isChecked()){
                        profesor.setActive(true);
                    }else{
                        profesor.setActive(false);
                    }
                    profesor.setTypeProfesor(spinner_areas.getSelectedItem().toString());

                    GuardarNuevoProfesorTask guardarProfesor = new GuardarNuevoProfesorTask();
                    guardarProfesor.execute(profesor);
                }

            }
        });
        dialog.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
    private void verProfesorPorId(final Profesor profesor) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View add_profesor = inflater.inflate(R.layout.layout_agregar_profesor, null);

        final EditText nombre_profesor = add_profesor.findViewById(R.id.edt_nombre_profesor);
        final EditText email_profesor = add_profesor.findViewById(R.id.edt_email_profesor);
        final Switch isActiveProfesor = add_profesor.findViewById(R.id.swt_isActiveProfesor);
        final Spinner spinner_areas = add_profesor.findViewById(R.id.spn_type_profesor);

        dialog.setView(add_profesor);

        //
        final String[] PROFESOR_AREAS = {"MATEMATICA", "FISICA", "COMPUTO"};
        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, PROFESOR_AREAS);
        spinner_areas.setAdapter(areasAdapter);


        nombre_profesor.setText(profesor.getName());
        email_profesor.setText(profesor.getEmail());
        if(profesor.isActive()){
            isActiveProfesor.setChecked(true);
        }
        if(profesor.getTypeProfesor().equals("MATEMATICA")){
            spinner_areas.setSelection(0);
        }else if(profesor.getTypeProfesor().equals("FISICA")){
            spinner_areas.setSelection(1);
        }else{
            spinner_areas.setSelection(2);
        }

        dialog.setPositiveButton(getResources().getString(R.string.actualizar_profesor), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Validate editexts
                if(!TextUtils.isEmpty(nombre_profesor.getText()) && !TextUtils.isEmpty(email_profesor.getText())){
                    profesor.setName(nombre_profesor.getText().toString());
                    profesor.setEmail(email_profesor.getText().toString());
                    if(isActiveProfesor.isChecked()){
                        profesor.setActive(true);
                    }else{
                        profesor.setActive(false);
                    }
                    profesor.setTypeProfesor(spinner_areas.getSelectedItem().toString());
                    db.getdb(getApplicationContext()).profesorDAO().updateProfesorById(profesor);
                    adapterProfesores.updateProfesor(profesor);
                    Toast.makeText(MainActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setNeutralButton(getResources().getString(R.string.eliminar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.getdb(getApplicationContext()).profesorDAO().deleteProfesorByyId(profesor);
                adapterProfesores.deleteProfesor(profesor);
                Toast.makeText(MainActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    private void verTodosProfesores(){
        for(int i = 0; i<db.getdb(getApplicationContext()).profesorDAO().selectAllProfesors().size();i++){
            adapterProfesores.addProfesor(db.getdb(getApplicationContext()).profesorDAO().selectAllProfesors().get(i));
        }
    }

    public class GuardarNuevoProfesorTask extends AsyncTask<Profesor, Void, Void>{


        @Override
        protected Void doInBackground(Profesor... profesors) {
            db.getdb(getApplicationContext()).profesorDAO().insertProfesor(profesors[0]);
            adapterProfesores.addProfesor(profesors[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void Void) {
            Toast.makeText(MainActivity.this, "Guardado", Toast.LENGTH_SHORT).show();
        }
    }

}
