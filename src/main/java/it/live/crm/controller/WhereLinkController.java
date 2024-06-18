package it.live.crm.controller;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.WhereLinkAddDTO;
import it.live.crm.payload.WhereLinkGetDTO;
import it.live.crm.service.WhereLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/where-link")
@RequiredArgsConstructor
public class WhereLinkController {
    private final WhereLinkService whereLinkService;

    @GetMapping("/get-all")
    public Map<WhereLinkGetDTO, Long> getLinks() {
        return whereLinkService.getLinks();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody WhereLinkAddDTO whereLink) {
        return whereLinkService.add(whereLink);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> delete(@RequestParam Long linkId) {
        return whereLinkService.delete(linkId);
    }

}
