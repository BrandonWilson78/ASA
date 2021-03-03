package za.asa_media.awesome_sa.modules_.registered_users.model;

/**
 * Created by Snow-Dell-05 on 5/9/2017.
 */

public class TransactionHistoryModel {

    private String placeName;
    private String planName;
    private String price;
    private String purchaseDate;
    private String ExpiryDate;
    private String transactionId;
    private String paymentStatus;
    private String adStatus;

    public TransactionHistoryModel(String placeName, String planName, String price, String purchaseDate, String expiryDate, String transactionId, String paymentStatus, String adStatus) {
        this.placeName = placeName;
        this.planName = planName;
        this.price = price;
        this.purchaseDate = purchaseDate;
        ExpiryDate = expiryDate;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.adStatus = adStatus;
    }

    public String getPrice() {
        return price;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlanName() {
        return planName;
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

    public String getAdStatus() {
        return adStatus;
    }
}



