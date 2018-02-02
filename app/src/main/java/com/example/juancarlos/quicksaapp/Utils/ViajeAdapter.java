package com.example.juancarlos.quicksaapp.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juancarlos.quicksaapp.Model.Viaje;
import com.example.juancarlos.quicksaapp.R;


import java.util.List;


public class ViajeAdapter extends RecyclerView.Adapter<ViajeAdapter.ViewHolder> {
    private List<Viaje> mViajeList;
    private Context mContext;
    private RecyclerView mRecyclev;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Operador;
        TextView Material;
        TextView Origen;
        TextView Destino;

        public View layout;


        ViewHolder(View v) {
            super(v);
            layout = v;
            Operador = v.findViewById(R.id.idoperador);
            // Volquete = (TextView) v.findViewById(R.id.idvolquete);
            Material = v.findViewById(R.id.idmaterial);
            Origen = v.findViewById(R.id.idorigen);
            Destino = v.findViewById(R.id.iddestino);
        }
    }

    public ViajeAdapter(List<Viaje> myDataset, Context context, RecyclerView recyclerView) {
        mViajeList = myDataset;
        mContext = context;
        mRecyclev = recyclerView;
    }

    @Override
    public ViajeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row, parent, false);
        return new ViewHolder(v);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Viaje viaje = mViajeList.get(position);
        holder.Operador.setText("Operador: " + viaje.getOperador());
        //  holder.Volquete.setText("Volquete: ESTA DESHABILITADO" );
        holder.Material.setText("Material: " + viaje.getMaterial());
        holder.Origen.setText("Origen: " + viaje.getOrigen());
        holder.Destino.setText("Destino: " + viaje.getDestino());

        holder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Escoge una opcion");
                builder.setMessage("Actualizar o borrar Viaje??");
                builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActualizarActivity();
                    }
                });
                builder.setNeutralButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViajeDBHelper dbHelper = new ViajeDBHelper(mContext);
                        dbHelper.deleteViajeRecord(viaje.getId(), mContext);

                        mViajeList.remove(position);
                        mRecyclev.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeRemoved(position, mViajeList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }


    private void ActualizarActivity() {

    }

    @Override
    public int getItemCount() {
        return mViajeList.size();
    }


}
