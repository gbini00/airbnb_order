package airbnb;

import airbnb.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_ConfirmOrder(@Payload PaymentApproved paymentApproved){
		
        if(paymentApproved.isMe()){
            System.out.println("##### listener ConfirmOrder : " + paymentApproved.toJson());

			long memId = paymentApproved.getMemId();
			long mileageUsed = paymentApproved.getMileageUsed();
            long prdId = paymentApproved.getPrdId();
			long ordId = paymentApproved.getOrdId();
			long qty = paymentApproved.getQty();
            long payId = paymentApproved.getPayId();
			
            updateOrderStatus(ordId, prdId, qty, memId, payId, "confirmOrder", mileageUsed);

        }
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_ConfirmCancel(@Payload PaymentCancelled paymentCancelled){

        if(paymentCancelled.isMe()){
            System.out.println("##### listener ConfirmCancel : " + paymentCancelled.toJson());
			
			long memId = paymentCancelled.getMemId();
			long mileageUsed = paymentCancelled.getMileageUsed();
            long prdId = paymentCancelled.getPrdId();
			long ordId = paymentCancelled.getOrdId();
			long qty = paymentCancelled.getQty();
            long payId = paymentCancelled.getPayId();

            updateOrderStatus(ordId, prdId, qty, memId, payId, "confirmCancel", mileageUsed);

        }
            
    }


    private void updateOrderStatus(long ordId, long prdId, long qty, long memId, long payId, String status, long mileageUsed)     {
        Optional<Order> res = orderRepository.findById(ordId);
        Order order = res.get();

        order.setStatus(status); 
        order.setPayId(payId);
		order.setMileageUsed(mileageUsed);
		order.setPrdId(prdId);
		order.setQty(qty);
        order.setMemId(memId);

        orderRepository.save(order);

    }

}
