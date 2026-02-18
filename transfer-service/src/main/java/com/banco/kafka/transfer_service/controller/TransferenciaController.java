package com.banco.kafka.transfer_service.controller;

import com.banco.kafka.transfer_service.model.Transferencia;
import com.banco.kafka.transfer_service.model.TransferenciaRequestDTO;
import com.banco.kafka.transfer_service.service.TransferenciaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transferencias")
public class TransferenciaController {

    private final TransferenciaService service;

    public TransferenciaController(TransferenciaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transferencia> crearTransferencia(@RequestBody TransferenciaRequestDTO request) {
        Transferencia nuevaTransferencia = service.iniciarTransferencia(request);
        // Devolvemos un 202 Accepted, porque el proceso (Saga) aún no termina, solo se ha iniciado.
        return ResponseEntity.accepted().body(nuevaTransferencia);
    }
}
