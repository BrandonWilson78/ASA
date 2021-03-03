package za.asa_media.awesome_sa.modules_.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Snow-Dell-05 on 4/27/2017.
 */

public class NearByPlacesData implements Parcelable {
    public static final Creator<NearByPlacesData> CREATOR = new Creator<NearByPlacesData>() {
        @Override
        public NearByPlacesData createFromParcel(Parcel in) {
            return new NearByPlacesData(in);
        }

        @Override
        public NearByPlacesData[] newArray(int size) {
            return new NearByPlacesData[size];
        }
    };
    private String placeName = "";
    private int drawableName;

    public NearByPlacesData(String placeName, int drawableName) {
        this.placeName = placeName;
        this.drawableName = drawableName;
    }

    protected NearByPlacesData(Parcel in) {
        placeName = in.readString();
        drawableName = in.readInt();
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getDrawableName() {
        return drawableName;
    }

    public void setDrawableName(int drawableName) {
        this.drawableName = drawableName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeInt(drawableName);
    }
}
