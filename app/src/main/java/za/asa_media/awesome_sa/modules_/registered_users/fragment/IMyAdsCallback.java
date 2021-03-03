package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import za.asa_media.awesome_sa.modules_.registered_users.model.CreateAdModel;

/**
 * Created by Snow-Dell-05 on 5/26/2017.
 */

public interface IMyAdsCallback {

    void getAdDetail(String batch_id, CreateAdModel createAdModel, String[] mbatchs);
    void getPlanDetail(String batch_id, String planId);


}
