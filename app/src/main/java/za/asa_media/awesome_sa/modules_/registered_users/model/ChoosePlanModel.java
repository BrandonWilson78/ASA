package za.asa_media.awesome_sa.modules_.registered_users.model;

/**
 * Created by Snow-Dell-05 on 5/24/2017.
 */

public class ChoosePlanModel {

    private boolean isChecked=false;
    private String planId;
    private String planName;
    private String planSubtitle;
    private String planPrice;
    private String planBatch;

    public ChoosePlanModel(String planId, String planName, String planSubtitle, String planPrice, String planBatch) {
        this.planId = planId;
        this.planName = planName;
        this.planSubtitle = planSubtitle;
        this.planPrice = planPrice;
        this.planBatch = planBatch;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPlanSubtitle() {
        return planSubtitle;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public String getPlanBatch() {
        return planBatch;
    }
}
