//package project.akhir.danapprentechteam3.transaksi.rabbitMQ.consumer;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import project.akhir.danapprentechteam3.transaksi.model.Transaksi;
//import project.akhir.danapprentechteam3.transaksi.rabbitMQ.config.RabbitConfig;
//
//@Component
//public class TransaksiMessageListener {
//    static final Logger logger = LoggerFactory.getLogger(TransaksiMessageListener.class);
//    private Transaksi transaksi;
//
//    @RabbitListener(queues = RabbitConfig.QUEUE_ORDERS)
//    public void processOrder(Transaksi transaksi) {
//        logger.info("Transaksi Received: "+transaksi);
//        this.transaksi = transaksi;
//    }
//
//
//    public Transaksi getTransaksi() {
//        return transaksi;
//    }
//
//
//}
