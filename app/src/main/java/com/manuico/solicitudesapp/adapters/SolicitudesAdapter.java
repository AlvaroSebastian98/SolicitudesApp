package com.manuico.solicitudesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuico.solicitudesapp.R;
import com.manuico.solicitudesapp.models.Solicitud;
import com.manuico.solicitudesapp.services.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.ViewHolder> {

    private List<Solicitud> solicitudes;

    public SolicitudesAdapter(){
        this.solicitudes = new ArrayList<>();
    }

    public void setSolicitudes(List<Solicitud> solicitudes){
        this.solicitudes = solicitudes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;
        public TextView correoText;
        public TextView tipoSolicitudText;
        public TextView motivoText;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            correoText = itemView.findViewById(R.id.correo_text);
            tipoSolicitudText = itemView.findViewById(R.id.tipo_solicitud_text);
            motivoText = itemView.findViewById(R.id.motivo_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Solicitud solicitud = this.solicitudes.get(position);

        viewHolder.correoText.setText("De " + solicitud.getCorreo());
        viewHolder.tipoSolicitudText.setText(solicitud.getTipoSolicitud());
        viewHolder.motivoText.setText(solicitud.getMotivo());

        String url = ApiService.API_BASE_URL + "/solicitudes/images/" + solicitud.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return this.solicitudes.size();
    }

}