package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.UUID;

/**
 * Created by izumin on 1/3/2016 AD.
 */
class RxBleScannerJB extends RxBleScannerImpl<ScanResultJB> {

    protected RxBleScannerJB(BluetoothAdapter adapter) {
        super(adapter);
    }

    @Override
    void startScanImpl(UUID... serviceUuids) {
        if (serviceUuids.length == 0) {
            getAdapter().startLeScan(scanCallback);
        } else {
            getAdapter().startLeScan(serviceUuids, scanCallback);
        }
    }

    @Override
    protected void stopScanImpl() {
        getAdapter().stopLeScan(scanCallback);
    }

    private final BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            onNext(new ScanResultJB(device, rssi, scanRecord));
        }
    };
}
