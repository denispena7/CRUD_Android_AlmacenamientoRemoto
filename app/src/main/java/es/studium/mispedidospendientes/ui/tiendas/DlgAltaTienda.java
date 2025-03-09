package es.studium.mispedidospendientes.ui.tiendas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.crudTiendas.AltaRemotaTiendas;

public class DlgAltaTienda extends DialogFragment
{
    EditText nombreTienda;
    TiendasFragment fragment;

    // Constructor que recibe el fragmento
    public DlgAltaTienda(TiendasFragment fragment) {
        this.fragment = fragment;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState)
    {
        // Contruir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dlg_alta_tienda, null);
        builder.setView(myView);

        nombreTienda = myView.findViewById(R.id.storeName);

        builder.setTitle("Alta Tiendas")
                .setPositiveButton("Guardar", null) // Creación del botón positivo, sin funcionalidad aún
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(getContext(), "Alta Cancelada", Toast.LENGTH_SHORT).show();
                });

        AlertDialog dialog = builder.create();

        // Configuración manual del botón positivo
        dialog.setOnShowListener(d -> {
            // Agregar manualmente el comportamiento del botón "Aceptar"
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                try
                {
                    if (nombreTienda.getText().toString().isEmpty())
                    {
                        // Mostrar mensaje de error si el campo está vacío
                        Toast.makeText(getActivity(), "Escribe el nombre de una tienda", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // Si no está vacío, realizar alta
                        AltaRemotaTiendas altaRemotaTiendas = new AltaRemotaTiendas();
                        boolean correcta = altaRemotaTiendas.darAlta(nombreTienda.getText().toString());
                        if (correcta)
                        {
                            fragment.cargarDatos();
                            Toast.makeText(getContext(), "Alta Correcta", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Error en la Alta", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
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
