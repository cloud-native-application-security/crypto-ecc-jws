package com.example.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;

public class EllipticCurveSigner {

  private final OctetKeyPair key;

  public EllipticCurveSigner() {
    try {
      this.key = new OctetKeyPairGenerator(Curve.Ed25519).generate();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPublicKey() {
    return key.toPublicJWK().toJSONString();
  }

  public String sign(String data) {
    try {
      JWSHeader header = new JWSHeader(JWSAlgorithm.EdDSA);
      Payload payload = new Payload(data);
      JWSObject jwsObject = new JWSObject(header, payload);
      jwsObject.sign(new Ed25519Signer(key));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
