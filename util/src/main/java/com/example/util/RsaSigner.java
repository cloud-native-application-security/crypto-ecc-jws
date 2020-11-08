package com.example.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

public class RsaSigner {

  private final RSAKey key;

  public RsaSigner() {
    try {
      this.key = new RSAKeyGenerator(4096).generate();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPublicKey() {
    return key.toPublicJWK().toJSONString();
  }

  public String sign(String data) {
    try {
      JWSHeader header = new JWSHeader(JWSAlgorithm.PS512);
      Payload payload = new Payload(data);
      JWSObject jwsObject = new JWSObject(header, payload);
      jwsObject.sign(new RSASSASigner(key));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
