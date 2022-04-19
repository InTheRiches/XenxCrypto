package net.riches.xenx.blockchain;

import net.riches.xenx.Address;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain;
    private final ArrayList<Transaction> pendingTransactions;
    private final int difficulty;
    private int minerRewards;
    private final int blockSize;

    public Blockchain() {
        this.chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        this.blockSize = 1;
        this.difficulty = 6;
    }

    public boolean addTransaction(Address sender, Address receiver, int amount, PublicKey key, KeyPair senderKey) {
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
            newBLock.mineBlock(difficulty);
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
