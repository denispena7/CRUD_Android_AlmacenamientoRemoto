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
import es.studium.mispedidospendientes.crudTiendas.ModificacionRemotaTiendas;
import es.studium.mispedidospendientes.modelos.Tienda;

public class DlgEdicionTienda extends DialogFragment
{
    EditText nombreTienda;
    TiendasFragment fragment;
    Tienda tiendaActualizar;
    long id;
    String nombre;

    // Constructor que recibe el fragmento
    public DlgEdicionTienda(TiendasFragment fragment, long id, String nombre) {
        this.fragment = fragment;
        this.id = id;
        this.nombre = nombre;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState)
    {
        // Contruir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dlg_edicion_tienda, null);
        builder.setView(myView);

        nombreTienda = myView.findViewById(R.id.newName);

        tiendaActualizar = new Tienda(id, nombre);
        nombreTienda.setText(nombre);

        // Configuración de botones
        builder.setTitle(R.string.dlgModificacionTienda)
                .setPositiveButton(R.string.btnGuardar, null) // Creación del botón positivo, sin funcionalidad aún
                .setNegativeButton(R.string.btnCancelar, (dialog, which) -> {
                    Toast.makeText(getContext(), R.string.cancelarModificacion, Toast.LENGTH_SHORT).show();
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
                        // Si no está vacío, realizar modificacion
                        ModificacionRemotaTiendas modificacionRemotaTiendas = new ModificacionRemotaTiendas();
                        boolean correcta = modificacionRemotaTiendas.modificar(tiendaActualizar.getId(), nombreTienda.getText().toString());
                        if (correcta)
                        {
                            fragment.cargarDatos();
                            // Mostrar mensaje de error si el campo está vacío
                            Toast.makeText(getActivity(), R.string.modificacionCorrecta, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(), R.string.modificacionIncorrecta, Toast.LENGTH_SHORT).show();
                        }
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

