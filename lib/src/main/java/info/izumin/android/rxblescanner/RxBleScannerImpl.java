package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothAdapter;

import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by izumin on 1/2/2016 AD.
 */
abstract class RxBleScannerImpl<T> {

    enum State {
        SCANNING,
        NOT_SCANNING
    }

    private final BluetoothAdapter adapter;
    private Subscriber<? super T> subscriber;
    private State state = State.NOT_SCANNING;

    protected RxBleScannerImpl(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    Observable<T> startScan(UUID... serviceUuids) {
        startScanImpl(serviceUuids);
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                setSubscriber(subscriber);
                setState(State.SCANNING);
            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (getSubscriber().isUnsubscribed() && getState() != State.NOT_SCANNING) {
                    setState(State.NOT_SCANNING);
                    stopScanImpl();
                }
            }
        });
    }

    void stopScan() {
        setState(State.NOT_SCANNING);
        subscriber.onCompleted();
        stopScanImpl();
    }

    protected BluetoothAdapter getAdapter() {
        return adapter;
    }

    protected void setSubscriber(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    protected Subscriber<? super T> getSubscriber() {
        return subscriber;
    }

    protected void setState(State state) {
        this.state = state;
    }

    protected State getState() {
        return state;
    }

    protected void onNext(T result) {
        subscriber.onNext(result);
    }

    protected void onError(Throwable e) {
        setState(State.NOT_SCANNING);
        subscriber.onError(e);
        stopScanImpl();
    }

    abstract void startScanImpl(UUID... serviceUuids);
    abstract void stopScanImpl();
}
