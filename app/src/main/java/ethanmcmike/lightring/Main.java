package ethanmcmike.lightring;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import ethanmcmike.lightring.models.RingModel;

public class Main extends AppCompatActivity {

    BTController bt;
    RingController ring;
    RingModel ringModel;

    //Elements
    EditText input;
    SeekBar inputRed, inputGreen, inputBlue;
    Button sendButton, red, green, blue, purple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupBluetooth();
        setupElements();

        ring = new RingController(24);
        ring.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ring.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ring.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ring.stop();
    }

    private void setupBluetooth(){
//        Intent intent = getIntent();
//        String address = intent.getStringExtra(DeviceList.EXTRA_ADDRESS);
//        bt = new BTController(address);
    }

    private void setupElements(){
        input = findViewById(R.id.input);
        inputRed = findViewById(R.id.input_red);
        inputRed.setOnSeekBarChangeListener(onChange);
        inputGreen = findViewById(R.id.input_green);
        inputGreen.setOnSeekBarChangeListener(onChange);
        inputBlue = findViewById(R.id.input_blue);
        inputBlue.setOnSeekBarChangeListener(onChange);

        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(changeColor);

        red =       findViewById(R.id.red);
        green =     findViewById(R.id.green);
        blue =      findViewById(R.id.blue);
        purple =    findViewById(R.id.purple);
    }

    View.OnClickListener changeColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int r = inputRed.getProgress();
            int g = inputGreen.getProgress();
            int b = inputBlue.getProgress();

            ringModel.setLED(r, g, b);

            ring.setLED(r, g, b);
            ring.render();
        }
    };

    long lastTime = 0;

    SeekBar.OnSeekBarChangeListener onChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int r = inputRed.getProgress();
            int g = inputGreen.getProgress();
            int b = inputBlue.getProgress();

            long now = System.currentTimeMillis();
            if(now - lastTime > 17) {
                ringModel.setLED(r, g, b);

                ring.setLED(r, g, b);
                ring.render();
                lastTime = now;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}