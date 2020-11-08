package com.example.warehouse;

import com.example.util.EllipticCurveSigner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RefundController {

  private final RefundService refundService;
  private final EllipticCurveSigner rsaSigner;

  RefundController(RefundService refundService) {
    this.refundService = refundService;
    this.rsaSigner = new EllipticCurveSigner();
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
