package com.market.shopservice.service;

import com.market.shopservice.dto.LogoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LogoService {

    void saveLogo(LogoDto logoDto, MultipartFile file);

    void deleteLogo(Long id);

}
