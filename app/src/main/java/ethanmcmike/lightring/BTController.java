package ethanmcmike.lightring;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BTController{

    public static BTController instance;

    BluetoothAdapter adapter;

    public String address;
    public BluetoothSocket socket;
    public OutputStream os;
    public InputStream is;

    public byte[] buffer = new byte[256];

    public BTController(String address){
        instance = this;
        this.address = address;
        adapter = BluetoothAdapter.getDefaultAdapter();
        connect();
    }

    public void connect(){

        Set<BluetoothDevice> devices = adapter.getBondedDevices();

        for(BluetoothDevice device : devices){

            if(device.getAddress().equals(address)){

                try {
                    UUID myUUID = UUID.fromString(device.getUuids()[0].toString());
                    socket = device.createRfcommSocketToServiceRecord(myUUID);
                    socket.connect();

                    os = socket.getOutputStream();
                    is = socket.getInputStream();

                } catch(IOException e){
                    System.err.println(e);
                }

                return;
            }
        }
    }

    public void send(int val){
        try {
            os.write(val);
        } catch(IOException e){

        }
    }

    public void send(int[] data){

        byte[] msg = new byte[data.length];
        for(int i=0; i<data.length; i++){
            msg[i] = intToByte(data[i]);
        }

        try{
            os.write(msg);
        } catch (IOException e){

        }
    }

    public void send(int[] data, int off, int len){

        byte[] msg = new byte[data.length];
        for(int i=0; i<data.length; i++){
            msg[i] = intToByte(data[i]);
        }

        try{
            os.write(msg, off, len);
        } catch (IOException e){

        }
    }

    public int read(){
        try{
            return is.read(buffer, 0, buffer.length);

        } catch(IOException e){

        }

        return 0;
    }

    public void close(){
        try {
            os.close();
            is.close();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static int byteToInt(byte b){
        return b & 0xFF;
    }

    public static byte intToByte(int i){
        return (byte)i;
    }
}
