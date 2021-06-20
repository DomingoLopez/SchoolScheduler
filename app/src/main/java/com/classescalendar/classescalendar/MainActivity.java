package com.classescalendar.classescalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
;
import com.classescalendar.classescalendar.ui.DisplayHours;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    CheckBox curso_1_completo;
    CheckBox CA;
    CheckBox FFT;
    CheckBox ALEM;
    CheckBox FP;
    CheckBox FS;
    CheckBox LMD;
    CheckBox TOC;
    CheckBox MP;
    CheckBox ES;
    CheckBox IES;

    //Asignaturas optativas
    CheckBox DSD;
    CheckBox PDM;

    RadioGroup generate_group;
    RadioButton one_cuat_radio;
    RadioButton two_cuat_radio;
    RadioButton both_cuat_radio;

    Button generate_btn;


    List<String> cursos_completos;
    List<String> asignaturas_sueltas;
    String cuat_chosen = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        findViews();
        this.cursos_completos = new ArrayList<>();
        this.asignaturas_sueltas = new ArrayList<>();

        checkAndroidVersion();
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();

        } else {
            // write your logic here
        }

    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please Grant Permissions to upload profile photo",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            // write your logic code if permission already granted
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean location = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (location && writeExternal) {
                        // write your logic here
                    } else {
                        Snackbar.make(this.findViewById(android.R.id.content),
                                "Please Grant Permissions to upload profile photo",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        requestPermissions(
                                                new String[]{Manifest.permission
                                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                                PERMISSIONS_MULTIPLE_REQUEST);
                                    }
                                }).show();
                    }
                }
                break;
        }
    }


    private void findViews() {
        this.curso_1_completo = findViewById(R.id.curso_1_completo_check);
        this.CA = findViewById(R.id.CA_check);
        this.FFT = findViewById(R.id.FFT_check);
        this.ALEM = findViewById(R.id.ALEM_check);
        this.FP = findViewById(R.id.FP_check);
        this.FS = findViewById(R.id.FS_check);
        this.LMD = findViewById(R.id.LMD_check);
        this.TOC = findViewById(R.id.TOC_check);
        this.MP = findViewById(R.id.MP_check);
        this.ES = findViewById(R.id.ES_check);
        this.IES = findViewById(R.id.IES_check);

        //Asignaturas optativas
        this.PDM = findViewById(R.id.PDM_checkbox);
        this.DSD = findViewById(R.id.DSD_checkbox);


        this.generate_btn = findViewById(R.id.btn_generate);
        this.generate_btn.setOnClickListener(this);

        this.generate_group = findViewById(R.id.radio_group_generate);
        this.one_cuat_radio = findViewById(R.id.radio_button_1_cuat);
        this.two_cuat_radio = findViewById(R.id.radio_button_2_cuat);
        this.both_cuat_radio = findViewById(R.id.radio_button_both_cuat);


        this.generate_group.clearCheck();

    }

    public void onCheckboxClicked(View view){

        boolean checked  = ((CheckBox)view).isChecked();

        switch (view.getId()){

            //Para los cursos completos
            case R.id.curso_1_completo_check:

                if(checked){
                    this.CA.setChecked(true);
                    this.FFT.setChecked(true);
                    this.ALEM.setChecked(true);
                    this.FP.setChecked(true);
                    this.FS.setChecked(true);
                    this.LMD.setChecked(true);
                    this.TOC.setChecked(true);
                    this.MP.setChecked(true);
                    this.ES.setChecked(true);
                    this.IES.setChecked(true);

                    this.CA.setClickable(false);
                    this.FFT.setClickable(false);
                    this.ALEM.setClickable(false);
                    this.FP.setClickable(false);
                    this.FS.setClickable(false);
                    this.LMD.setClickable(false);
                    this.TOC.setClickable(false);
                    this.MP.setClickable(false);
                    this.ES.setClickable(false);
                    this.IES.setClickable(false);

                    //Meto el curso completo en el array y quito las asignaturas del curso del array de asignaturas_sueltas
                    this.cursos_completos.add("1"); //1 para el curso 1
                    removeAsignaturasFromCurso(1);


                }else{
                    this.CA.setChecked(false);
                    this.FFT.setChecked(false);
                    this.ALEM.setChecked(false);
                    this.FP.setChecked(false);
                    this.FS.setChecked(false);
                    this.LMD.setChecked(false);
                    this.TOC.setChecked(false);
                    this.MP.setChecked(false);
                    this.ES.setChecked(false);
                    this.IES.setChecked(false);

                    this.CA.setClickable(true);
                    this.FFT.setClickable(true);
                    this.ALEM.setClickable(true);
                    this.FP.setClickable(true);
                    this.FS.setClickable(true);
                    this.LMD.setClickable(true);
                    this.TOC.setClickable(true);
                    this.MP.setClickable(true);
                    this.ES.setClickable(true);
                    this.IES.setClickable(true);

                    this.cursos_completos.remove("1");
                    removeAsignaturasFromCurso(1);
                }

                break;

             //Para las asignaturas sueltas
            default:
                if(checked)
                    this.asignaturas_sueltas.add(((CheckBox) view).getText().toString());
                else
                    this.asignaturas_sueltas.remove(((CheckBox) view).getText().toString());


                break;
        }

    }


    public void removeAsignaturasFromCurso(int curso){

        switch (curso){
            case 1:
                this.asignaturas_sueltas.remove("CA");
                this.asignaturas_sueltas.remove("FFT");
                this.asignaturas_sueltas.remove("ALEM");
                this.asignaturas_sueltas.remove("FP");
                this.asignaturas_sueltas.remove("FS");

                this.asignaturas_sueltas.remove("LMD");
                this.asignaturas_sueltas.remove("TOC");
                this.asignaturas_sueltas.remove("MP");
                this.asignaturas_sueltas.remove("ES");
                this.asignaturas_sueltas.remove("IES");
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_generate:

                int eleccion = this.generate_group.getCheckedRadioButtonId();
                if(eleccion == R.id.radio_button_1_cuat)
                    this.cuat_chosen = "1";
                else if(eleccion == R.id.radio_button_2_cuat)
                    this.cuat_chosen = "2";
                else if(eleccion == R.id.radio_button_both_cuat)
                    this.cuat_chosen = "3";
                else
                    this.cuat_chosen = "";

                if(cuat_chosen.equals(""))
                    Toast.makeText(this, "Debe elegir el cuatrimestre a generar", Toast.LENGTH_SHORT).show();
                else if(this.cursos_completos.size() == 0 && this.asignaturas_sueltas.size() == 0)
                    Toast.makeText(this, "Debe marcar alguna asignatura", Toast.LENGTH_SHORT).show();
                else{
                    //Preparamos el intent a mandar a la activity

                    Intent intent = new Intent(this, DisplayHours.class);

                    //Serializamos los arrays
                    Gson gson = new Gson();
                    String jsonCursos = gson.toJson(this.cursos_completos);
                    String jsonAsigs = gson.toJson(this.asignaturas_sueltas);

                    intent.putExtra("CURSOS",jsonCursos);
                    intent.putExtra("ASIGS",jsonAsigs);
                    intent.putExtra("CUAT",this.cuat_chosen);

                    startActivity(intent);




                }
                break;


        }


    }
}