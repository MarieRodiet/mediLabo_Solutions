package com.mariemoore.gateway.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * Utility class for generating a secure random secret key for cryptographic operations.
 * This class specifically generates a key for HMAC-SHA512 signing, encodes it in BASE64 format,
 * and prints it to the console.
 */
public class KeyGenerator {

    /**
     * Main method which acts as the entry point for the KeyGenerator utility.
     * It generates a BASE64-encoded secret key for HMAC-SHA512 algorithm and prints it.
     *
     * @param args Command line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encodedKey = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Generated Key: " + encodedKey);
    }
}
