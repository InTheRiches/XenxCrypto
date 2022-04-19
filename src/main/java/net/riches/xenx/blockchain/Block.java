package net.riches.xenx.blockchain;

import net.riches.xenx.Xenx;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

public class Block {
    private String prev;
    private String hash;
    private final LocalDateTime time;
    private final long index;
    private long nonse;
    private final List<Transaction> transactions;


    public Block(LocalDateTime time, long index, List<Transaction> transactions) {
        this.prev = "";
        this.time = time;
        this.index = index;
        this.nonse = 0;
        this.transactions = transactions;

        this.hash = calculateHash();
    }

    public void mineBlock(int difficulty) {
        StringBuilder hashPuzzle = new StringBuilder();
        for (int i = 0; i < difficulty; i++)
            hashPuzzle.append(i);

        while (!this.hash.substring(0, difficulty).equals(hashPuzzle.toString())) {
            this.nonse += 1;
            this.hash = calculateHash();
            System.out.println("Hash: " + hash);
            System.out.println("Hash we want: " + hashPuzzle);
            System.out.println("Attempt: " + nonse);
            System.out.println("");
        }
        System.out.println("Success! Nonse: " + nonse);
    }

    private String calculateHash() {
        StringBuilder hashTransactions = new StringBuilder();
        for (Transaction transaction : transactions)
            hashTransactions.append(transaction.getHash());

        String hashString = time.toString() + hashTransactions + prev + index + nonse;
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

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getPrev() {
        return prev;
    }

    public String getHash() {
        return hash;
    }

    public long getIndex() {
        return index;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
