package com.example.iot_lab5_20201497;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etPeso, etAltura, etEdad;
    private RadioGroup rgGenero;
    private Spinner spNivelActividad;
    private Spinner spObjetivo;
    private TextView tvResultadoCalorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Capturar elementos de la vista
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        etEdad = findViewById(R.id.etEdad);
        rgGenero = findViewById(R.id.rgGenero);
        spNivelActividad = findViewById(R.id.spNivelActividad);
        spObjetivo = findViewById(R.id.spObjetivo);
        tvResultadoCalorias = findViewById(R.id.tvResultadoCalorias);

        // Configurar el botón de calcular
        Button btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(v -> calcularCalorias());
    }

    private void calcularCalorias() {
        // Obtener los valores ingresados por el usuario
        double peso = Double.parseDouble(etPeso.getText().toString());
        double altura = Double.parseDouble(etAltura.getText().toString());
        int edad = Integer.parseInt(etEdad.getText().toString());
        int genero = rgGenero.getCheckedRadioButtonId();
        String objetivo = spObjetivo.getSelectedItem().toString();
        String nivelActividad = spNivelActividad.getSelectedItem().toString();

        // Cálculo del TMB (Tasa Metabólica Basal)
        double tmb;
        if (genero == R.id.rbHombre) {
            tmb = 88.362 + (13.397 * peso) + (4.799 * altura) - (5.677 * edad);
        } else {
            tmb = 447.593 + (9.247 * peso) + (3.098 * altura) - (4.330 * edad);
        }

        // Multiplicador según el nivel de actividad
        double calorias = getCalorias(nivelActividad, objetivo, tmb);
        tvResultadoCalorias.setText(String.format("%.2f", calorias));
    }

    private static double getCalorias(String nivelActividad, String objetivo, double tmb) {
        double multiplicador;
        switch (nivelActividad) {
            case "Sedentario": multiplicador = 1.2; break;
            case "Ligeramente Activo": multiplicador = 1.375; break;
            case "Moderadamente Activo": multiplicador = 1.55; break;
            case "Muy Activo": multiplicador = 1.725; break;
            default: multiplicador = 1.0; break;
        }

        // Incremento según el objetivo
        int incremento = 0; // Valor predeterminado (Mantener peso)
        switch (objetivo) {
            case "Bajar de peso":
                incremento = -300;
                break;
            case "Subir de peso":
                incremento = 500;
                break;
        }

        // Cálculo de las calorías recomendadas
        return tmb * multiplicador + incremento;
    }

}