package com.example.demo.Service;

import com.example.demo.Dto.Request.EtkinlikGuncelleDto;
import com.example.demo.Dto.Request.SinemaEkleDto;
import com.example.demo.Dto.Request.SinemaGuncelleDto;
import com.example.demo.Dto.Response.EtkinlikForOrgDetayDto;
import com.example.demo.Dto.Response.SinemaForOrgDetayDto;
import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Entity.SinemaEntity;
import com.example.demo.Repository.SinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SinemaService {
    private final OrganizatorLandingService organizatorLandingService;
    private final SinemaRepository sinemaRepository;

    @Autowired
    public SinemaService(OrganizatorLandingService organizatorLandingService, SinemaRepository sinemaRepository)
    {
        this.organizatorLandingService =organizatorLandingService;
        this.sinemaRepository = sinemaRepository;
    }

    public boolean sinemaEkle(Long orgId, SinemaEkleDto sinemaEkleDto)
    {
        EtkinlikEntity etkinlik = organizatorLandingService.etkinlikEkle(sinemaEkleDto.getEtkinlikEkleDto(),orgId);
        sinemaRepository.save(new SinemaEntity(etkinlik,sinemaEkleDto.getFragmanLinki()));
        return true;
    }

    //notlarda yapmak istediğin orgid ile silme yapmayı unutma şimdilik test için bu şekilde
    public boolean sinemaSil(Long orgId,Long sinemaId){
        SinemaEntity sinemaEntity = sinemaRepository.findBySinemaID(sinemaId);
        sinemaRepository.delete(sinemaEntity);
        organizatorLandingService.etkinlikSil(sinemaEntity.getEtkinlik().getEtkinlikID());
        return true;
    }

    public SinemaGuncelleDto getSinemaGuncelleDto(Long orgId, Long sinemaId)
    {
        SinemaEntity sinema = sinemaRepository.findBySinemaID(sinemaId);
        EtkinlikGuncelleDto etkinlikGuncelleDto = organizatorLandingService.getEtkinlikGuncelleDto(orgId,sinema.getEtkinlik().getEtkinlikID());
        return new SinemaGuncelleDto(sinema.getSinemaID(),etkinlikGuncelleDto,sinema.getFragmanLinki());
    }

    public boolean sinemaGuncelle(Long orgId,SinemaGuncelleDto sinemaGuncelleDto)
    {
        EtkinlikEntity etkinlik = organizatorLandingService.etkinlikGuncelle(orgId,sinemaGuncelleDto.getEtkinlikGuncelleDto());
        SinemaEntity sinema = sinemaRepository.findBySinemaID(sinemaGuncelleDto.getSinemaId());

        sinema.setEtkinlik(etkinlik);
        sinema.setFragmanLinki(sinemaGuncelleDto.getFragmanLinki());

        sinemaRepository.save(sinema);

        return true;
    }

    public SinemaForOrgDetayDto getSinema(Long orgId, Long sinemaId)
    {
        SinemaEntity sinema = sinemaRepository.findBySinemaID(sinemaId);
        EtkinlikForOrgDetayDto etkinlikForOrgDetayDto = organizatorLandingService.getEtkinlik(sinema.getEtkinlik().getEtkinlikID());

        return new SinemaForOrgDetayDto(sinema.getSinemaID(),etkinlikForOrgDetayDto, sinema.getFragmanLinki(),sinema.getImdbPuani());
    }
}
