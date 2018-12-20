package ethanmcmike.lightring.Simon;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ethanmcmike.lightring.BTController;
import ethanmcmike.lightring.R;
import ethanmcmike.lightring.RingController;
import ethanmcmike.lightring.activities.BaseActivity;

public class SimonActivity extends BaseActivity {

    public static int NUM_LEDS = 24;

    SimonModel model;
    SimonView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    @Override
    protected int getContentView() {
        return R.layout.simon;
    }

    private void initViews(){
        view = findViewById(R.id.simon_view);
        view.setModel(model);

        ringController = new RingController(NUM_LEDS);
    }
}
