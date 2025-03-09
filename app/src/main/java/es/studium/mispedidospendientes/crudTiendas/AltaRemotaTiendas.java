package es.studium.mispedidospendientes.crudTiendas;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;

public class AltaRemotaTiendas
{
    OkHttpClient client = new OkHttpClient();
    public AltaRemotaTiendas(){};

    public boolean darAlta(String nombre)
    {
        boolean correcta = true;

        // Montamos la petición POST
        RequestBody formBody = new FormBody.Builder()
                .add("nombreTienda", nombre)
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2/Api Rest Practica/tiendas.php")
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
