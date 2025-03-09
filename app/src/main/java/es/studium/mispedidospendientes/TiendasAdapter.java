package es.studium.mispedidospendientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.studium.mispedidospendientes.modelos.Tienda;

public class TiendasAdapter extends RecyclerView.Adapter<TiendasAdapter.MyViewHolder>
{
    private List<Tienda> listaDeTiendas;

    public TiendasAdapter(List<Tienda> tiendas)
    {
        this.listaDeTiendas = tiendas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View filaTienda = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_tienda,
                viewGroup, false);
        return new MyViewHolder(filaTienda);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        // Obtener la frase de nuestra lista gracias al índice i
        Tienda tienda = listaDeTiendas.get(i);
        // Obtener los datos de la lista
        String nTienda = tienda.getNombreTienda();

        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nTienda);
    }
    @Override
    public int getItemCount()
    {
        return listaDeTiendas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombre;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.txtTienda);
        }
    }
}
