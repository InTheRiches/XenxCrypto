package net.riches.xenx.blockchain;

import net.riches.xenx.Address;
import net.riches.xenx.Xenx;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private final Address sender;
    private final Address receiver;

    private boolean signature;

    private final double amt;
    private final LocalDateTime time = LocalDateTime.now();
    private final String hash;

    public Transaction(Address sender, Address receiver, double amt) {
        this.sender = sender;
        this.receiver = receiver;
        this.amt = amt;
        this.hash = calculateHash();
        this.signature = false;
    }

    public boolean signTransaction(PublicKey key, KeyPair senderKey) {
        if (!Objects.equals(this.hash, calculateHash())) {
            System.err.println("Translation tampered error.");
            return false;
        }
        if (!key.toString().equals(senderKey.getPublic().toString())) {
            System.err.println("Transaction attempted to be signed by another wallet.");
            return false;
        }

        this.signature = true;
        System.out.println("Made signature!");
        return true;
    }

    public String getHash() {
        return hash;
    }

    private String calculateHash() {
        String hashString = sender.toString() + receiver.toString() + amt + time.toString();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            return Xenx.bytesToHex(digest.digest(
                    hashString.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
