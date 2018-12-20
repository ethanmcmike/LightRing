package ethanmcmike.lightring.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ethanmcmike.lightring.Flashlight.FlashlightActivity;
import ethanmcmike.lightring.R;
import ethanmcmike.lightring.Simon.SimonActivity;
import ethanmcmike.lightring.adapters.AppListAdapter;
import ethanmcmike.lightring.models.AppModel;

public class AppListActivity extends BaseActivity{

    RecyclerView recyclerView;
    AppListAdapter adapter;

    ArrayList<AppModel> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModels();
        initViews();
    }

    @Override
    protected int getContentView() {
        return R.layout.app_list;
    }

    private void initModels(){
        apps = new ArrayList();

        AppModel simon = new AppModel("Simon", SimonActivity.class);
        simon.iconRes = R.drawable.ic_simon;
        apps.add(simon);

        AppModel flashlight = new AppModel("Flashlight", FlashlightActivity.class);
        flashlight.iconRes = R.drawable.ic_flashlight;
        apps.add(flashlight);

        AppModel simulator = new AppModel("Simulator", SimulatorActivity.class);
        simulator.iconRes = R.drawable.ic_simulator;
        apps.add(simulator);

        AppModel timer = new AppModel("Timer", SimulatorActivity.class);
        timer.iconRes = R.drawable.ic_timer;
        apps.add(timer);
    }

    private void initViews(){
        recyclerView = findViewById(R.id.appListRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AppListAdapter(this, apps);
        recyclerView.setAdapter(adapter);
    }
}
