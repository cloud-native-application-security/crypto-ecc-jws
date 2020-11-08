package com.example.warehouse;

import com.example.util.RsaSigner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RefundController {

  private final RefundService refundService;
  private final RsaSigner rsaSigner;

  RefundController(RefundService refundService) {
    this.refundService = refundService;
    this.rsaSigner = new RsaSigner();
  }

  @GetMapping("/publicKey")
  String pubicKey() {
    return rsaSigner.getPublicKey();
  }

  @GetMapping("/refunds")
  String generateReport() {
    return rsaSigner.sign(refundService.generateReport());
  }
}
