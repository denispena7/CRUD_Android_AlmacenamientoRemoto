package es.studium.mispedidospendientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.studium.mispedidospendientes.modelos.Pedido;
import es.studium.mispedidospendientes.modelos.Tienda;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.MyViewHolder>
{
    private List<Pedido> listaDePedidos;

    public PedidosAdapter(){}

    public PedidosAdapter(List<Pedido> pedidos)
    {
        this.listaDePedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View filaPedido = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_pedido,
                viewGroup, false);
        return new PedidosAdapter.MyViewHolder(filaPedido);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.MyViewHolder myViewHolder, int i)
    {
        // Obtener la frase de nuestra lista gracias al índice i
        Pedido pedidos = listaDePedidos.get(i);
        // Obtener los datos de la lista
        String nPedido = pedidos.getDescripcion();
        String nTienda = pedidos.getnTienda();
        LocalDate fechaE = pedidos.getFechaEntrega();

        // Y poner a los TextView los datos con setText
        myViewHolder.descripcion.setText(nPedido);
        myViewHolder.tienda.setText(nTienda);
        myViewHolder.fecha.setText(formatearFecha(fechaE));
    }
    @Override
    public int getItemCount()
    {
        return listaDePedidos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView descripcion, tienda, fecha;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            this.descripcion = itemView.findViewById(R.id.txtArticulo);
            this.tienda = itemView.findViewById(R.id.txtTienda);
            this.fecha = itemView.findViewById(R.id.txtFechaEntrega);
        }
    }

    public String formatearFecha(LocalDate fecha)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
}
