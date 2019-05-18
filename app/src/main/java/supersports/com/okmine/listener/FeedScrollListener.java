package supersports.com.okmine.listener;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import supersports.com.okmine.R;
import supersports.com.okmine.adapter.VideoAdapter;
import supersports.com.okmine.entity.VideoInfoEntity;
import supersports.com.okmine.view.CustomVideoView;

public class FeedScrollListener extends RecyclerView.OnScrollListener {
    private int firstVisibleItem = 0;
    private int lastVisibleItem = 0;
    private int visibleCount = 0;
    private FrameLayout rootLayout;

    /**
     * 视频状态标签
     */
    private enum VideoTagEnum {

        /**
         * 自动播放视频
         */
        TAG_AUTO_PLAY_VIDEO,

        /**
         * 暂停视频
         */
        TAG_PAUSE_VIDEO
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                checkViewHolder(recyclerView, VideoTagEnum.TAG_AUTO_PLAY_VIDEO);
                break;
            default:
//                autoPlayVideo(recyclerView, VideoTagEnum.TAG_PAUSE_VIDEO);
                break;
        }
    }

    private void checkViewHolder(RecyclerView recyclerView, VideoTagEnum tagAutoPlayVideo) {
        for (int child_index = 0; child_index < visibleCount; child_index++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(child_index));
            if (viewHolder instanceof VideoAdapter.VideoViewHolder) {

                VideoAdapter.VideoViewHolder videoViewHolder = (VideoAdapter.VideoViewHolder) viewHolder;
                rootLayout = videoViewHolder.rootLayout;
                if (rootLayout != null) {
                    Rect rect = new Rect();
                    rootLayout.getLocalVisibleRect(rect);
                    int videoheight = rootLayout.getHeight();
                    if (rect.top == 0 && rect.bottom == videoheight) {
                        addVideoView(rootLayout);
                    } else {
                        removeVideoView(rootLayout);
                    }
                }


            }
        }

    }


    public CustomVideoView createVideoView(Context mContext, VideoInfoEntity infoEntity) {
        CustomVideoView customVideoView = new CustomVideoView(mContext);
        customVideoView.setVideoPath(infoEntity.getVideoUrl());
        customVideoView.start();
        customVideoView.setCurrentState(CustomVideoView.STATE_PLAYING);
        return customVideoView;

    }


    public void release(CustomVideoView videoView) {
        videoView.pause();
        videoView.stopPlayback();
        videoView = null;

    }


    private void removeVideoView(FrameLayout rootLayout) {
        if (rootLayout.getChildCount() > 0) {
            CustomVideoView customVideoView = (CustomVideoView) rootLayout.getChildAt(0);
            customVideoView.setCurrentState(CustomVideoView.STATE_PAUSE);
            rootLayout.removeView(customVideoView);
            release(customVideoView);
        }
    }

    /**
     * 滚动过程中监听动态加载视频
     */
    public void addVideoView(FrameLayout rootLayout) {

        if (rootLayout.getChildCount() > 0) {
            CustomVideoView customVideoView = (CustomVideoView) rootLayout.getChildAt(0);
            if (customVideoView.getCurrentState() == CustomVideoView.STATE_PLAYING) {
                return;
            }
        }
        VideoInfoEntity videoInfoEntity = (VideoInfoEntity) rootLayout.getTag();
        rootLayout.removeAllViews();
        rootLayout.addView(createVideoView(rootLayout.getContext(), videoInfoEntity));

    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearManager.findFirstVisibleItemPosition();
            lastVisibleItem = linearManager.findLastVisibleItemPosition();
            visibleCount = lastVisibleItem - firstVisibleItem;
        }

        if (rootLayout != null) {
            Rect rect = new Rect();
            rootLayout.getLocalVisibleRect(rect);
            int videoheight = rootLayout.getHeight();
            if (!(rect.top == 0 && rect.bottom == videoheight)) {
                removeVideoView(rootLayout);
            }
        }

    }


}
