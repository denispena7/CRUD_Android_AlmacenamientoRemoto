package es.studium.mispedidospendientes.crudPedidos;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BajaRemotaPedidos
{
    OkHttpClient client = new OkHttpClient();
    public BajaRemotaPedidos() {}

    public boolean darBaja(long id)
    {
        boolean correcta = true;
        Request request = new Request.Builder()
                .url("http://10.0.2.2/Api Rest Practica/pedidos.php?idPedido="+id)
                .delete()
                .build();
        Call call = client.newCall(request);
        try
        {
            Response response = call.execute();
            Log.i("BajaRemota", String.valueOf(response));
            Log.i("BajaRemota", "Enviando petición de baja para ID: " + id);
            correcta = true;
        }
        catch (IOException e)
        {
            Log.e("BajaRemota", e.getMessage());
            correcta = false;
        }
        return correcta;
    }
}
