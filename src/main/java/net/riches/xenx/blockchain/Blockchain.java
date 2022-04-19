package net.riches.xenx.blockchain;

import net.riches.xenx.Address;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import java.util.List;

public class Blockchain {
    private List<Block> chain;
    private ArrayList<Transaction> pendingTransactions;
    private int difficulty;
    private int minerRewards;
    private int blockSize;

    public Blockchain() {
        this.chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        this.blockSize = 1;
    }

    public boolean addTransaction(Address sender, Address receiver, int amount, PublicKey key, KeyPair senderKey) throws Exception {
        Transaction transaction = new Transaction(sender, receiver, amount);

        if (!transaction.signTransaction(key, senderKey)) {
            System.err.println("Signature Failed");
            return false;
        }
        pendingTransactions.add(transaction);
        System.out.println("Successfully added transaction!");
        return true;
    }

    public String generateKeys() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair key = keyPairGen.generateKeyPair();

        try (FileOutputStream outPrivate = new FileOutputStream("private.pem")) {
            outPrivate.write(key.getPrivate().getEncoded());
        }

        try (FileOutputStream outPublic = new FileOutputStream("receiver.pem")) {
            outPublic.write(key.getPublic().getEncoded());
        }

        return key.getPublic().toString();
    }

    public boolean minePendingTransactions() {
        int length = this.pendingTransactions.size();

        if (length < 1) {
            // TODO ERROR
            return false;
        }

        for (int i = 0; i < length; i += this.blockSize) {
            int end = i + this.blockSize;
            if (i >= length)
                end = length;

            List<Transaction> transactionSlice = this.pendingTransactions.subList(i, end);

            Block newBLock = new Block(LocalDateTime.now(), chain.size(), transactionSlice);
            String hash = (chain.size() == 0) ? "" : getLastBlock().getHash();
            newBLock.setPrev(hash);
            newBLock.mineBlock(3);
            chain.add(newBLock);
        }
        return true;
    }

    public void addPendingTransaction(Transaction transaction) {
        this.pendingTransactions.add(transaction);
    }

    public Block getLastBlock() {
        return chain.get(chain.size()-1);
    }

    public void addBlock(Block block) {
        if (chain.size() > 0)
            block.setPrev(getLastBlock().getPrev());

        chain.add(block);
    }
}
