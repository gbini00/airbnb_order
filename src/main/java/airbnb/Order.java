package airbnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ordId;
    private Long prdId;
    private Long qty;
    private Long memId;
    private Long payId;
    private String status;
    private Long mildageUsed;

    @PostPersist
    public void onPostPersist(){
        OrderCreated orderCreated = new OrderCreated();
        BeanUtils.copyProperties(this, orderCreated);
        orderCreated.publishAfterCommit();

        airbnb.external.Payment payment = new airbnb.external.Payment();
		payment.setOrdId(this.getOrdId());
		payment.setPrdId(this.getPrdId());
		payment.setStatus("paid");
		payment.setMemId(this.getMemId());
		payment.setMileageUsed(this.getMileageUsed());
		payment.setQty(this.getQty());
        OrderApplication.applicationContext.getBean(airbnb.external.PaymentService.class).approvePayment(payment);
    }
	
    @PostUpdate
    public void onPostUpdate(){

		if(this.getStatus().equals("cancelOrder")) {
			OrderCancelled orderCancelled = new OrderCancelled();
			BeanUtils.copyProperties(this, orderCancelled);
			orderCancelled.publishAfterCommit();
		}

		if(this.getStatus().equals("confirmOrder")) {
			OrderCreateConfirmed orderCreateConfirmed = new OrderCreateConfirmed();
			BeanUtils.copyProperties(this, orderCreateConfirmed);
			orderCreateConfirmed.publishAfterCommit();
		}

		if(this.getStatus().equals("confirmCancel")) {
			OrderCancelConfirmed orderCancelConfirmed = new OrderCancelConfirmed();
			BeanUtils.copyProperties(this, orderCancelConfirmed);
			orderCancelConfirmed.publishAfterCommit();
		}
	}


    public Long getOrdId() {
        return ordId;
    }

    public void setOrdId(Long ordId) {
        this.ordId = ordId;
    }
    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public Long getMemId() {
        return memId;
    }

    public void setMemId(Long memId) {
        this.memId = memId;
    }
    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Long getMileageUsed() {
        return mildageUsed;
    }

    public void setMileageUsed(Long mildageUsed) {
        this.mildageUsed = mildageUsed;
    }




}
