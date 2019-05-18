package supersports.com.okmine.entity;

import java.io.Serializable;

/**
 * Created by rocky on 2019/5/18.
 */

public class VideoInfoEntity implements Serializable{

    public int mItemType;

    public String videoUrl;

    public int mResId;


    public int getmItemType() {
        return mItemType;
    }

    public void setmItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getmResId() {
        return mResId;
    }

    public void setmResId(int mResId) {
        this.mResId = mResId;
    }
}
