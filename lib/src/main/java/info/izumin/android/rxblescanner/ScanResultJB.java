package info.izumin.android.rxblescanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.Build;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScanResultJB(ScanResult result) {
        this.device = result.getDevice();
        this.rssi = result.getRssi();
        if (result.getScanRecord() == null) {
            this.scanRecord = new byte[]{};
        } else {
            this.scanRecord = result.getScanRecord().getBytes();
        }
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
