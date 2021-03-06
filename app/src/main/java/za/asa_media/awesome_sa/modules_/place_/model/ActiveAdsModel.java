package za.asa_media.awesome_sa.modules_.place_.model;

/**
 * Created by Snow-Dell-05 on 5/30/2017.
 */

public class ActiveAdsModel {

    private String userId;
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
    private String planStatus;

    public ActiveAdsModel(String userId, String purchaseId, String placeId, String placeName, String planId, String planName, String price, String[] batchId, String purchaseDate, String expiryDate, String transactionId, String paymentStatus, String planStatus) {
        this.userId = userId;
        this.purchaseId = purchaseId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.planId = planId;
        this.planName = planName;
        this.price = price;
        this.batchId = batchId;
        this.purchaseDate = purchaseDate;
        this.ExpiryDate = expiryDate;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.planStatus = planStatus;
    }

    public String getUserId() {
        return userId;
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

    public String getPlanId() {
        return planId;
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

    public String getPlanStatus() {
        return planStatus;
    }
}
