package cursoandroid.com.pr01_visorimagenes;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;

public class main extends AppCompatActivity {


    private ImageButton next,back;
    private ImageView imgVisor;

    private int i;
    private File archivoImagen;
    private File listaImagenes[];

    FilenameFilter filtro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        next=(ImageButton)findViewById(R.id.imgb_next);
        back=(ImageButton)findViewById(R.id.imgb_previous);
        imgVisor=(ImageView)findViewById(R.id.img_visor);
        

        //METODO PARA FILTRAR LOS ARCHIVOS .JPG Y .PNG
        filtrar();

        accesoSDcard();

        cargarImagen(0);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextM();
            }
        });
        
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backM();
            }
        });
    }


    //validar si se pudo acceder a la SD del dispositivo
    private void accesoSDcard() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                archivoImagen= new File(Environment.getExternalStorageDirectory()+File.separator+"imagenes");


            if(archivoImagen.isDirectory()){

                //filtramos el archivo para agregarlo a la lista
                listaImagenes=archivoImagen.listFiles(filtro);
            }
        }else{
           // archivoImagen= new File(Environment.getRootDirectory()+File.separator+"imagenes");
        }


    }

    private void filtrar() {
        filtro = new FilenameFilter() {
            @Override
            public boolean accept(File archivo, String nomArchivo) {

                if(nomArchivo.endsWith(".jpg")||nomArchivo.endsWith(".png")){
                 return true;
                }else{
                    return false;
                }

            }
        };

    }

    private void backM() {
        i=i-1;
        if(i==-1){
            i=listaImagenes.length-1;
        }
        cargarImagen(i);
    }

    private void nextM() {
        i=i+1;
        if(i==listaImagenes.length){
            i=0;
        }
        cargarImagen(i);
    }

    public void cargarImagen(int i){

        /*
        //LLENAR EL OBJETO NATIVO ASOCIADO CON EL MAPA DE BITS
        if(((BitmapDrawable)imgVisor.getDrawable())!=null){
            ((BitmapDrawable)imgVisor.getDrawable()).getBitmap().recycle();
            Log.e("QUEA","SI");
        }

        */
        try{
            Glide.with(this)
                    .load(listaImagenes[i])
                    .into(imgVisor);
        }catch (Exception e){}

        imgVisor.setScaleType(ImageView.ScaleType.FIT_XY);



    }
}
