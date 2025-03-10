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

        // Funcionalidad de los botones del diálogo
        builder.setTitle(R.string.dlgAltaTienda)
                .setPositiveButton(R.string.btnGuardar, null) // Creación del botón positivo, sin funcionalidad aún
                .setNegativeButton(R.string.btnCancelar, (dialog, which) -> {
                    Toast.makeText(getContext(), R.string.cancelarAlta, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), R.string.tiendaVacia, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // Si no está vacío, realizar alta
                        AltaRemotaTiendas altaRemotaTiendas = new AltaRemotaTiendas();
                        boolean correcta = altaRemotaTiendas.darAlta(nombreTienda.getText().toString());
                        if (correcta)
                        {
                            // Actualizar la lista de tiendas
                            fragment.cargarDatos();
                            Toast.makeText(getContext(), R.string.altaCorrecta, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), R.string.altaIncorrecta, Toast.LENGTH_SHORT).show();
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
