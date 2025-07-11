package com.tallerwebi.dominio;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.entidad.Pago;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Producto;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioProducto;
import com.tallerwebi.presentacion.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    private final ServicioProducto servicioProducto;
    private final ServicioPersonaje servicioPersonaje;
    private final ServicioPago servicioPago;

    @Autowired
    public ServicioMercadoPagoImpl(ServicioProducto servicioProducto, ServicioPersonaje servicioPersonaje, ServicioPago servicioPago) {
        this.servicioProducto = servicioProducto;
        this.servicioPersonaje = servicioPersonaje;
        this.servicioPago = servicioPago;
    }

    @Override
    public String crearCheckout(Pedido pedido) throws MPException, MPApiException {

        Producto productoObtenido = servicioProducto.buscarProductoPorId(pedido.getIdProducto());

        String nGrokUrl = "https://ea40b6f1b33c.ngrok-free.app";

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(nGrokUrl + "/spring/pago/success?idPersonaje=" + pedido.getIdPersonajeComprador())
                .failure(nGrokUrl + "/spring/pago/failure?idPersonaje=" + pedido.getIdPersonajeComprador())
                .pending(nGrokUrl + "/spring/pago/pending?idPersonaje=" + pedido.getIdPersonajeComprador())
                .build();

// Crear un objeto de preferencia
        PreferenceClient client = new PreferenceClient();

// Crear un elemento en la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title(productoObtenido.getNombre())
                        .quantity(pedido.getCantidad())
                        .unitPrice(new BigDecimal(productoObtenido.getPrecio()))
                        .id(pedido.getIdProducto().toString())
                        .build();

        items.add(item);

        PreferenceRequest request = PreferenceRequest.builder()
                .backUrls(backUrls)
                // el .purpose('wallet_purchase') solo permite pagos registrados
                // para permitir pagos de guest, puede omitir esta línea
                .purpose("wallet_purchase")
                .items(items).build();

            Preference preference = client.create(request);


        return preference.getId();
    }

    @Override
    public Optional<String> obtenerMensajeOperacion(String operacionMP) {
        String mensajito = "";
        if (operacionMP == null){
            return Optional.empty();
        }
        switch(operacionMP) {
            case "success":
                mensajito = "El pago se ha realizado correctamente";
                break;
            case "failure":
                mensajito = "El pago no se ha realizado correctamente";
                break;
            case "pending":
                mensajito = "El pago sigue pendiente";
                break;
            default:
                mensajito = null;
        };
        return Optional.ofNullable(mensajito);
    }

    @Override
    public void completarCompraRealizada(Long idPersonaje, Long idProducto, Integer cantidad, String status) {

        Producto productoComprado = servicioProducto.buscarProductoPorId(idProducto);
        Personaje personajeComprador = servicioPersonaje.buscarPersonaje(idPersonaje);
        if (status != null) {
            if (status.equals("approved")) {
                personajeComprador.setOro(personajeComprador.getOro() + (productoComprado.getCantidadProducto() * cantidad));
                servicioPersonaje.modificar(personajeComprador);
            }
        }else {
            status = "failed";
        }

        Pago pagoCreado = new Pago();
        pagoCreado.setPersonaje(personajeComprador);
        pagoCreado.setProducto(productoComprado);
        pagoCreado.setCantidad(cantidad);
        pagoCreado.setStatus(status);
        pagoCreado.setFecha(LocalDateTime.now());
        servicioPago.guardarPago(pagoCreado);
    }
}
