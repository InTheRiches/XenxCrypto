package net.riches.xenx;

import java.util.Base64;

public class Address {

    private final String address;
    private double balance;

    public Address() {
        this.address = "";
        this.balance = 0.0;
    }

    public Address(String address, double balance) {
        this.address = address;
        this.balance = balance;
    }

    public static Address fromEncodedBase64(String encodedAddress, String encodedBalance) {
        Base64.Decoder decoder = Base64.getDecoder();
        // Decoding string
        return new Address(new String(decoder.decode(encodedAddress)), Integer.parseInt(new String(decoder.decode(encodedBalance))));
    }

    public String toString() {
        return address;
    }

    public double getBalance() {
        return balance;
    }
}
