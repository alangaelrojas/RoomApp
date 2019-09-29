package com.apps.aggr.roomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.aggr.roomapp.db.entity.Profesor;

import java.util.ArrayList;
import java.util.List;

public class AdapterProfesores extends RecyclerView.Adapter<AdapterProfesores.HolderProfesores> {

    List<Profesor> listProfesores = new ArrayList<>();
    Context c;
    OnClickProfesor onClickProfesor;

    public AdapterProfesores(Context c, OnClickProfesor onClickProfesor) {
        this.c = c;
        this.onClickProfesor = onClickProfesor;
    }

    public void addProfesor(Profesor profesor){
        listProfesores.add(profesor);
        notifyItemInserted(listProfesores.size());
    }

    public void deleteProfesor(Profesor profesor) {
        listProfesores.remove(profesor);
        notifyItemRemoved(listProfesores.size());
    }

    public void updateProfesor(Profesor profesor){
        listProfesores.set(listProfesores.indexOf(profesor), profesor);
        notifyDataSetChanged();
    }
    public void deleteAll() {
        listProfesores.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HolderProfesores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_profesor, parent, false);
        return new HolderProfesores(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProfesores holder, int position) {

        holder.nombre.setText(listProfesores.get(position).getName());
        holder.email.setText(listProfesores.get(position).getEmail());
        if(listProfesores.get(position).isActive()){
            holder.status.setText("ACTIVO");
            holder.imgStatus.setImageResource(R.drawable.ic_ok);
        }else{
            holder.status.setText("INACTIVO");
            holder.imgStatus.setImageResource(R.drawable.ic_error);
        }
        holder.area.setText(listProfesores.get(position).getTypeProfesor());
    }

    @Override
    public int getItemCount() {
        return listProfesores.size();
    }

    public interface OnClickProfesor{
        void onClickProfesor(Profesor profesor);
    }

    public class HolderProfesores extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nombre, email, area, status;
        ImageView imgStatus;
        ConstraintLayout item;

        public HolderProfesores(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txt_nombre_profesor);
            area = itemView.findViewById(R.id.txt_area_profesor);
            email = itemView.findViewById(R.id.txt_emial_profesor);
            status = itemView.findViewById(R.id.txt_status_profesor);
            imgStatus = itemView.findViewById(R.id.imageView);
            item = itemView.findViewById(R.id.item_profesor);

            item.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.item_profesor){
                onClickProfesor.onClickProfesor(listProfesores.get(getAdapterPosition()));
            }
        }
    }
}
