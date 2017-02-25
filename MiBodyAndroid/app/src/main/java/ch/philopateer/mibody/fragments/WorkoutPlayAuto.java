package ch.philopateer.mibody.fragments;

/**
 * Created by NakeebMac on 10/14/16.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.app.WorkoutItem;

public class WorkoutPlayAuto extends Fragment {

    Button btnOn, btnOff;
    TextView workoutName, processName, counter, status, sensorView1, sensorView2, sensorView3;
    Handler bluetoothIn;

    //FragmentManager fm = getSupportFragmentManager();
    WorkoutItem workoutItem;
  //  ArrayList<WorkoutItem> workoutItemArrayList;
  //  ArrayList<WorkoutExItem> workoutExItemArrayList;

    int flagT = 0;
    int flagF = 0;
    int flagF0 = 0;
    int flagF1 = 0;
    int processCount = 0;
    int exerciseCount = 1;
    int setExCount = 1;
    int workoutReps = 1;
    int setCount = 1;
    int repsCount = 1;
    int settedToleranceTime = 5;
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

    public WorkoutPlayAuto(WorkoutItem workoutItem){
        this.workoutItem = workoutItem;
    }

    public void setBTAddress(String Baddress){
        Log.d("Movie Name4", Baddress);
        address = Baddress;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            address = savedInstanceState.getString("address");
        }

        /**
         * Exercise A
         * 1/12 reps
         * 1/3 sets
         * 2/4 exercise
         * 1/2 workout reps
         */

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                             //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread

                    Log.d("received msg", readMessage);

                    if (readMessage.equals("t")){
                        flagT = 1;
                        //mConnectedThread.write("T");
                        workoutName.setText(workoutItem.workoutName);

                        processName.setText(AppConfig.exercises_names[Integer.valueOf(workoutItem.exercisesList.get(setExCount-1).name)] + "\n"
                                + workoutItem.exercisesList.get(setExCount-1).restTime + " Reps\n"
                                + setCount + " / " + workoutItem.exercisesList.get(setExCount-1).exReps + " Sets\n"
                                + (exerciseCount) + " / " + String.valueOf(workoutItem.exercisesList.size()) + " Exercises\n"
                                + workoutReps + " / " + workoutItem.workoutReps + " WorkoutFragment Reps");

                    }
                    /*
                    else if (readMessage.equals("f") && flagF1 == 0){
                        flagF = 1;
                        while (flagT == 0){
                            mConnectedThread.write("t");
                        }
                    }
                    */
                    else if (readMessage.equals("f")){
                        flagF = 1;
                    }
                    else if (!readMessage.isEmpty()) {

                        int endOfLineIndex = 0;
                        for (int i = 0; i <readMessage.length(); i++){
                            /*
                            if (readMessage.charAt(i) != 't' && readMessage.charAt(i) != 'f'){
                                recDataString.append(readMessage.charAt(i));
                                endOfLineIndex = recDataString.indexOf("#");
                            }
                            */
                            if (readMessage.charAt(i) == '*'){
                                recDataString.append(readMessage.charAt(i));
                            }
                            else if (readMessage.charAt(i) == '#'){
                                recDataString.append(readMessage.charAt(i));
                                endOfLineIndex = recDataString.indexOf("#");
                            }
                            else {
                                recDataString.append(readMessage.charAt(i));
                            }
                        }

                        if(endOfLineIndex > 0){
                            String strCount = recDataString.substring(1, endOfLineIndex);
                            Log.d("strCount", strCount);

                            calendar = Calendar.getInstance();
                            processCount = Integer.parseInt(strCount);

                            if(toleranceTime1 == 59){
                                toleranceTime1 = calendar.get(Calendar.SECOND);
                            }
                            if (oldMsg.equals("~")){
                                oldMsg = strCount;
                            }

                            toleranceTime2 = calendar.get(Calendar.SECOND);

                            if (Math.abs(toleranceTime2 - toleranceTime1) >= settedToleranceTime && oldMsg.equals(strCount)){
                                status.setVisibility(View.VISIBLE);

                                Log.d("processtatus", oldMsg + strCount);

                                processStatus(processCount, workoutItem.exercisesList.get(setExCount-1).restTime);

                                processName.setText("Rest Time");
                                oldMsg = "~";
                                toleranceTime1 = 59;

                            }

                            else {
                                counter.setText(strCount);

                                Log.d("TimeUp", toleranceTime1 + " - " + toleranceTime2 + " - "+ oldMsg + " - " + strCount);

                                if (!oldMsg.equals(strCount)){
                                    toleranceTime1 = toleranceTime2;
                                }
                                oldMsg = strCount;

                            }

                            recDataString.delete(0, recDataString.length());
                        }
                        /*
                        String strCount = "";
                        for (int i = 0; i <readMessage.length(); i++){
                            if (readMessage.charAt(i) != 't'){
                                strCount += readMessage.charAt(i);
                            }
                        }
                        */

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


    private void processStatus(int processCount, int reps){

        if(processCount < reps){
            status.setText("Failed!");
        }
        else if(processCount == reps){
            status.setText("Success!");
        }
        else if(processCount > reps){
            status.setText("Great!");
        }

        mConnectedThread.write("f");
        /*
        while (flagF == 0 && flagF0 == 0) {
            mConnectedThread.write("f");
        }
*/
        new CountDownTimer(workoutItem.exercisesList.get(setExCount-1).restTime * 1000 , 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                counter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                status.setText("");
                counter.setText("0");

                if (setCount >= workoutItem.exercisesList.get(setExCount-1).exReps){
                    if (exerciseCount >= workoutItem.exercisesList.size()){
                        if (workoutReps >= workoutItem.workoutReps){
                            processName.setVisibility(View.GONE);
                            counter.setTextSize(80);
                            counter.setText("Done!");
                            flagF1 = 1;
                            flagT = 1;
                        }
                        else {
                            setCount = 1;
                            exerciseCount = 1;
                            setExCount = 1;
                            workoutReps++;
                            flagT = 0;
                        }
                    }
                    else {
                        setCount = 1;
                        setExCount++;
                        exerciseCount++;
                        flagT = 0;
                    }
                }
                else {
                    setCount++;
                    flagT = 0;
                }

                processName.setText(AppConfig.exercises_names[Integer.valueOf(workoutItem.exercisesList.get(setExCount - 1).name)] + "\n"
                        + workoutItem.exercisesList.get(setExCount-1).restTime + " Reps\n"
                        + setCount + " / " + workoutItem.exercisesList.get(setExCount - 1).exReps + " Sets\n"
                        + (exerciseCount) + " / " + String.valueOf(workoutItem.exercisesList.size()) + " Exercises\n"
                        + workoutReps + " / " + workoutItem.workoutReps + " WorkoutFragment Reps");

                if (flagT == 0){
                    mConnectedThread.write("t");
                }

                /*
                while (flagT == 0 && flagF1 == 0) {
                    mConnectedThread.write("t");
                    flagF0 = 0;
                }
                */
            }
        }.start();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("address", address);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.workout_play, container, false);

        calendar = Calendar.getInstance();
        workoutName = (TextView) view.findViewById(R.id.workout_name);
        processName = (TextView) view.findViewById(R.id.process_name);
        counter = (TextView) view.findViewById(R.id.counter);
        status = (TextView) view.findViewById(R.id.status);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
    //    Intent intent = getActivity().getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
       // address = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);

//        Log.d("BTAddress1", address);


        Log.d("BTAddress2", address);
        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
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
        mConnectedThread.write("t");
        /*
        while (flagT == 0) {
            mConnectedThread.write("t");
        }
        */
    }

    @Override
    public void onPause()
    {
        super.onPause();
        flagF = 0;
        flagT = 0;
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
            Toast.makeText(getContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
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
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Failed to Connect", Toast.LENGTH_SHORT).show();
            }

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
                    if (readMessage.equals("t")){
                        flagT = 1;
                        Log.d("flagTValue", String.valueOf(flagT));
                    }

                    if (readMessage.equals("f")){
                        flagF = 1;
                        flagF0 = 1;
                        Log.d("flagFValue", String.valueOf(flagF));
                    }
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            Log.d("writeMsg", input);
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {

                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                getActivity().finish();

            }
        }
    }
}

