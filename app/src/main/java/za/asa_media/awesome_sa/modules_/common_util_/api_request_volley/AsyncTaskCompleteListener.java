package za.asa_media.awesome_sa.modules_.common_util_.api_request_volley;

/**
 * Created by Snow-Dell-07 on 05-Dec-17.
 */

public interface AsyncTaskCompleteListener {
    void onTaskCompleted(String response, int serviceCode);
    void onErrorFound(String errorResponse, int serviceCode);
}
