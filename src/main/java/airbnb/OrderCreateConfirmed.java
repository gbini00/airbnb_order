package airbnb;

public class OrderCreateConfirmed extends AbstractEvent {

    private Long ordId;
    private Long prdId;
    private Long qty;
    private Long memId;
    private Long payId;
    private String status;
    private Long mileageUsed;

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
        return mileageUsed;
    }

    public void setMileageUsed(Long mileageUsed) {
        this.mileageUsed = mileageUsed;
    }
}