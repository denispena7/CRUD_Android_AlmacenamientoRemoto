package es.studium.mispedidospendientes.ui.pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.studium.mispedidospendientes.R;
import es.studium.mispedidospendientes.crudPedidos.AltaRemotaPedidos;
import es.studium.mispedidospendientes.crudTiendas.AccesoRemotoTiendas;
import es.studium.mispedidospendientes.modelos.Tienda;

public class DlgAltaPedido extends DialogFragment
{
    Spinner spnTiendas;
    EditText fechaEnt, articulo, importe;
    PedidosFragment fragment;
    List<Tienda> listaTiendas;

    public DlgAltaPedido(){}

    // Constructor que recibe el fragmento
    public DlgAltaPedido(PedidosFragment fragment) {
        this.fragment = fragment;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState)
    {
        // Contruir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.dlg_alta_pedido, null);
        builder.setView(myView);

        spnTiendas = myView.findViewById(R.id.listaTiendas);
        fechaEnt = myView.findViewById(R.id.txtFechaEntr);
        articulo = myView.findViewById(R.id.txtArt);
        importe = myView.findViewById(R.id.txtTotal);

        // Llamamos a la función para cargar el Spinner con las tiendas
        cargarTiendas(spnTiendas);

        // Configuración de los botones
        builder.setTitle(R.string.dlgAltaPedido)
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
                    String desc = articulo.getText().toString();
                    String imp = importe.getText().toString();
                    String spn = spnTiendas.getSelectedItem().toString().split(" ")[0];

                    // Validaciones de campos vacíos
                    if (spnTiendas.getSelectedItem().equals("Selecciona una tienda") || fechaEnt.getText().toString().isEmpty() || articulo.getText().toString().isEmpty() || importe.getText().toString().isEmpty())
                    {
                        StringBuilder mensaje = new StringBuilder();

                        if (spnTiendas.getSelectedItem().equals("Selecciona una tienda")) mensaje.append("Elige una tienda").append("\n");
                        if (fechaEnt.getText().toString().isEmpty()) mensaje.append("Falta la fecha").append("\n");
                        if (articulo.getText().toString().isEmpty()) mensaje.append("Falta el artículo").append("\n");
                        if (importe.getText().toString().isEmpty()) mensaje.append("Falta el importe").append("\n");

                        // Si hay más de un campo vacío, pedir que se rellenen todos los campos
                        if (mensaje.toString().split("\n").length > 1)
                        {
                            mensaje = new StringBuilder("Cumplimenta los campos");
                        }

                        Toast.makeText(getContext(), mensaje.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        // Si todos los campos están vacíos, se valida la fecha y se procede si está correcta
                        if(validarFecha(fechaEnt.getText().toString()))
                        {
                            String nuevaFecha = formatoMySQL(fechaEnt.getText().toString());

                            AltaRemotaPedidos altaRemotaPedidos = new AltaRemotaPedidos();
                            boolean correcta = altaRemotaPedidos.darAlta(nuevaFecha, desc, imp, spn);
                            if (correcta)
                            {
                                // Actualizar la lista de pedidos
                                fragment.cargarPedidos();
                                Toast.makeText(getContext(), R.string.altaCorrecta, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), R.string.altaIncorrecta, Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), R.string.fechaIncorrecta, Toast.LENGTH_SHORT).show();
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

    // Función para cargar spinner con tiendas
    public void cargarTiendas(Spinner lista) {
        new Thread(() -> {
            AccesoRemotoTiendas accesoRemoto = new AccesoRemotoTiendas();
            listaTiendas = accesoRemoto.obtenerTiendas();

            List<String> nombresTiendas = new ArrayList<>();
            nombresTiendas.add("Selecciona una tienda");
            for (Tienda tienda : listaTiendas) {
                nombresTiendas.add(tienda.getId() + " " + tienda.getNombreTienda());
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(lista.getContext(),
                        android.R.layout.simple_spinner_dropdown_item, nombresTiendas);
                lista.setAdapter(adapter);
            });
        }).start();
    }

    // Función para validar fechas
    public boolean validarFecha(String fecha)
    {
        try
        {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        }
        catch(ParseException e)
        {
            return false;
        }
        return true;
    }

    // Método para dar formato MySQL
    public String formatoMySQL(String fecha)
    {
      //  Log.d("FechaRecibida", "Fecha antes de formatear: " + fecha);
        String[] fechaCambiada = fecha.split("/");
        String fechaFormateada = fechaCambiada[2] + "-" + fechaCambiada[1] + "-" + fechaCambiada[0];
    //     Log.d("FechaFormateada", "Fecha después de formatear: " + fechaFormateada);
        return fechaFormateada;
    }
}
