package com.example.iot_lab5_20201497;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResumenActivity extends AppCompatActivity {

    private TextView tvCaloriasRecomendadas, tvCaloriasConsumidas;
    private EditText etCaloriasComida;
    private double caloriasConsumidas = 0.0;
    private double caloriasRecomendadas = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Capturar a los elementos de la vista
        tvCaloriasRecomendadas = findViewById(R.id.tvCaloriasRecomendadas);
        tvCaloriasConsumidas = findViewById(R.id.tvCaloriasConsumidas);
        etCaloriasComida = findViewById(R.id.etCaloriasComida);
        Button btnAgregarComida = findViewById(R.id.btnAgregarComida);

        // Obtener las calorías recomendadas del intent
        caloriasRecomendadas = getIntent().getDoubleExtra("caloriasRecomendadas", 0.0);
        tvCaloriasRecomendadas.setText(String.format("%.2f", caloriasRecomendadas));

        // Configurar el botón de agregar comida
        btnAgregarComida.setOnClickListener(v -> {
            String caloriasComidaStr = etCaloriasComida.getText().toString();
            if (!caloriasComidaStr.isEmpty()) {
                double caloriasComida = Double.parseDouble(caloriasComidaStr);
                caloriasConsumidas += caloriasComida;
                tvCaloriasConsumidas.setText(String.format("%.2f", caloriasConsumidas));
            }
        });
    }
}