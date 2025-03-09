package es.studium.mispedidospendientes.ui.tiendas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.crudTiendas.BajaRemotaTiendas;
import es.studium.mispedidospendientes.modelos.Tienda;

public class DlgBajaTienda extends DialogFragment
{
    TextView pregunta;
    TiendasFragment fragment;
    Tienda tiendaBorrar;
    long id;
    String nombre;

    // Constructor que recibe el fragmento
    public DlgBajaTienda(TiendasFragment fragment, long id, String nombre)
    {
        this.fragment = fragment;
        this.id = id;
        this.nombre = nombre;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState)
    {
        // Contruir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dlg_baja_tienda, null);
        builder.setView(myView);

        pregunta = myView.findViewById(R.id.lblPregunta);

        // Si no está vacío, realizar modificacion
        tiendaBorrar = new Tienda(id, nombre);
        pregunta.setText(getString(R.string.pregunta, nombre));

        builder.setTitle("Modificación Tiendas")
                .setPositiveButton("Guardar", null) // Creación del botón positivo, sin funcionalidad aún
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(getContext(), "Baja Cancelada", Toast.LENGTH_SHORT).show();
                });

        AlertDialog dialog = builder.create();

        // Configuración manual del botón positivo
        dialog.setOnShowListener(d -> {
            // Agregar manualmente el comportamiento del botón "Aceptar"
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                try
                {
                    BajaRemotaTiendas bajaRemotaTiendas = new BajaRemotaTiendas();
                    boolean correcta = bajaRemotaTiendas.darBaja(tiendaBorrar.getId());
                    if (correcta)
                    {
                            fragment.cargarDatos();
                            // Mostrar mensaje de error si el campo está vacío
                            Toast.makeText(getActivity(), "Baja correcta", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Error en la Modificación", Toast.LENGTH_SHORT).show();
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
