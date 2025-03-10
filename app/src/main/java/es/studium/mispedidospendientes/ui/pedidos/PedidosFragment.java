package es.studium.mispedidospendientes.ui.pedidos;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import es.studium.mispedidospendientes.PedidosAdapter;
import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.RecyclerTouchListener;
import es.studium.mispedidospendientes.crudPedidos.AccesoRemotoPedidos;
import es.studium.mispedidospendientes.modelos.Pedido;

public class PedidosFragment extends Fragment implements View.OnClickListener
{
    private RecyclerView recyclerViewPedidos;
    private PedidosAdapter adapter;
    private List<Pedido> listaPedidos;
    private FloatingActionButton floatingActionButtonP;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        recyclerViewPedidos = root.findViewById(R.id.recyclerViewPedidos);
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dentro de onCreateView, justo después de configurar el RecyclerView:
        recyclerViewPedidos.addOnItemTouchListener(new RecyclerTouchListener(requireContext(),
                recyclerViewPedidos, new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Pedido pedidoSeleccionado = listaPedidos.get(position);
                // Acción al hacer click en un elemento de la lista
                // Se muestra el diálogo de edición
                DlgEdicionPedido dlgEdicion = new DlgEdicionPedido(PedidosFragment.this, pedidoSeleccionado);
                dlgEdicion.setCancelable(false);
                dlgEdicion.show(getParentFragmentManager(), "Edicion Pedidos");
            }

            @Override
            public void onLongClick(View view, int position)
            {
                // Acción al hacer un long-click en un elemento
                Pedido pedidoSeleccionado = listaPedidos.get(position);
                // Acción al hacer click en un elemento de la lista
                // Se muestra el diálogo de baja
                DlgBajaPedido dlgBaja = new DlgBajaPedido(PedidosFragment.this, pedidoSeleccionado.getId());
                dlgBaja.setCancelable(false);
                dlgBaja.show(getParentFragmentManager(), "Baja Pedidos");
            }
        }));


        floatingActionButtonP = root.findViewById(R.id.floatingActionButtonPedidos);
        floatingActionButtonP.setOnClickListener(this);

        // Permitir conexiones de red en el hilo principal
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Obtener datos desde AccesoRemoto y asignar el adaptador
        cargarPedidos();

        return root;
    }

    // Función para cargar los pedidos pendientes en el recycler view
    public void cargarPedidos()
    {
        AccesoRemotoPedidos accesoRemotoPedidos = new AccesoRemotoPedidos();
        listaPedidos = accesoRemotoPedidos.obtenerPedidos();
        adapter = new PedidosAdapter(listaPedidos);
        recyclerViewPedidos.setAdapter(adapter);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.floatingActionButtonPedidos)
        {
            // Lógica para el botón flotante
            // Aparece el diálogo de altas
            DlgAltaPedido dlgAltaPedidos = new DlgAltaPedido(this);
            dlgAltaPedidos.setCancelable(false);
            dlgAltaPedidos.show(getParentFragmentManager(), "Alta Pedidos");
        }
    }
}