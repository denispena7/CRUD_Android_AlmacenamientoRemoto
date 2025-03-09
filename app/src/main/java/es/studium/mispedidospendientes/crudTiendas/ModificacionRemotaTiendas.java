package es.studium.mispedidospendientes.crudTiendas;

import android.util.Log;
import java.io.IOException;
import java.net.URI;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModificacionRemotaTiendas
{
    OkHttpClient client = new OkHttpClient();
    public ModificacionRemotaTiendas() {}

    public boolean modificar(long id, String nombre)
    {
        boolean correcta = true;
        HttpUrl.Builder queryUrlBuilder = HttpUrl.get(URI.create("http://10.0.2.2/Api%20Rest%20Practica/tiendas.php"))
                        .newBuilder();
        queryUrlBuilder.addQueryParameter("idTienda", id+"");
        queryUrlBuilder.addQueryParameter("nombreTienda", nombre);

        // Las peticiones PUT requieren BODY, aunque sea vacío
        RequestBody formBody = new FormBody.Builder()
                .build();
        Log.i("ModificacionRemota", formBody.toString());
        Request request = new Request.Builder()
                .url(queryUrlBuilder.build())
                .put(formBody)
                .build();
        Log.i("ModificacionRemota", String.valueOf(request));
        Call call = client.newCall(request);
        try
        {
            Response response = call.execute();
            Log.i("ModificacionRemota", String.valueOf(response));
            correcta = true;
        }
        catch (IOException e)
        {
            Log.e("ModificacionRemota", e.getMessage());
            correcta = false;
        }
        return correcta;
    }
}
