package com.tallerwebi.presentacion;

import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderItem;
import com.mercadopago.resources.payment.Payment;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ControladorMercadoPago {

    private final ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorMercadoPago(ServicioMercadoPago servicioMercadoPago) {
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping(path = "/mp", method = RequestMethod.POST,  produces = "text/plain", consumes = "application/json")
    public String crearCheckout(@RequestBody Pedido pedido, HttpSession session) throws MPException, MPApiException {

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        pedido.setIdPersonajeComprador(idPersonaje);
        String preferenceId = null;
        try {
            preferenceId = servicioMercadoPago.crearCheckout(pedido);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

        return preferenceId;
    }

    @RequestMapping(path = "/pago/success")
    public ModelAndView pagoValidadoAprobado(HttpServletRequest request){

        String merchantOrderId  = request.getParameter("merchant_order_id");
        Long orderId = Long.parseLong(merchantOrderId);
        MerchantOrderClient merchantOrderClient = new MerchantOrderClient();
        String paymentCLienteId  = request.getParameter("payment_id");
        Long paymentId = Long.parseLong(paymentCLienteId);
        PaymentClient paymentClient = new PaymentClient();
        try {
            MerchantOrder order = merchantOrderClient.get(orderId);
            Payment payment = paymentClient.get(paymentId);
            Long idPersonaje = Long.parseLong(request.getParameter("idPersonaje"));
            Long idProducto = Long.parseLong(order.getItems().get(0).getId());
            Integer cantidad = order.getItems().get(0).getQuantity();
            String status = payment.getStatus();
            servicioMercadoPago.completarCompraRealizada(idPersonaje,idProducto, cantidad, status);

        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

        return new ModelAndView("redirect:http://localhost:8080/spring/pago-completado?operacionMP=success");
    }

    @RequestMapping(path = "/pago/failure")
    public ModelAndView pagoValidadoFallido(HttpServletRequest request) {

        String merchantOrderId  = request.getParameter("merchant_order_id");
        Long orderId = Long.parseLong(merchantOrderId);
        MerchantOrderClient merchantOrderClient = new MerchantOrderClient();
        String paymentCLienteId  = request.getParameter("payment_id");
        Long paymentId = Long.parseLong(paymentCLienteId);
        PaymentClient paymentClient = new PaymentClient();
        try {
            MerchantOrder order = merchantOrderClient.get(orderId);
            Payment payment = paymentClient.get(paymentId);
            Long idPersonaje = Long.parseLong(request.getParameter("idPersonaje"));
            Long idProducto = Long.parseLong(order.getItems().get(0).getId());
            Integer cantidad = order.getItems().get(0).getQuantity();
            String status = payment.getStatus();
            servicioMercadoPago.completarCompraRealizada(idPersonaje,idProducto, cantidad, status);

        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

        return new ModelAndView("redirect:http://localhost:8080/spring/pago-completado?operacionMP=failure");
    }

    @RequestMapping(path = "/pago/pending")
    public ModelAndView pagoValidadoPendiente(HttpServletRequest request) {

        String merchantOrderId  = request.getParameter("merchant_order_id");
        Long orderId = Long.parseLong(merchantOrderId);
        MerchantOrderClient merchantOrderClient = new MerchantOrderClient();
        String paymentCLienteId  = request.getParameter("payment_id");
        Long paymentId = Long.parseLong(paymentCLienteId);
        PaymentClient paymentClient = new PaymentClient();
        try {
            MerchantOrder order = merchantOrderClient.get(orderId);
            Payment payment = paymentClient.get(paymentId);
            Long idPersonaje = Long.parseLong(request.getParameter("idPersonaje"));
            Long idProducto = Long.parseLong(order.getItems().get(0).getId());
            Integer cantidad = order.getItems().get(0).getQuantity();
            String status = payment.getStatus();
            servicioMercadoPago.completarCompraRealizada(idPersonaje,idProducto, cantidad, status);

        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

        return new ModelAndView("redirect:http://localhost:8080/spring/pago-completado?operacionMP=pending");
    }
}
