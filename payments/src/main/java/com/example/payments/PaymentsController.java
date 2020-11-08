package com.example.payments;

import com.example.util.RsaVerifier;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentsController {
  private final RestTemplate restTemplate;

  public PaymentsController() {
    this.restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }

  @GetMapping("/")
  public String processRefunds() {

    var warehousePublicKey =
        restTemplate.getForObject("http://localhost:8082/publicKey", String.class);
    var rsaVerifier = new RsaVerifier(warehousePublicKey);

    String refundsJws = restTemplate.getForObject("http://localhost:8082/refunds", String.class);
    String refundsJson = rsaVerifier.verify(refundsJws).orElseThrow();

    System.out.println("Refunds JWS : " + refundsJws);
    System.out.println("Warehouse Public Key: " + rsaVerifier.getKeyJson());
    System.out.println("Verified Refunds ...");
    System.out.println(refundsJson);
    return refundsJson;
  }
}
