package za.asa_media.awesome_sa.modules_.registered_users.model;

/**
 * Created by Snow-Dell-05 on 5/8/2017.
 */
/*
{
  "status": true,
  "message": "Plan Notifications Details",
  "data": [
    {
      "id": "1",
      "plan_name": "Once of Ad",
      "detail": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
      "purchase_date": "2017-06-01 10:50:32",
      "activation_date": "2017-06-01",
      "expiry_date": "2017-07-02"
    }
  ]
}
*/

public class NotificationModel {

    private String planName;
    private String purchaseDate;
    private String activationDate;
    private String expiryDate;
    private String mDetail;

    public NotificationModel(String planName, String purchaseDate, String activationDate, String expiryDate, String mDetail) {
        this.planName = planName;
        this.purchaseDate = purchaseDate;
        this.activationDate = activationDate;
        this.expiryDate = expiryDate;
        this.mDetail = mDetail;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getmDetail() {
        return mDetail;
    }
}
