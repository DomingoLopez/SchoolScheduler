package com.classescalendar.classescalendar.ui;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import com.classescalendar.classescalendar.BuildConfig;
import com.classescalendar.classescalendar.classes.Cell;
import com.classescalendar.classescalendar.classes.CellAsignaturas;
import com.classescalendar.classescalendar.classes.CellInformativa;
import com.classescalendar.classescalendar.classes.CellType;
import com.classescalendar.classescalendar.data.AsignaturasRepository;
import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.classescalendar.classescalendar.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DisplayHours extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener,ConflictDialog.NoticeDialogListener,InfoDialog.InfoDialogListener {

    GridView gridView;
    ConstraintLayout constraintL;
    TextView text;
    CellAdapter adapter;
    ImageView btnPDF;
    AsignaturasRepository asignaturasRepository;
    public static final int franjas_horarias = 12;
    public static final int hora_inicio = 9;
    public static final int num_columnas = 6;

    //Lista de asignaturas finales a mostrar
    List<AsignaturaEntity> asignaturaEntityList;

    //Matriz del gridView
    Cell matriz[][];

    //Cuatrimestre elegido
    String cuat_chosen;
    //Cursos elegidos
    List<Integer> cursos_elegidos;
    //Asignaturas sueltas elegidas
    List<String> asigs_elegidas;

    ArrayList<Cell> cells;

    //Array con el nombre de los días
    String[]nombre_dias = {"","L","M","X","J","V"};

    //Bitmap para crear el pdf
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hours);

        //Obtenemos el intent
        String cursos = getIntent().getStringExtra("CURSOS");
        String asigs = getIntent().getStringExtra("ASIGS");
        this.cuat_chosen = getIntent().getStringExtra("CUAT");

        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        Type typeInt = new TypeToken<List<Integer>>(){}.getType();

        this.cursos_elegidos = gson.fromJson(cursos,typeInt);
        this.asigs_elegidas = gson.fromJson(asigs,type);



        findViews();
        this.asignaturasRepository = new AsignaturasRepository(getApplication());
        getAsigsFromDB();

        //createAsigs();
        initCalendar();
        adapter = new CellAdapter(getApplicationContext(),this.cells);
        this.gridView.setAdapter(adapter);
    }




    private void getAsigsFromDB() {

            List<AsignaturaEntity> asignaturasPorCursoCompleto =  this.asignaturasRepository.getAsignaturasByCursos(this.cursos_elegidos);
            List<AsignaturaEntity> asignaturasSueltas = this.asignaturasRepository.getAsignaturasByABR(this.asigs_elegidas);

            this.asignaturaEntityList = asignaturasPorCursoCompleto;
            for(int i = 0; i<asignaturasSueltas.size(); i++){
                this.asignaturaEntityList.add(asignaturasSueltas.get(i));
            }

    }


    private void findViews() {
        this.gridView = findViewById(R.id.main_grid);
        this.gridView.setBackgroundColor(Color.WHITE);
        this.gridView.setVerticalSpacing(10);
        this.gridView.setHorizontalSpacing(10);
        this.btnPDF = findViewById(R.id.btnPDF);
        this.btnPDF.setOnClickListener(this);


        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cell cell = cells.get(position);
                if(cell.getType() == CellType.ASIGNATURAS ){
                    if(cell.getAsignaturasEntity().size() == 1){
                        DialogFragment dialogInfo = new InfoDialog(cell.getAsignaturasEntity().get(0),DisplayHours.this);
                        dialogInfo.show(getSupportFragmentManager(),"info");
                    }else if(cell.getAsignaturasEntity().size() >1){
                        DialogFragment dialogoConflicto = new ConflictDialog(cell.getAsignaturasEntity(), DisplayHours.this);
                        dialogoConflicto.show(getSupportFragmentManager(),"conflicto");
                    }
                }
            }


        });

        this.constraintL = findViewById(R.id.constr_display);
    }






    private void initCalendar() {
        clearMatrix();
        initializeBlank();
        initializeAsigs();

    }

    private void clearMatrix(){
        this.matriz = new Cell[franjas_horarias][num_columnas];
    }

    private void initializeBlank() {
        int hora_ini = hora_inicio;

        for(int i = 0; i< franjas_horarias; i++){
            for(int j = 0; j< num_columnas; j++){

                if (i == 0)
                    matriz[i][j] = new CellInformativa(CellType.DIAS,nombre_dias[j]);
                else if (j == 0) {
                    matriz[i][j] = new CellInformativa(CellType.HORAS, String.format("%02d", hora_ini) + ":00\n" + String.format("%02d", hora_ini + 1) + ":00");
                    hora_ini++;
                }else{
                    //Rellenamos con Celdas de asignatura con array vacío
                    matriz[i][j] = new CellAsignaturas(CellType.ASIGNATURAS,new ArrayList<AsignaturaEntity>());
                }
            }

        }
    }

    private void initializeAsigs(){
        //Ahora recorermos el array de asignaturas, asígnandolas

        for(int i = 0; i <asignaturaEntityList.size(); i++){

            int dia = asignaturaEntityList.get(i).dia;
            int hora = asignaturaEntityList.get(i).hora_ini;

            if(asignaturaEntityList.get(i).hora_fin - asignaturaEntityList.get(i).hora_ini == 2){
                //Una doble
                matriz[(hora - hora_inicio) + 1][dia].setAsignatura(asignaturaEntityList.get(i));
                matriz[(hora - hora_inicio) + 2][dia].setAsignatura(asignaturaEntityList.get(i));
            }else{
                //Una simple
                matriz[(hora - hora_inicio) + 1][dia].setAsignatura(asignaturaEntityList.get(i));
            }
        }


        this.cells = new ArrayList<>();
        //Pasamos la matriz a array y la devolvemos
        for(int i = 0; i< franjas_horarias; i++){
            for(int j = 0; j< num_columnas; j++){
                cells.add(matriz[i][j]);
            }
        }



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onElegida(int elegida, String[] names) {


        for(int i = 0; i< this.asignaturaEntityList.size(); i++){
            for(int j = 0; j<names.length; j++){
                if(j != elegida){
                    if(this.asignaturaEntityList.get(i).abr.equals(names[j]))
                        this.asignaturaEntityList.remove(i);
                }
            }
        }
        initCalendar();
        adapter = new CellAdapter(getApplicationContext(),this.cells);
        this.gridView.setAdapter(adapter);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


    //Métodos para eliminar la asignatura desde el dialogo de info
    @Override
    public void aEliminar(String abr) {
        for(int i = 0; i< this.asignaturaEntityList.size(); i++){
                if(this.asignaturaEntityList.get(i).abr.equals(abr)){
                        this.asignaturaEntityList.remove(i);
                }
        }
        initCalendar();
        adapter = new CellAdapter(getApplicationContext(),this.cells);
        this.gridView.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnPDF:

                Log.d("size"," "+gridView.getWidth() +"  "+gridView.getHeight());
                bitmap = loadBitmapFromView(gridView, gridView.getWidth(), gridView.getHeight());
                createPdf();
                break;
        }

    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }


    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/horario_generado.pdf";
        File filePath;
        filePath = new File(targetPdf);
        //File filePath = new File("/sdcard/my_calendar.pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF con horario creado", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }


    private void openGeneratedPDF(){
        File file = new File("/sdcard/horario_generado.pdf");


        if (file.exists())
        {

            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);

            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(DisplayHours.this, "No existe una aplicación para abrir el pdf", Toast.LENGTH_LONG).show();
            }
        }
    }









    /*CREACIÓN DE ASIGNATURAS PUNTUAL*/
    private void createAsigs() {
        AsignaturaEntity asig = new AsignaturaEntity(
                1,
                1,
                "A",
                "CA",
                "Cálculo",
                "Matemáticas puras y duras!",
                6,
                11,
                12 ,
                3,
                "T",
                "#80d6ff"
        );
        AsignaturaEntity asig1 = new AsignaturaEntity(
                1,
                1,
                "A",
                "TOC",
                "Tecnología y Organización de Computadores",
                "Circuitillos!!",
                6,
                10,
                12  ,
                4,
                "T",
                "#ff94c2"
        );
        /*AsignaturaEntity asig2 = new AsignaturaEntity(
                1,
                1,
                "A",
                "MP",
                "Metodología de la programación",
                "Asignatura de primer curso que ...",
                6,
                8,
                10,
                2,
                "T",
                "#d7ffd9"
        );
        AsignaturaEntity asig3 = new AsignaturaEntity(
                1,
                1,
                "B",
                "ALEM",
                "Álgebra",
                "Asignatura de primer curso que ...",
                6,
                11,
                13,
                1,
                "T",
                "#ff94c2"
        );*/


        this.asignaturasRepository.insertAsignatura(asig);
        this.asignaturasRepository.insertAsignatura(asig1);
        /*this.asignaturasRepository.insertAsignatura(asig3);*/
    }



}