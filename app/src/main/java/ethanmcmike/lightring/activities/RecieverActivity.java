package ethanmcmike.lightring.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ethanmcmike.lightring.BTController;
import ethanmcmike.lightring.R;
import ethanmcmike.lightring.RingController;

public class RecieverActivity extends DeviceList implements Runnable{

    String address;
    BTController bt;
    RingController ring;

    Thread thread;
    boolean running;

    EditText input;
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        input = findViewById(R.id.bt_input);
        send = findViewById(R.id.bt_send);
        send.setOnClickListener(onSend);

        Intent intent = getIntent();
        address = intent.getStringExtra(DeviceList.EXTRA_ADDRESS);
        bt = new BTController(address);

        start();

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filter);
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void run(){

        running = true;

        while(running){

            int bytes = bt.read();

            System.out.print("INPUT:");
            for(int i=0; i<bytes; i++) {
                System.out.print (" " + BTController.byteToInt(bt.buffer[i]));
            }
            System.out.println();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        running = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    View.OnClickListener onSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = input.getText().toString();
            for(char c : msg.toCharArray()){
                bt.send(c);
            }
        }
    };

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)){

                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_DISCONNECTED:
                        break;

                }
            }
        }
    };
}
