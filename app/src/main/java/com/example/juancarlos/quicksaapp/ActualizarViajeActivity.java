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

import javax.crypto.interfaces.PBEKey;

public class ActualizarViajeActivity extends AppCompatActivity {

    private Spinner Operador;
    private Spinner Material;
    private Spinner Origen;
    private Spinner Destino;
    private Spinner Guardia;
    private EditText Pbruto;
    private TextView Pneto;
    private EditText Tara;

    private ViajeDBHelper dbHelper;
    private long receivedViajeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_viaje);
        Operador = findViewById(R.id.IdConductorActualizar);
        Material = findViewById(R.id.IdMaterialActualizar);
        Origen = findViewById(R.id.IdOrigenActualizar);
        Destino = findViewById(R.id.IdDestinoActualizar);
        Guardia = findViewById(R.id.IdGuardiaActualizar);
        Pbruto = findViewById(R.id.IdPesoBActualizar);
        Pneto = findViewById(R.id.IdNetoActualizar);
        Tara = findViewById(R.id.IdTaraActualizar);
        FloatingActionButton button = findViewById(R.id.fabActualizar);

        Pneto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Neto();
            }
        });
        dbHelper = new ViajeDBHelper(this);

        try {
            receivedViajeId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViaje();

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Materiales,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Material.setAdapter(adapter);
        Material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int[] origenes = {R.array.OrigenMineral, R.array.OrigenMineralCancha, R.array.OrigenRelave
                        , R.array.OrigenDesmonte, R.array.OrigenRellenoCementado, R.array.OrigenAgregado, R.array.OrigenVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios};

                int[] destinos = {R.array.DestinoMineral, R.array.DestinoMineralCancha, R.array.DestinoRelave
                        , R.array.DestinoDesmonte, R.array.DestinoRellenoCementado, R.array.DestinoAgregado, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios, R.array.DestinoVarios};
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        Material.getContext(),
                        origenes[position],
                        android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Origen.setAdapter(adapter);

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                        Material.getContext(),
                        destinos[position],
                        android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Destino.setAdapter(adapter1);
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
        Guardia.setAdapter(adapter1);

        Guardia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int[] operadores = {R.array.OperadoresA, R.array.OperadoresB, R.array.OperadoresC};
                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                        Guardia.getContext(),
                        operadores[position],
                        android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Operador.setAdapter(adapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateViaje() {
        String operador = Operador.getSelectedItem().toString().trim();
        String material = Material.getSelectedItem().toString().trim();
        String origen = Origen.getSelectedItem().toString().trim();
        String destino = Destino.getSelectedItem().toString().trim();
        String guardia = Guardia.getSelectedItem().toString().trim();
        String pbruto = Pbruto.getText().toString().trim();
        String tara = Tara.getText().toString().trim();


        try {
            Double sumapbruto = Double.parseDouble(pbruto);
            Double sumatara = Double.parseDouble(tara);


            Double neto = sumapbruto - sumatara;
            if (neto >= 0) {

                String paneto = String.valueOf(neto);

                Viaje updateViaje = new Viaje(operador, guardia, origen, destino, material, pbruto, paneto, tara);
                dbHelper.updateViajeRecord(receivedViajeId, this, updateViaje);

                //

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
        startActivity(new Intent(this, MainActivity.class));
    }

    public void Neto() {
        try {
            String pbruto = Pbruto.getText().toString();
            String tara = Tara.getText().toString();
            int sumapbruto = Integer.parseInt(pbruto);
            int sumatara = Integer.parseInt(tara);

            int neto = sumapbruto - sumatara;
            if (neto >= 0) {
                String pneto = String.valueOf(neto);
                Pneto.setText(pneto);
            } else {
                Toast.makeText(this, "RESULTADO INVALIDO, VERIFIQUE EL PESO BRUTO Y/O LA TARA", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "RESULTADO INVALIDO, VERIFIQUE EL PESO BRUTO Y/O LA TARA", Toast.LENGTH_SHORT).show();
        }

    }

}
