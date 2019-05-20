package supersports.com.okmine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import supersports.com.okmine.adapter.VideoAdapter;
import supersports.com.okmine.entity.VideoInfoEntity;
import supersports.com.okmine.listener.FeedScrollListener;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecycleView;
    private VideoAdapter videoAdapter;
    private List<VideoInfoEntity> mVideoInfoEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initRv();
        bindListener();


    }

    private void initData() {
        mVideoInfoEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoInfoEntity entity = new VideoInfoEntity();
            if (i == 0
                    || i == 8
                    || i == 15
                    || i == 19) {
                entity.setmItemType(VideoAdapter.TYPE_VIDEO);
                entity.setmResId(R.drawable.b);
                entity.setVideoUrl("http://vjs.zencdn.net/v/oceans.mp4");
            } else {
                entity.setmItemType(VideoAdapter.TYPE_PIC);
                entity.setmResId(R.drawable.a);
                entity.setVideoUrl("");
            }
            mVideoInfoEntities.add(entity);

        }


    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        videoAdapter = new VideoAdapter(this, mVideoInfoEntities);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(videoAdapter);

    }

    private void bindListener() {
        mRecycleView.addOnScrollListener(new FeedScrollListener());
    }


    private void initView() {
        mRecycleView = findViewById(R.id.recycle_view);
    }
}
