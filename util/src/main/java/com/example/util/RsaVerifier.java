package com.example.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import java.text.ParseException;
import java.util.Optional;

public class RsaVerifier {

  private final RSAKey key;

  public RsaVerifier(String jwk) {
    try {
      this.key = JWK.parse(jwk).toRSAKey();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public String getKeyJson() {
    return key.toJSONString();
  }

  public Optional<String> verify(String jws) {
    try {
      JWSObject jwsObject = JWSObject.parse(jws);
      if (jwsObject.verify(new RSASSAVerifier(key))) {
        return Optional.of(jwsObject.getPayload().toString());
      } else {
        return Optional.empty();
      }
    } catch (ParseException | JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
