package net.riches.xenx;

import net.riches.web.server.HttpServer;
import net.riches.web.server.impl.BasicHttpServer;
import net.riches.xenx.blockchain.Blockchain;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Xenx {

    private static HttpServer server;

    public static void main(String[] args) throws Exception {
        server = new BasicHttpServer(8080);
        server.start();

        Blockchain chain = new Blockchain();

        String key = chain.generateKeys();

        PublicKey pKey = getPemPublicKey("receiver.pem", "RSA");
        KeyPair keyPair = new KeyPair(pKey, getPemPrivateKey("private.pem", "RSA"));

        chain.addTransaction(new Address(), new Address(), 10, pKey, keyPair);

    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public static PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
        File privateKeyFile = new File(filename);
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

        KeyFactory privateKeyFactory = KeyFactory.getInstance(algorithm);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return privateKeyFactory.generatePrivate(privateKeySpec);
    }

    public static PublicKey getPemPublicKey(String filename, String algorithm) throws Exception {
        File publicKeyFile = new File(filename);
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory publicKeyFactory = KeyFactory.getInstance(algorithm);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return publicKeyFactory.generatePublic(publicKeySpec);
    }
}
