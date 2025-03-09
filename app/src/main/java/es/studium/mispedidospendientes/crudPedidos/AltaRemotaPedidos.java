package es.studium.mispedidospendientes.crudPedidos;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AltaRemotaPedidos
{
    OkHttpClient client = new OkHttpClient();
    public AltaRemotaPedidos(){};

    public boolean darAlta(String fecha, String articulo, String importe, String tienda)
    {
        boolean correcta = true;

        // Montamos la petición POST
        RequestBody formBody = new FormBody.Builder()
                .add("fechaAproxEntrega", fecha)
                .add("descripcionPedido", articulo)
                .add("importePedido", importe)
                .add("idTiendaFK", tienda)
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2/Api Rest Practica/pedidos.php")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        try
        {
            Response response = call.execute();
            Log.i("AltaRemota", String.valueOf(response));
            correcta = true;
        }
        catch (IOException e)
        {
            Log.e("AltaRemota", e.getMessage());
            correcta = false;
        }
        return correcta;
    }
}
