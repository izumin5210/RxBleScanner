package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothAdapter;

import java.util.UUID;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by izumin on 1/2/2016 AD.
 */
abstract class RxBleScannerImpl<T> {

    private final BluetoothAdapter adapter;
    private PublishSubject<T> subject;

    protected RxBleScannerImpl(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    Observable<T> startScan(UUID... serviceUuids) {
        startScanImpl(serviceUuids);
        return getSubject();
    }

    void stopScan() {
        getSubject().onCompleted();
        stopScanImpl();
    }

    protected BluetoothAdapter getAdapter() {
        return adapter;
    }

    protected PublishSubject<T> getSubject() {
        if (subject == null || subject.hasCompleted() || subject.hasThrowable()) {
            subject = PublishSubject.create();
        }
        return subject;
    }

    protected void onNext(T result) {
        getSubject().onNext(result);
    }

    abstract void startScanImpl(UUID... serviceUuids);
    abstract void stopScanImpl();
}
