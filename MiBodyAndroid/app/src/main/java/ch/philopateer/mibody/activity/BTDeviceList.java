package ch.philopateer.mibody.activity;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.content.SharedPreferences.Editor;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by NakeebMac on 10/9/16.
 */

public class BTDeviceList extends Fragment {
    // Debugging for LOGCAT
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    OnDeviceSelected onDeviceSelected;

    // declare button for launching website and textview for connection status
    Button tlbutton;
    TextView textView1;

    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(ch.philopateer.mibody.R.layout.device_list, container, false);
/*
        SharedPreferences prefs = getActivity().getSharedPreferences("BT", MODE_PRIVATE);
        String MacAddress = prefs.getString("BT_MAC", "");

        if (!MacAddress.isEmpty()){
            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(getActivity(), WorkoutPlayAuto.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, MacAddress);
            startActivity(i);
        }
*/
        checkBTState();

        textView1 = (TextView) view.findViewById(ch.philopateer.mibody.R.id.connecting);
        textView1.setTextSize(40);
        textView1.setText(" ");

        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this.getActivity(), ch.philopateer.mibody.R.layout.device_name);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) view.findViewById(ch.philopateer.mibody.R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView1.setText("Connecting...");
                // Get the device MAC address, which is the last 17 chars in the View
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                SharedPreferences prefs = getActivity().getSharedPreferences("BT", MODE_PRIVATE);
                Editor editor = prefs.edit();
                editor.putString("BT_MAC", address);
                editor.apply();

                onDeviceSelected.onDeviceSelected(address);
             //   getActivity().getSupportFragmentManager().beginTransaction().remove(getParentFragment()).commit();


            }
        });

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices and append to 'pairedDevices'
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // Add previosuly paired devices to the array
        if (pairedDevices.size() > 0) {
            view.findViewById(ch.philopateer.mibody.R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(ch.philopateer.mibody.R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }

        return view;
    }

    // Container Activity must implement this interface
    public interface OnDeviceSelected {
        void onDeviceSelected(String address);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onDeviceSelected = (OnDeviceSelected) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnMovieSelected");
        }
    }

/*
    @Override
    public void onResume()
    {
        super.onResume();
        //***************
        checkBTState();

        textView1 = (TextView) findViewById(R.id.connecting);
        textView1.setTextSize(40);
        textView1.setText(" ");

        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices and append to 'pairedDevices'
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // Add previosuly paired devices to the array
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }
*/
    /*
    // Set up on-click listener for the list (nicked this - unsure)
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            textView1.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            SharedPreferences prefs = getActivity().getSharedPreferences("BT", MODE_PRIVATE);
            Editor editor = prefs.edit();
            editor.putString("BT_MAC", address);
            editor.apply();

            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(getActivity(), WorkoutPlayAuto.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };
*/
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter=BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBtAdapter==null) {
            Toast.makeText(getActivity(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }

}
