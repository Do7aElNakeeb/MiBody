package com.mibody.app.activity;

/**
 * Created by NakeebMac on 10/14/16.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.mibody.app.R;

import static com.mibody.app.activity.BTDeviceList.EXTRA_DEVICE_ADDRESS;

public class WorkoutPlay  extends Activity {

    Button btnOn, btnOff;
    TextView workoutName, processName, counter, status, sensorView1, sensorView2, sensorView3;
    Handler bluetoothIn;

    int processCount = 0;
    int exerciseCount = 1;
    int settedToleranceTime = 10;
    int toleranceTime1 = 0;
    int toleranceTime2 = 0;
    String oldMsg = "";
    Calendar calendar ;
    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.workout_play);

        //Link the buttons and textViews to respective views
        /*
        btnOn = (Button) findViewById(R.id.buttonOn);
        btnOff = (Button) findViewById(R.id.buttonOff);

        sensorView0 = (TextView) findViewById(R.id.sensorView0);
        sensorView1 = (TextView) findViewById(R.id.sensorView1);
        sensorView2 = (TextView) findViewById(R.id.sensorView2);
        sensorView3 = (TextView) findViewById(R.id.sensorView3);

        */

        SharedPreferences prefs = getSharedPreferences("BT", MODE_PRIVATE);
        String MacAddress = prefs.getString("BT_MAC", "");

        if (!MacAddress.isEmpty()){
            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(WorkoutPlay.this, BTDeviceList.class);
            startActivity(i);
        }

        calendar = Calendar.getInstance();
        workoutName = (TextView) findViewById(R.id.workout_name);
        processName = (TextView) findViewById(R.id.process_name);
        counter = (TextView) findViewById(R.id.counter);
        status = (TextView) findViewById(R.id.status);

        processName.setText("Exercise " + exerciseCount + "/3");
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread

                    calendar = Calendar.getInstance();
                    if(!readMessage.isEmpty()){

                        processCount = Integer.parseInt(readMessage);

                        if(toleranceTime1 == 0){
                            toleranceTime1 = calendar.get(Calendar.SECOND);
                        }
                        if (oldMsg.isEmpty()){
                            oldMsg = readMessage;
                        }

                        toleranceTime2 = calendar.get(Calendar.SECOND);

                        if (toleranceTime2 - toleranceTime1 > settedToleranceTime && oldMsg.equals(readMessage)){
                            status.setVisibility(View.VISIBLE);

                            if (processCount <15){
                                status.setText("Failed!");
                                new CountDownTimer(10000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        counter.setText(String.valueOf(millisUntilFinished / 1000));
                                    }

                                    @Override
                                    public void onFinish() {
                                        exerciseCount++;
                                        status.setText("");
                                        if (exerciseCount > 3){
                                            processName.setVisibility(View.GONE);
                                            counter.setTextSize(80);
                                            counter.setText("Done!");
                                        }
                                        else {
                                            processName.setText("Exercise " + exerciseCount + "/3");
                                        }
                                    }
                                }.start();
                            }
                            else if (processCount == 15){
                                status.setText("Success");
                                new CountDownTimer(10000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        counter.setText(String.valueOf(millisUntilFinished / 1000));
                                    }

                                    @Override
                                    public void onFinish() {
                                        exerciseCount++;
                                        status.setText("");
                                        if (exerciseCount > 3){
                                            processName.setVisibility(View.GONE);
                                            counter.setTextSize(80);
                                            counter.setText("Done!");
                                        }
                                        else {
                                            processName.setText("Exercise " + exerciseCount + "/3");
                                        }
                                    }
                                }.start();
                            }
                            else {
                                status.setText("Great");
                                new CountDownTimer(10000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        counter.setText(String.valueOf(millisUntilFinished / 1000));
                                    }

                                    @Override
                                    public void onFinish() {
                                        exerciseCount++;
                                        status.setText("");
                                        if (exerciseCount > 3){
                                            processName.setVisibility(View.GONE);
                                            counter.setTextSize(80);
                                            counter.setText("Done!");
                                        }
                                        else {
                                            processName.setText("Exercise " + exerciseCount + "/3");
                                        }
                                    }
                                }.start();
                            }

                            processName.setText("Rest Time");
                          //  oldMsg = "";

                        }

                        else {
                            counter.setText(readMessage);

                            Log.d("TimeUp", toleranceTime1 + " - " + toleranceTime2 + " - "+ oldMsg + " - " + readMessage);

                            if (!oldMsg.equals(readMessage)){
                                toleranceTime1 = toleranceTime2;
                            }
                            oldMsg = readMessage;

                        }

                    }

                    /*
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            sensorView0.setText(" Sensor 0 Voltage = " + sensor0 + "V");	//update the textviews with sensor values
                            sensorView1.setText(" Sensor 1 Voltage = " + sensor1 + "V");
                            sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
                            sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                    */
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
/*

        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btnOff.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
            }
        });

        btnOn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("Start");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {

            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}

