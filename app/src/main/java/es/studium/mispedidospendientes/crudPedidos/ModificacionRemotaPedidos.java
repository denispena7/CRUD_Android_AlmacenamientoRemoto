package es.studium.mispedidospendientes.crudPedidos;

import android.util.Log;

import java.io.IOException;
import java.net.URI;

import es.studium.mispedidospendientes.modelos.Pedido;
import es.studium.mispedidospendientes.ui.pedidos.DlgAltaPedido;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModificacionRemotaPedidos
{
    OkHttpClient client = new OkHttpClient();
    DlgAltaPedido funcionFormato = new DlgAltaPedido();

    public ModificacionRemotaPedidos() {}

    public boolean modificar(Pedido pedido)
    {
        boolean correcta = true;
        HttpUrl.Builder queryUrlBuilder = HttpUrl.get(URI.create("http://10.0.2.2/Api%20Rest%20Practica/pedidos.php"))
                .newBuilder();
        queryUrlBuilder.addQueryParameter("idPedido", pedido.getId()+"");
        queryUrlBuilder.addQueryParameter("fechaPedido", pedido.getFechaPedido()+"");
        queryUrlBuilder.addQueryParameter("fechaAproxEntrega", pedido.getFechaEntrega()+"");
        queryUrlBuilder.addQueryParameter("descripcionPedido", pedido.getDescripcion());
        queryUrlBuilder.addQueryParameter("importePedido", pedido.getImporte()+"");
        queryUrlBuilder.addQueryParameter("estadoPedido", pedido.getEstadoPedido()+"");
        queryUrlBuilder.addQueryParameter("idTiendaFK", pedido.getIdTienda()+"");

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
