package ethanmcmike.lightring.Flashlight;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ethanmcmike.lightring.R;
import ethanmcmike.lightring.RingController;
import ethanmcmike.lightring.activities.AppActivity;

public class FlashlightActivity extends AppActivity {

    RingController ring;

    FlashlightView view;
    FlashlightModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    @Override
    protected int getContentView() {
        return R.layout.flashlight;
    }

    private void initViews(){

        ring = new RingController(24);

        model = new FlashlightModel();

        view = findViewById(R.id.flashlight_view);
        view.setModel(model);
        view.setRing(ring);
    }
}
