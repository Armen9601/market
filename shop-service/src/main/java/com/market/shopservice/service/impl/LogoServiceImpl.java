package com.market.shopservice.service.impl;

import com.market.shopservice.dto.CompanyDto;
import com.market.shopservice.dto.LogoDto;
import com.market.shopservice.entity.Logo;
import com.market.shopservice.mapper.LogoMapper;
import com.market.shopservice.repository.LogoRepository;
import com.market.shopservice.service.CompanyService;
import com.market.shopservice.service.LogoService;
import com.market.shopservice.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LogoServiceImpl implements LogoService {

    private final LogoRepository logoRepository;
    private final LogoMapper logoMapper;
    private final CompanyService companyService;
    private final FileUtil fileUtil;

    @Transactional
    @Override
    public void saveLogo(LogoDto logoDto, MultipartFile file) {
        CompanyDto byId = companyService.findById(logoDto.getCompanyId());
        String fileUpload = fileUtil.fileUpload(file);
        logoDto.setFilePath(fileUpload);
        Logo save = logoRepository.save(logoMapper.toEntity(logoDto));
        byId.setLogo(logoMapper.toDto(save));
        companyService.update(byId);
    }


    @Override
    public void deleteLogo(Long id) {
        logoRepository.findById(id).ifPresent(logoRepository::delete);
    }

}
