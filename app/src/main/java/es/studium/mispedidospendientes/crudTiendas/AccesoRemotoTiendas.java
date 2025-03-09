package es.studium.mispedidospendientes.crudTiendas;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.studium.mispedidospendientes.modelos.Tienda;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AccesoRemotoTiendas
{
    // Crear una instancia de OkHttpClient
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("http://10.0.2.2/Api Rest Practica/tiendas.php") // Cambia la URL por la de tu API
            .build();

    public List<Tienda> obtenerTiendas()
    {
        List<Tienda> listaTiendas = new ArrayList<>();

        try
        {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONArray jsonArray = new JSONArray(response.body().string());

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("idTienda");
                    String nombre = jsonObject.getString("nombreTienda");

                    listaTiendas.add(new Tienda(Integer.parseInt(id), nombre));
                }
            } else {
                Log.e("AccesoRemoto", "Error en la respuesta: " + response.message());
            }
        } catch (IOException | JSONException e) {
            Log.e("AccesoRemoto", "Error: " + e.getMessage());
        }

        return listaTiendas;
    }
}
