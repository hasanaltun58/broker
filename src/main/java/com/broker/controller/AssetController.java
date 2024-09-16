package com.broker.controller;

import com.broker.Service.AssetService;
import com.broker.entity.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    public ResponseEntity<List<Asset>> listAssets(@RequestParam Long customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }
}
