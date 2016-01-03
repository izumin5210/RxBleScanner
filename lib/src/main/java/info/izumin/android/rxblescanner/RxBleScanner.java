package info.izumin.android.rxblescanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import rx.Observable;

/**
 * Created by izumin on 1/2/2016 AD.
 */
public class RxBleScanner {

    private final BluetoothAdapter adapter;

    private RxBleScannerImpl scannerImpl;

    public RxBleScanner(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Observable<ScanResult> startScan() {
        final RxBleScannerL scanner = new RxBleScannerL(adapter);
        scannerImpl = scanner;
        return scanner.startScan();
    }

    public Observable<ScanResultJB> startScanJB() {
        final RxBleScannerJB scanner = new RxBleScannerJB(adapter);
        scannerImpl = scanner;
        return scanner.startScan();
    }

    public void stopScan() {
        if (scannerImpl != null) {
            scannerImpl.stopScan();
        }
    }
}
