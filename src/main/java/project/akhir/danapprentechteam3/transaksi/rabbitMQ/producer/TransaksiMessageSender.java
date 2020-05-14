//package project.akhir.danapprentechteam3.transaksi.rabbitMQ.producer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import project.akhir.danapprentechteam3.transaksi.model.Transaksi;
//import project.akhir.danapprentechteam3.transaksi.rabbitMQ.config.RabbitConfig;
//
//@Service
//public class TransaksiMessageSender {
//    private final RabbitTemplate rabbitTemplate;
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public TransaksiMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public void sendOrder(Transaksi transaksi) {
//        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDERS, transaksi);
//    }
//}
