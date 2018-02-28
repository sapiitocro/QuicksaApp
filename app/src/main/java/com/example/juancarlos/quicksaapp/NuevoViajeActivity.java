package com.example.juancarlos.quicksaapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juancarlos.quicksaapp.Model.Viaje;
import com.example.juancarlos.quicksaapp.Utils.ViajeDBHelper;

public class NuevoViajeActivity extends AppCompatActivity {
    private Spinner spinnerOrigen;
    private Spinner spinnerDestino;
    private Spinner spinnerGuardia;
    private Spinner spinnerOperador;
    private Spinner spinnerMaterial;
    private EditText editTextPbruto;
    private TextView editTextPneto;
    private EditText editTextTara;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_viaje);
        spinnerMaterial = findViewById(R.id.IdMaterial);
        spinnerOrigen = findViewById(R.id.IdOrigen);
        spinnerDestino = findViewById(R.id.IdDestino);
        spinnerGuardia = findViewById(R.id.IdGuardia);
        spinnerOperador = findViewById(R.id.IdConductor);
        editTextPbruto = findViewById(R.id.IdPesoB);
        editTextPneto = findViewById(R.id.IdNeto);
        editTextTara = findViewById(R.id.IdTara);
        FloatingActionButton actionButton = findViewById(R.id.fab);

        editTextPneto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Neto();
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarViaje();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Materiales,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaterial.setAdapter(adapter);
        spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int[] origenes = {R.array.OrigenMineral, R.array.OrigenMineralCancha, R.array.OrigenRelave
                        , R.array.OrigenDesmonte, R.array.OrigenRellenoCementado, R.array.OrigenAgregado, R.array.OrigenVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios};

                int[] destinos = {R.array.DestinoMineral, R.array.DestinoMineralCancha, R.array.DestinoRelave
                        , R.array.DestinoDesmonte, R.array.DestinoRellenoCementado, R.array.DestinoAgregado, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios};
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        spinnerMaterial.getContext(),
                        origenes[position],
                        android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOrigen.setAdapter(adapter);

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                        spinnerMaterial.getContext(),
                        destinos[position],
                        android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDestino.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.Guardia,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuardia.setAdapter(adapter1);
        spinnerGuardia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int[] operadores = {R.array.OperadoresA, R.array.OperadoresB, R.array.OperadoresC};
                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                        spinnerGuardia.getContext(),
                        operadores[position],
                        android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOperador.setAdapter(adapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void GuardarViaje() {
        String operador = spinnerOperador.getSelectedItem().toString();
        String guardia = spinnerGuardia.getSelectedItem().toString();
        String origen = spinnerOrigen.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();
        String material = spinnerMaterial.getSelectedItem().toString();
        String pbruto = editTextPbruto.getText().toString();
        String tara = editTextTara.getText().toString();


        try {
            Double sumapbruto = Double.parseDouble(pbruto);
            Double sumatara = Double.parseDouble(tara);


            Double neto = sumapbruto - sumatara;
            if (neto >= 0) {

                String pneto = String.valueOf(neto);

                ViajeDBHelper dbHelper = new ViajeDBHelper(this);

                Viaje viaje = new Viaje(operador, guardia, origen, destino, material, pbruto, pneto, tara);
                dbHelper.saveNewViaje(viaje);

                Regresar();
            } else {
                Toast.makeText(this, "RESULTADO INVALIDO, VERIFIQUE EL PESO BRUTO Y/O LA TARA", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "RESULTADO INVALIDO, VERIFIQUE EL PESO BRUTO Y/O LA TARA", Toast.LENGTH_SHORT).show();
        }




    }

    private void Regresar() {
        startActivity(new Intent(NuevoViajeActivity.this, MainActivity.class));
    }


    public void Neto() {
        try {
            String pbruto = editTextPbruto.getText().toString();
            String tara = editTextTara.getText().toString();
            Double sumapbruto = Double.parseDouble(pbruto);
            Double sumatara = Double.parseDouble(tara);

            Double neto = sumapbruto - sumatara;
            if (neto >= 0) {
                String pneto = String.valueOf(neto);
                editTextPneto.setText(pneto);
            } else {
                Toast.makeText(this, "RESULTADO INVALIDO, VERIFIQUE EL PESO BRUTO Y/O LA TARA", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "RESULTADO INVALIDO, VERIFIQUE EL PESO BRUTO Y/O LA TARA", Toast.LENGTH_SHORT).show();
        }

    }
}
