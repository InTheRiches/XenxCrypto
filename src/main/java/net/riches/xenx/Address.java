package net.riches.xenx;

public class Address {

    private final String address;
    private double balance;

    public Address() {
        this.address = "";
        this.balance = 0.0;
    }

    public String toString() {
        return address;
    }

    public double getBalance() {
        return balance;
    }
}
