package it.live.crm.service.impl;

import it.live.crm.entity.WhereLink;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.WhereLinkAddDTO;
import it.live.crm.payload.WhereLinkGetDTO;
import it.live.crm.repository.StudentLeadRepository;
import it.live.crm.repository.WhereLinkRepository;
import it.live.crm.service.WhereLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class WhereLinkServiceImpl implements WhereLinkService {
    private final WhereLinkRepository whereLinkRepository;
    private final StudentLeadRepository studentLeadRepository;

    @Override
    public Map<WhereLinkGetDTO, Long> getLinks() {
        Map<WhereLinkGetDTO, Long> asistMap = new HashMap<>();
        for (WhereLink whereLink : whereLinkRepository.findAll()) {
            asistMap.put(WhereLinkGetDTO.builder().id(whereLink.getId()).name(whereLink.getName()).link(whereLink.getLink()).build(), (long) studentLeadRepository.findAllByWhereLinkId(whereLink.getId()).size());
        }
        return asistMap;
    }

    @Override
    public ResponseEntity<ApiResponse> add(WhereLinkAddDTO whereLink) {
        whereLinkRepository.save(WhereLink.builder().link(whereLink.getLink()).name(whereLink.getName()).build());
        return ResponseEntity.ok(ApiResponse.builder().message("saved").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> delete(Long linkId) {
        whereLinkRepository.deleteById(linkId);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Deleted").build());
    }
}
