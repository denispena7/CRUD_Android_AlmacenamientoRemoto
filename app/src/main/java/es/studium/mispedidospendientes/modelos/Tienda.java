package es.studium.mispedidospendientes.modelos;

public class Tienda
{
    private String nombreTienda;
    private long id;

    public Tienda(long id, String nombreTienda)
    {
        this.id = id;
        this.nombreTienda = nombreTienda;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getNombreTienda()
    {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda)
    {
        this.nombreTienda = nombreTienda;
    }

    @Override
    public String toString()
    {
        return "Tiendas{" +
                "nombre='" + nombreTienda + '\'' +
                ", id=" + id +
                '}';
    }
}
