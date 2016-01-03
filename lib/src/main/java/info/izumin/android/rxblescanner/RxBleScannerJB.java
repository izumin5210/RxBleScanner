package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * Created by izumin on 1/3/2016 AD.
 */
class RxBleScannerJB extends RxBleScannerImpl<ScanResultJB> {

    protected RxBleScannerJB(BluetoothAdapter adapter) {
        super(adapter);
    }

    @Override
    protected void startScanImpl() {
        getAdapter().startLeScan(scanCallback);
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