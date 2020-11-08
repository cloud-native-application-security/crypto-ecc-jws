package com.example.warehouse;

import com.example.util.JsonUtils;
import com.example.util.RsaVerifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WarehouseApplicationTests {

  @LocalServerPort private int port;

  private RestTemplate restTemplate = new RestTemplate();

  @Test
  void testReportGeneration() {

    var serverUrl = "http://localhost:" + port;
    var publicKeyJwk = restTemplate.getForObject(serverUrl + "/publicKey", String.class);
    var rsaCipher = new RsaVerifier(publicKeyJwk);

    var url = "http://localhost:" + port + "/refunds";
    var responseJws = restTemplate.getForObject(serverUrl + "/refunds", String.class);
    var refundsJson = rsaCipher.verify(responseJws);

    Refund[] refunds = JsonUtils.fromJson(refundsJson.get(), Refund[].class);
    Assertions.assertThat(refunds).hasSize(2);
  }
}
