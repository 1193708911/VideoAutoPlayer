package supersports.com.okmine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import supersports.com.okmine.R;
import supersports.com.okmine.entity.VideoInfoEntity;

/**
 * Created by rocky on 2019/5/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<VideoInfoEntity> videoInfoEntities;

    public static final int TYPE_PIC = 1;
    public static final int TYPE_VIDEO = 2;

    public VideoAdapter(Context context, List<VideoInfoEntity> entities) {
        this.mContext = context;
        this.videoInfoEntities = entities;
    }

    @Override
    public int getItemViewType(int position) {
        return videoInfoEntities.get(position).getmItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PIC:
                return new PicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic_view, null));
            case TYPE_VIDEO:
                return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_view, null));
            default:
                return new DefaultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_default_view, null));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoInfoEntity videoInfoEntity = videoInfoEntities.get(position);
        if (holder instanceof VideoViewHolder) {
            VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
            videoViewHolder.bindData(videoInfoEntity);
        } else if (holder instanceof PicViewHolder) {
            PicViewHolder picViewHolder = (PicViewHolder) holder;
            picViewHolder.bindData(videoInfoEntity);
        }
    }

    @Override
    public int getItemCount() {
        return videoInfoEntities.size();
    }


    public static class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public PicViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }

        public void bindData(VideoInfoEntity videoInfoEntity) {
            mImageView.setImageResource(videoInfoEntity.getmResId());
        }
    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout rootLayout;

        public VideoViewHolder(View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.root);
        }

        public void bindData(VideoInfoEntity videoInfoEntity) {
            rootLayout.setTag(videoInfoEntity);
        }
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder {

        public DefaultViewHolder(View itemView) {
            super(itemView);
        }
    }
}
