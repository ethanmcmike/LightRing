package ethanmcmike.lightring.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ethanmcmike.lightring.R;
import ethanmcmike.lightring.views.SimulatorView;

public class SimulatorActivity extends BaseActivity {

    SimulatorView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setContentView(view);

        for(int i=0; i<24; i++)
            ringController.model.setLED(i, 250, 200, 0);
    }

    @Override
    protected int getContentView() {
        return R.layout.blank;
    }

    private void initViews(){
        view = new SimulatorView(this, ringController.model);
    }
}
