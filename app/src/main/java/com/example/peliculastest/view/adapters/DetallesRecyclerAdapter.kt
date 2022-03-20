package com.example.peliculastest.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculastest.R
import com.example.peliculastest.view.vo.ParClaveValor

class DetallesRecyclerAdapter(val listaClaves:List<ParClaveValor>) : RecyclerView.Adapter<DetallesRecyclerAdapter.DetalleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DetalleViewHolder(layoutInflater.inflate(R.layout.detalle_item, parent, false))
    }

    override fun onBindViewHolder(holder: DetalleViewHolder, position: Int) {
        val parClaveValor:ParClaveValor = this.listaClaves.get(position)
        holder.bind(parClaveValor)
    }

    override fun getItemCount(): Int {
        return listaClaves.size
    }

    class DetalleViewHolder( view: View) : RecyclerView.ViewHolder(view)  {

        private val titulo = view.findViewById<TextView>(R.id.lblTitulo)
        private val valor = view.findViewById<TextView>(R.id.lblPropiedad)

        fun bind( parClaveValor: ParClaveValor){
            titulo.text = parClaveValor.titulo
            valor.text = parClaveValor.valor
        }
    }

}