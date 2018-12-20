package ethanmcmike.lightring.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import ethanmcmike.lightring.R;
import ethanmcmike.lightring.RingController;

public abstract class BaseActivity extends AppCompatActivity {

    public static final int BLUETOOTH_REQUEST = 0;

    private Toolbar toolbar;
    private Menu menu;

    protected BluetoothAdapter btAdapter = null;

    public RingController ringController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initModels();
        initViews();
        initBluetooth();
    }

    protected abstract int getContentView();

    private void initModels(){
        ringController = new RingController(24);
    }

    private void initViews(){
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
    }

    private void initBluetooth(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if(btAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Available", Toast.LENGTH_LONG).show();
            finish();

        } else {
            if (!btAdapter.isEnabled()) {
                Intent requestBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(requestBT, 1);
            }
        }

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.toolbarBluetooth:
                Intent gotoDeviceList = new Intent(this, DeviceList.class);
                startActivityForResult(gotoDeviceList, BLUETOOTH_REQUEST);
                break;

            case R.id.toolbarSettings:
                System.out.println("Settings");
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case BLUETOOTH_REQUEST:
                if(resultCode == RESULT_OK) {
                    menu.findItem(R.id.toolbarBluetooth).setIcon(R.drawable.bluetooth);
                    Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
                }
                else if(resultCode == RESULT_CANCELED) {
                    menu.findItem(R.id.toolbarBluetooth).setIcon(R.drawable.disconnected);
                    Toast.makeText(this, "Could not connect.", Toast.LENGTH_SHORT).show();
                }

        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            System.out.println("RING: REVEIVED");

            if(intent.getAction().equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {

                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state) {

                    case BluetoothAdapter.STATE_CONNECTED:
                        System.out.println("RING: Receiver connected!");
                        Toast.makeText(BaseActivity.this, "Bluetooth Connected", Toast.LENGTH_SHORT).show();
                        break;

                    case BluetoothAdapter.STATE_DISCONNECTED:
                        System.out.println("RING: Receiver disconnected!");
                        Toast.makeText(BaseActivity.this, "Bluetooth Disconnected", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };
}
