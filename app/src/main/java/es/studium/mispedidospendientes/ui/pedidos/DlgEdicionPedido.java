package es.studium.mispedidospendientes.ui.pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

import es.studium.mispedidospendientes.PedidosAdapter;
import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.crudPedidos.ModificacionRemotaPedidos;
import es.studium.mispedidospendientes.modelos.Pedido;

public class DlgEdicionPedido extends DialogFragment
{
    Spinner tiendasEdicion;
    EditText fEntrega, descPedido, impPedido;
    CheckBox estado;

    PedidosFragment fragment;
    Pedido pedidoActualizar;

    LocalDate fecha2;
    String descripcionPedido, nombreTienda;
    Double importePedido;
    int idTiendaFK;

    DlgAltaPedido funcionRellenar = new DlgAltaPedido();
    PedidosAdapter funcionesFecha = new PedidosAdapter();

    // Constructor que recibe el fragmento
    public DlgEdicionPedido(PedidosFragment fragment, Pedido pedido)
    {
        this.fragment = fragment;
        this.pedidoActualizar = pedido;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState)
    {
        // Contruir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dlg_edicion_pedido, null);
        builder.setView(myView);

        tiendasEdicion = myView.findViewById(R.id.spnTiendasMod);
        fEntrega = myView.findViewById(R.id.txtNuevaFecha);
        descPedido = myView.findViewById(R.id.editTextText);
        impPedido = myView.findViewById(R.id.txtNuevoImporte);
        estado = myView.findViewById(R.id.chkEstadoPedido);

        fecha2 = pedidoActualizar.getFechaEntrega();
        descripcionPedido = pedidoActualizar.getDescripcion();
        importePedido = pedidoActualizar.getImporte();
        idTiendaFK = pedidoActualizar.getIdTienda();
        nombreTienda = pedidoActualizar.getnTienda();

        // Rellenar el Spinner con las tiendas
        funcionRellenar.cargarTiendas(tiendasEdicion);

        for (int i = 0; i < tiendasEdicion.getCount(); i++)
        {
            Log.d("SpinnerData", "Tienda en posición " + i + ": " + tiendasEdicion.getItemAtPosition(i));
        }

        // Esperar a que el Spinner se llene antes de establecer la selección
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            int p = obtenerPosicionTienda(nombreTienda);
            Log.d("IndiceTienda", "Índice obtenido: " + p);
            if (p != -1 && p < tiendasEdicion.getCount()) {
                tiendasEdicion.setSelection(p);
            } else {
                Log.e("SpinnerError", "La tienda no se encontró en el Spinner");
            }
        }, 500);

        // Rellenar los campos con los datos del pedido
        fEntrega.setText(funcionesFecha.formatearFecha(fecha2));
        descPedido.setText(descripcionPedido);
        impPedido.setText(importePedido.toString());
        estado.setChecked(false);

        builder.setTitle("Modificación Pedidos")
                .setPositiveButton("Actualizar", null) // Creación del botón positivo, sin funcionalidad aún
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(getContext(), "Modificación Cancelada", Toast.LENGTH_SHORT).show();
                });

        AlertDialog dialog = builder.create();

        // Configuración manual del botón positivo
        dialog.setOnShowListener(d -> {
            // Agregar manualmente el comportamiento del botón "Aceptar"
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                try
                {
                    if ((tiendasEdicion.getSelectedItem().equals("Selecciona una tienda") || fEntrega.getText().toString().isEmpty() || descPedido.getText().toString().isEmpty() || impPedido.getText().toString().isEmpty()))
                    {
                        StringBuilder mensaje = new StringBuilder();

                        if (tiendasEdicion.getSelectedItem() != null && tiendasEdicion.getSelectedItem().equals("Selecciona una tienda")) mensaje.append("Falta Tienda").append("\n");
                        if (fEntrega.getText().toString().isEmpty()) mensaje.append("Falta Fecha de  Entrega").append("\n");
                        if (descPedido.getText().toString().isEmpty()) mensaje.append("Falta Artículo").append("\n");
                        if (impPedido.getText().toString().isEmpty()) mensaje.append("Falta Importe").append("\n");

                        // Si hay más de un campo vacío, pedir que se rellenen todos los campos
                        if (mensaje.toString().split("\n").length > 1)
                        {
                            mensaje = new StringBuilder("Cumplimente los campos");
                        }

                        Toast.makeText(getContext(), mensaje.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(funcionRellenar.validarFecha(fEntrega.getText().toString()))
                        {
                            // Si no está vacío, realizar modificacion
                            ModificacionRemotaPedidos modificacionRemotaPedidos = new ModificacionRemotaPedidos();
                            if(estado.isChecked())
                            {
                                pedidoActualizar.setEstadoPedido(1);
                                estado.setChecked(true);
                            }
                            else
                            {
                                pedidoActualizar.setEstadoPedido(0);
                            }

                            pedidoActualizar.setIdTienda(Integer.parseInt(tiendasEdicion.getSelectedItem().toString().split(" ")[0]));
                            pedidoActualizar.setFechaEntrega(LocalDate.parse(funcionRellenar.formatoMySQL(fEntrega.getText().toString())));
                            pedidoActualizar.setDescripcion(descPedido.getText().toString());
                            pedidoActualizar.setImporte(Double.parseDouble(impPedido.getText().toString()));
                            Log.d("Importe", Double.parseDouble(impPedido.getText().toString()) + "");

                            boolean correcta = modificacionRemotaPedidos.modificar(pedidoActualizar);
                            if (correcta)
                            {
                                fragment.cargarPedidos();
                                // Mostrar mensaje de error si el campo está vacío
                                Toast.makeText(getActivity(), "Modificación correcta", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Error en la Modificación", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Fecha no válida", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.e("DlgEdicionPedido", "Error en la modificación", ex);
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return dialog;
    }

    private int obtenerPosicionTienda(String nombreTienda)
    {
        for (int i = 0; i < tiendasEdicion.getCount(); i++) {
            String item = tiendasEdicion.getItemAtPosition(i).toString();
            if (item.contains(nombreTienda)) { // Verifica si el nombre de la tienda está en la opción del Spinner
                return i;
            }
        }
        return -1; // Retorna -1 si no encuentra coincidencias
    }

}
