package es.studium.mispedidospendientes.crudPedidos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.studium.mispedidospendientes.modelos.Pedido;
import es.studium.mispedidospendientes.modelos.Tienda;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AccesoRemotoPedidos
{
    // Crear una instancia de OkHttpClient
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("http://10.0.2.2/Api Rest Practica/pedidos.php")
            .build();

    public List<Pedido> obtenerPedidos()
    {
        List<Pedido> listaPedidos = new ArrayList<>();

        try
        {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONArray jsonArray = new JSONArray(response.body().string());

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    long id = jsonObject.getLong("idPedido");
                    LocalDate fecha = LocalDate.parse(jsonObject.getString("fechaPedido"));
                    LocalDate fechaEnt = LocalDate.parse(jsonObject.getString("fechaAproxEntrega"));
                    String articulo = jsonObject.getString("descripcionPedido");
                    Double importe = jsonObject.getDouble("importePedido");
                    int estado = jsonObject.getInt("estadoPedido");
                    int idT = jsonObject.getInt("idTiendaFK");
                    String nTienda = jsonObject.getString("nombreTienda");


                    listaPedidos.add(new Pedido(id, fecha, fechaEnt, articulo, importe, estado, idT, nTienda));
                }
            } else {
                Log.e("AccesoRemoto", "Error en la respuesta: " + response.message());
            }
        } catch (IOException | JSONException e) {
            Log.e("AccesoRemoto", "Error: " + e.getMessage());
        }

        return listaPedidos;
    }
}
