package info.izumin.android.rxblescanner;

import android.bluetooth.le.ScanCallback;

/**
 * Created by izumin on 1/3/2016 AD.
 */
public class RxBleScannerException extends RuntimeException {

    private final ErrorType errorType;

    public RxBleScannerException(int errorCode) {
        errorType = ErrorType.valueOf(errorCode);
    }

    @Override
    public String getMessage() {
        return errorType.name();
    }

    public enum ErrorType {
        ALREADY_STARTED                 (ScanCallback.SCAN_FAILED_ALREADY_STARTED),
        APPLICATION_REGISTRATION_FAILED (ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED),
        FEATURE_UNSUPPORTED             (ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED),
        INTERNAL_ERROR                  (ScanCallback.SCAN_FAILED_INTERNAL_ERROR),
        UNKNOWN_ERROR                   (-1);

        private final int code;

        ErrorType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static ErrorType valueOf(int code) {
            for (ErrorType errorType : values()) {
                if (errorType.getCode() == code) { return errorType; }
            }
            return UNKNOWN_ERROR;
        }
    }
}
