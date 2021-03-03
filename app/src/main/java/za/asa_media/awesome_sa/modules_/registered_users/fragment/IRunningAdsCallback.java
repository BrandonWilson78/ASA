package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import za.asa_media.awesome_sa.modules_.registered_users.model.RunningAdsModel;

/**
 * Created by Snow-Dell-05 on 6/12/2017.
 */

public interface IRunningAdsCallback {
    void getRunningAdDetail(String batch_id,RunningAdsModel runningAdModel);



}
