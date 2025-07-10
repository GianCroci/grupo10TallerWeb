package com.tallerwebi.dominio;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.entidad.Producto;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioProducto;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import com.tallerwebi.presentacion.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    private final RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioMercadoPagoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public String crearCheckout(Pedido pedido) throws MPException, MPApiException {

        Producto productoObtenido = repositorioProducto.buscarProductoPorId(pedido.getIdProducto());

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://616ad40edf44.ngrok-free.app/spring/pago/success")
                .failure("https://616ad40edf44.ngrok-free.app/spring/pago/failure")
                .pending("https://616ad40edf44.ngrok-free.app/spring/pago/pending")
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
                        .build();

        items.add(item);

        PreferenceRequest request = PreferenceRequest.builder()
                .autoReturn("approved")
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
}
