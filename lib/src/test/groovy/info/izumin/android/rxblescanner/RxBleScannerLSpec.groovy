package info.izumin.android.rxblescanner
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import rx.observers.TestSubscriber
import spock.lang.Specification
/**
 * Created by izumin on 1/5/2016 AD.
 */
class RxBleScannerLSpec extends Specification {

    def bleScanner
    def scannerImpl
    def subscriber
    ScanCallback scanCallback

    def setup() {
        def adapter = Mock(BluetoothAdapter.class)
        bleScanner = Mock(BluetoothLeScanner.class)
        adapter.getBluetoothLeScanner() >> bleScanner
        scannerImpl = new RxBleScannerL(adapter)
    }

    def "when the scanner receives ScanResult successfully"() {
        setup:
        bleScanner.startScan(_) >> { args -> scanCallback = args[0] }
        subscriber = new TestSubscriber()
        scannerImpl.startScan().subscribe(subscriber)

        when:
        def result1 = Mock(ScanResult.class)
        scanCallback.onScanResult(ScanSettings.CALLBACK_TYPE_FIRST_MATCH, result1)

        then:
        subscriber.assertValue(result1)
        subscriber.assertNoTerminalEvent()

        when:
        def result2 = Mock(ScanResult.class)
        scanCallback.onScanResult(ScanSettings.CALLBACK_TYPE_ALL_MATCHES, result2)

        then:
        subscriber.assertValues(result1, result2)
        subscriber.assertNoTerminalEvent()

        when:
        scannerImpl.stopScan()

        then:
        subscriber.assertCompleted()

        then:
        1 * bleScanner.stopScan(scanCallback)
    }

    def "when the scanner scans failure"() {
        setup:
        bleScanner.startScan(_) >> { args -> scanCallback = args[0] }
        subscriber = new TestSubscriber()
        scannerImpl.startScan().subscribe(subscriber)

        when:
        scanCallback.onScanFailed(ScanCallback.SCAN_FAILED_ALREADY_STARTED)

        then:
        subscriber.assertError(RxBleScannerException.class)
        subscriber.onErrorEvents.get(0).getMessage() == RxBleScannerException.ErrorType.ALREADY_STARTED.name()

        then:
        1 * bleScanner.stopScan(scanCallback)
    }
}
