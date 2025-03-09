package es.studium.mispedidospendientes.ui.tiendas;

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

import es.studium.mispedidospendientes.crudTiendas.AccesoRemotoTiendas;
import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.RecyclerTouchListener;
import es.studium.mispedidospendientes.TiendasAdapter;
import es.studium.mispedidospendientes.modelos.Tienda;

public class TiendasFragment extends Fragment implements View.OnClickListener
{
    private RecyclerView recyclerView;
    private TiendasAdapter adapter;
    private List<Tienda> listaTiendas;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_tiendas, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewTiendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dentro de onCreateView, justo después de configurar el RecyclerView:
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(requireContext(),
                recyclerView, new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Tienda tiendaSeleccionada = listaTiendas.get(position);
                // Acción al hacer click en un elemento de la lista
                DlgEdicionTienda dlgEdicion = new DlgEdicionTienda(TiendasFragment.this, tiendaSeleccionada.getId(), tiendaSeleccionada.getNombreTienda());
                dlgEdicion.setCancelable(false);
                dlgEdicion.show(getParentFragmentManager(), "Edicion Tiendas");
            }

            @Override
            public void onLongClick(View view, int position)
            {
                // Acción al hacer un long-click en un elemento
                Tienda tiendaSeleccionada = listaTiendas.get(position);
                // Acción al hacer click en un elemento de la lista
                DlgBajaTienda dlgBaja = new DlgBajaTienda(TiendasFragment.this, tiendaSeleccionada.getId(), tiendaSeleccionada.getNombreTienda());
                dlgBaja.setCancelable(false);
                dlgBaja.show(getParentFragmentManager(), "Baja Tiendas");
            }
        }));


        floatingActionButton = root.findViewById(R.id.floatingActionButtonTiendas);
        floatingActionButton.setOnClickListener(this);

        // Permitir conexiones de red en el hilo principal (NO recomendado para producción)
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Obtener datos desde AccesoRemoto y asignar el adaptador
        cargarDatos();

        return root;
    }

    public void cargarDatos() {
        AccesoRemotoTiendas accesoRemotoTiendas = new AccesoRemotoTiendas();
        listaTiendas = accesoRemotoTiendas.obtenerTiendas(); // Debes crear este método en AccesoRemoto
        adapter = new TiendasAdapter(listaTiendas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.floatingActionButtonTiendas)
        {
            // Lógica para el botón flotante
            DlgAltaTienda dlgAlta = new DlgAltaTienda(this);
            dlgAlta.setCancelable(false);
            dlgAlta.show(getParentFragmentManager(), "Alta Tiendas");
        }
    }
}
