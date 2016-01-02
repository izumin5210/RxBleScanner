package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothDevice;

/**
 * Created by izumin on 1/3/2016 AD.
 */
public class ScanResultJB {
    private final BluetoothDevice device;
    private final int rssi;
    private final byte[] scanRecord;

    public ScanResultJB(BluetoothDevice device, int rssi, byte[] scanRecord) {
        this.device = device;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public int getRssi() {
        return rssi;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }
}
