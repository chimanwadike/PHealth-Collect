package org.webworks.datatool.Model;

public class FingerPrint {
    private String fpClientIdentifier, fingerPosition, fingerPrintCapture;

    public String getFingerPosition() { return fingerPosition; }

    public void setFingerPosition(String fingerPosition) { this.fingerPosition = fingerPosition; }

    public String getFingerPrintCapture() { return fingerPrintCapture; }

    public void setFingerPrintCapture(String fingerPrintCapture) { this.fingerPrintCapture = fingerPrintCapture; }

    public String getFpClientIdentifier() { return fpClientIdentifier; }

    public void setFpClientIdentifier(String fpClientIdentifier) { this.fpClientIdentifier = fpClientIdentifier; }
}
