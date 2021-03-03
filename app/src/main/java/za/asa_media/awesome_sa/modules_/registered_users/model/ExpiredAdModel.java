package za.asa_media.awesome_sa.modules_.registered_users.model;

/**
 * Created by Snow-Dell-05 on 8/18/2017.
 */

public class ExpiredAdModel {

    private String purchaseId;
    private String placeId;
    private String placeName;
    private String planId;
    private String planName;
    private String price;
    private String[] batchId;
    private String purchaseDate;
    private String ExpiryDate;
    private String transactionId;
    private String paymentStatus;
    private String latest_price;

    public ExpiredAdModel(String latest_price, String purchaseId, String placeId, String placeName, String planId, String planName, String price, String[] batchId, String purchaseDate, String expiryDate, String transactionId, String paymentStatus) {

        this.latest_price = latest_price;
        this.purchaseId = purchaseId;
        this.planId = planId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.planName = planName;
        this.price = price;
        this.batchId = batchId;
        this.purchaseDate = purchaseDate;
        this.ExpiryDate = expiryDate;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
    }


    public String getLatest_price() {
        return latest_price;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPrice() {
        return price;
    }

    public String[] getBatchId() {
        return batchId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

}

