package es.studium.mispedidospendientes.modelos;

import java.time.LocalDate;

public class Pedido
{
    private long id;
    private LocalDate fechaPedido;
    private LocalDate fechaEntrega;
    private String descripcion;
    private Double importe;
    private int estadoPedido = 0;
    private int idTienda;
    private String nTienda;

    public Pedido(long id, LocalDate fechaPedido, LocalDate fechaEntrega, String descripcion, Double importe, int estadoPedido, int idTienda, String nTienda)
    {
        this.id = id;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.descripcion = descripcion;
        this.importe = importe;
        this.estadoPedido = estadoPedido;
        this.idTienda = idTienda;
        this.nTienda = nTienda;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public int getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(int estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getnTienda() {
        return nTienda;
    }

    public void setnTienda(String nTienda) {
        this.nTienda = nTienda;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fechaPedido=" + fechaPedido +
                ", fechaEntrega=" + fechaEntrega +
                ", descripcion='" + descripcion + '\'' +
                ", importe=" + importe +
                ", estadoPedido=" + estadoPedido +
                ", idTienda=" + idTienda +
                ", nTienda='" + nTienda + '\'' +
                '}';
    }
}
