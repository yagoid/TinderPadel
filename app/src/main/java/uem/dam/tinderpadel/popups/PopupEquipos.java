package uem.dam.tinderpadel.popups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import uem.dam.tinderpadel.R;

public class PopupEquipos extends AppCompatActivity {

    TextView tvEquipo1;
    TextView tvEquipo2;
    TextView tvVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_equipos);

        tvEquipo1 = findViewById(R.id.tvEquipo1);
        tvEquipo2 = findViewById(R.id.tvEquipo2);
        tvVolver = findViewById(R.id.tvVolver);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int) (ancho * 0.85), (int) (alto * 0.5));

        String equipo1 = getIntent().getStringExtra("equipo1");
        String equipo2 = getIntent().getStringExtra("equipo2");

        tvEquipo1.setText(equipo1);
        tvEquipo2.setText(equipo2);

        // Hacemos que el textView sea clickable
        tvVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}