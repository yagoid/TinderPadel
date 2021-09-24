package uem.dam.tinderpadel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uem.dam.tinderpadel.popups.PopupEquipos;

public class MainActivity extends AppCompatActivity {

    EditText etJugador1;
    EditText etJugador2;
    EditText etJugador3;
    EditText etJugador4;
    Button btnStart;
    TextView tvResultado;

    private String player1;
    private String player2;
    private String player3;
    private String player4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        etJugador1 = findViewById(R.id.etJugador1);
        etJugador2 = findViewById(R.id.etJugador2);
        etJugador3 = findViewById(R.id.etJugador3);
        etJugador4 = findViewById(R.id.etJugador4);
        btnStart = findViewById(R.id.btnStart);
        tvResultado = findViewById(R.id.tvResultado);


        // Mostramos los nombres puestos con anterioridad en los EditText correspondientes
        final SharedPreferences prefs = recordarNombres();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player1 = etJugador1.getText().toString();
                player2 = etJugador2.getText().toString();
                player3 = etJugador3.getText().toString();
                player4 = etJugador4.getText().toString();

                // Metemos los 4 jugadores en un arrray.
                String[] jugadores = {player1, player2, player3, player4};

                // Validamos si los datos introducidos son correctos, y si es así se eligen los equipos aleatoriamente
                String msj = validarDatos(jugadores);

                if (msj != null) {
                    Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG).show();

                } else {
                    // Cuando el usuario pulse START, seguardarán los nombres escritos para otra ocasión
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("jugador1", etJugador1.getText().toString());
                    editor.putString("jugador2", etJugador2.getText().toString());
                    editor.putString("jugador3", etJugador3.getText().toString());
                    editor.putString("jugador4", etJugador4.getText().toString());
                    editor.commit();

                    // Elegir los equipos alatoriamente.
                    generarParejas(jugadores);
                }
            }
        });
    }

    private void generarParejas(String[] jugadores) {
        int[] numJugador = new int[2];
        int numAleatorio = 5;
        int numAnteriorAle;
        int i = 0;

        List<String> equipo1 = new ArrayList<>();
        List<String> equipo2 = new ArrayList<>();

        // Rellenamos mediante un while el primer equipo con númros aleatorios
        while (i != 2) {
            numAnteriorAle = numAleatorio;
            numAleatorio = (int) Math.floor(Math.random() * (3 - 0 + 1) + 0);
            while (numAnteriorAle == numAleatorio) {
                numAleatorio = (int) Math.floor(Math.random() * (3 - 0 + 1) + 0);
            }
            equipo1.add(jugadores[numAleatorio]);

            // Meto en la lista "numJugadores" el numero del jugador aleatorio
            numJugador[i] = numAleatorio;
            i++;
        }

        // Los jugadores restantes que no se hayan elegido en el equipo 1, se meterán en el equipo 2
        i = 0;
        while (i != 4) {

            if (i != numJugador[0] && i != numJugador[1]) {
                equipo2.add(jugadores[i]);
            }
            i++;
        }

        // Se convierte los equipos a strig para mandarlos
        String jugEquipo1 = String.join(", ", equipo1);
        String jugEquipo2 = String.join(", ", equipo2);

        //tvResultado.setText(equipo1.toString() + " VS " + equipo2.toString());

        Intent intent = new Intent(MainActivity.this, PopupEquipos.class);
        intent.putExtra("equipo1", jugEquipo1);
        intent.putExtra("equipo2", jugEquipo2);
        startActivity(intent);

    }

    private SharedPreferences recordarNombres() {
        final SharedPreferences prefs = getSharedPreferences("recordarmeWallet", Context.MODE_PRIVATE);

        player1 = prefs.getString("jugador1", "");
        player2 = prefs.getString("jugador2", "");
        player3 = prefs.getString("jugador3", "");
        player4 = prefs.getString("jugador4", "");

        etJugador1.setText(player1);
        etJugador2.setText(player2);
        etJugador3.setText(player3);
        etJugador4.setText(player4);
        return prefs;
    }

    // Comprobamos que los datos introducidos no estén vacíos
    private String validarDatos(String[] jugadores)  {

        String msj = null;
        if (jugadores[0].isEmpty()) {
            etJugador1.setError("Required");
            msj = getString(R.string.no_datos);
        } else if (jugadores[1].isEmpty()) {
            etJugador2.setError("Required");
            msj = getString(R.string.no_datos);
        } else if (jugadores[2].isEmpty()) {
            etJugador3.setError("Required");
            msj = getString(R.string.no_datos);
        } else if (jugadores[3].isEmpty()) {
            etJugador4.setError("Required");
            msj = getString(R.string.no_datos);
        }

        return msj;
    }
}