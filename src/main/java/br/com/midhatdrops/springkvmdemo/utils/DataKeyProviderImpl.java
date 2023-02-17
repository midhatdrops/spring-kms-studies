package br.com.midhatdrops.springkvmdemo.utils;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

@Component
@Slf4j
public class DataKeyProviderImpl implements DataKeyProvider{

    @Autowired
    private AWSKMS awskmsClient;

    @Value("${aws.credential.key-id}")
    private String keyid;

    private final HashMap<String, String> context = new HashMap<>();

    @Override
    public String encrypt(String encrypt) {
        context.put("department","TI");
        EncryptRequest encryptRequest = new EncryptRequest();
        encryptRequest.withPlaintext(ByteBuffer.wrap(encrypt.getBytes(StandardCharsets.UTF_8))).withKeyId(keyid).withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);

        EncryptResult encryptResult = awskmsClient.encrypt(encryptRequest);

        ByteBuffer ciphertextBlob = encryptResult.getCiphertextBlob();
        if(encryptResult.getCiphertextBlob().hasArray()) {

            byte[] encode = Base64.getEncoder().encode(ciphertextBlob.array());
            return new String(encode,StandardCharsets.UTF_8);
        }
        return null;
    }

    @Override
    public String decrypt() {

        ByteBuffer encrypt = generateCipherText();
        DecryptRequest decryptRequest = new DecryptRequest();
        decryptRequest.withKeyId(keyid).withCiphertextBlob(encrypt).withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);


       return new String(awskmsClient.decrypt(decryptRequest).getPlaintext().array(),StandardCharsets.UTF_8);
    }

    private ByteBuffer generateCipherText() {
        EncryptRequest encryptRequest = new EncryptRequest();
        encryptRequest.withPlaintext(ByteBuffer.wrap("encrypt".getBytes(StandardCharsets.UTF_8))).withKeyId(keyid).withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);

        EncryptResult encryptResult = awskmsClient.encrypt(encryptRequest);

        return encryptResult.getCiphertextBlob();
    }
}
