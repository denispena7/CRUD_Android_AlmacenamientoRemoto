package es.studium.mispedidospendientes.ui.pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.crudPedidos.BajaRemotaPedidos;
import es.studium.mispedidospendientes.modelos.Pedido;

public class DlgBajaPedido extends DialogFragment
{
    TextView pregunta;
    PedidosFragment fragment;
    Pedido pedidoBorrar;
    long id;

    // Constructor que recibe el fragmento
    public DlgBajaPedido(PedidosFragment fragment, long id)
    {
        this.fragment = fragment;
        this.id = id;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState)
    {
        // Contruir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dlg_baja_pedido, null);
        builder.setView(myView);

        pregunta = myView.findViewById(R.id.lblPreguntaPedidos);

        pedidoBorrar = new Pedido(id);
        pregunta.setText(getString(R.string.pregunta2, id + ""));

        // Configuración de los botones
        builder.setTitle(R.string.dlgBajaPedido)
                .setPositiveButton(R.string.btnEliminar, null) // Creación del botón positivo, sin funcionalidad aún
                .setNegativeButton(R.string.btnCancelar, (dialog, which) -> {
                    Toast.makeText(getContext(), R.string.cancelarBaja, Toast.LENGTH_SHORT).show();
                });

        AlertDialog dialog = builder.create();

        // Configuración manual del botón positivo
        dialog.setOnShowListener(d -> {
            // Agregar manualmente el comportamiento del botón "Aceptar"
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                try
                {
                    BajaRemotaPedidos bajaRemotaPedidos = new BajaRemotaPedidos();
                    boolean correcta = bajaRemotaPedidos.darBaja(pedidoBorrar.getId());
                    if (correcta)
                    {
                        // Actualizar lista de pedidos
                        fragment.cargarPedidos();
                        // Mostrar mensaje de error si el campo está vacío
                        Toast.makeText(getActivity(), R.string.bajaCorrecta, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getContext(), R.string.bajaIncorrecta, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return dialog;
    }
}
