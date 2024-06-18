package it.live.crm.service;

import it.live.crm.entity.WhereLink;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.WhereLinkAddDTO;
import it.live.crm.payload.WhereLinkGetDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface WhereLinkService {
    Map<WhereLinkGetDTO, Long> getLinks();

    ResponseEntity<ApiResponse> add(WhereLinkAddDTO whereLink);

    ResponseEntity<ApiResponse> delete(Long linkId);
}
