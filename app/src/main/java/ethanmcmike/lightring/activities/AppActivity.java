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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ethanmcmike.lightring.R;
import ethanmcmike.lightring.RingController;

public abstract class AppActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initModels();
    }

    protected abstract int getContentView();

    private void initModels(){
        ringController = new RingController(24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
