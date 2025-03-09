package es.studium.mispedidospendientes.ui.pedidos;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import es.studium.mispedidospendientes.PedidosAdapter;
import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.RecyclerTouchListener;
import es.studium.mispedidospendientes.TiendasAdapter;
import es.studium.mispedidospendientes.crudPedidos.AccesoRemotoPedidos;
import es.studium.mispedidospendientes.crudTiendas.AccesoRemotoTiendas;
import es.studium.mispedidospendientes.databinding.FragmentPedidosBinding;
import es.studium.mispedidospendientes.modelos.Pedido;
import es.studium.mispedidospendientes.modelos.Tienda;
import es.studium.mispedidospendientes.ui.tiendas.DlgAltaTienda;
import es.studium.mispedidospendientes.ui.tiendas.DlgBajaTienda;
import es.studium.mispedidospendientes.ui.tiendas.DlgEdicionTienda;
import es.studium.mispedidospendientes.ui.tiendas.TiendasFragment;

public class PedidosFragment extends Fragment implements View.OnClickListener {

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
                DlgEdicionPedido dlgEdicion = new DlgEdicionPedido(PedidosFragment.this, pedidoSeleccionado);
                dlgEdicion.setCancelable(false);
                dlgEdicion.show(getParentFragmentManager(), "Edicion Pedidos");
            }

            @Override
            public void onLongClick(View view, int position)
            {
                // Acción al hacer un long-click en un elemento
              /*  Pedido pedidoSeleccionado = listaPedidos.get(position);
                // Acción al hacer click en un elemento de la lista
                DlgBajaTienda dlgBaja = new DlgBajaTienda(PedidosFragment.this, pedidoSeleccionado.getId(), pedidoSeleccionado.getNombreTienda());
                dlgBaja.setCancelable(false);
                dlgBaja.show(getParentFragmentManager(), "Baja Tiendas");*/
            }
        }));


        floatingActionButtonP = root.findViewById(R.id.floatingActionButtonPedidos);
        floatingActionButtonP.setOnClickListener(this);

        // Permitir conexiones de red en el hilo principal (NO recomendado para producción)
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Obtener datos desde AccesoRemoto y asignar el adaptador
        cargarPedidos();

        return root;
    }

    public void cargarPedidos()
    {
        AccesoRemotoPedidos accesoRemotoPedidos = new AccesoRemotoPedidos();
        listaPedidos = accesoRemotoPedidos.obtenerPedidos(); // Debes crear este método en AccesoRemoto
        adapter = new PedidosAdapter(listaPedidos);
        recyclerViewPedidos.setAdapter(adapter);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.floatingActionButtonPedidos)
        {
            // Lógica para el botón flotante
            DlgAltaPedido dlgAltaPedidos = new DlgAltaPedido(this);
            dlgAltaPedidos.setCancelable(false);
            dlgAltaPedidos.show(getParentFragmentManager(), "Alta Pedidos");
        }
    }
}