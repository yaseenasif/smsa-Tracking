package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.EmailAddressForRoutesDto;
import com.example.CargoTracking.exception.RecordAlreadyExist;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.EmailAddressForRoutes;
import com.example.CargoTracking.repository.EmailAddressForRouteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class EmailAddressForRouteService {
  @Autowired
  private EmailAddressForRouteRepository emailAddressForRouteRepository;
  @Autowired
  ModelMapper modelMapper;

  public EmailAddressForRoutesDto addEmail(EmailAddressForRoutesDto emailAddressForRoutesDto) {
    EmailAddressForRoutes emailAddressForRoutes = emailAddressForRouteRepository.findByType(emailAddressForRoutesDto.getType().toLowerCase());
    if (emailAddressForRoutes == null) {
      emailAddressForRoutesDto.setType(emailAddressForRoutesDto.getType().toLowerCase());
      EmailAddressForRoutes save = emailAddressForRouteRepository.save(toEntity(emailAddressForRoutesDto));
      return toDto(save);
    }else {
      throw new RecordAlreadyExist("The record is already exist");
    }
  }

  public EmailAddressForRoutesDto updateEmail(Long id, EmailAddressForRoutesDto emailAddressForRoutesDto) {
    Optional<EmailAddressForRoutes> emailAddressForRoutes = emailAddressForRouteRepository.findById(id);
    if(emailAddressForRoutes.isPresent()){
      EmailAddressForRoutes unSave = emailAddressForRoutes.get();
      unSave.setEmails(emailAddressForRoutesDto.getEmails());
      EmailAddressForRoutes save = emailAddressForRouteRepository.save(unSave);
      return toDto(save);
    }else{
      throw new RecordNotFoundException("Record didn't exist");
    }
  }

  public EmailAddressForRoutesDto toDto(EmailAddressForRoutes emailAddressForRoutes) {
    return modelMapper.map(emailAddressForRoutes, EmailAddressForRoutesDto.class);
  }

  public EmailAddressForRoutes toEntity(EmailAddressForRoutesDto emailAddressForRoutesDto) {
    return modelMapper.map(emailAddressForRoutesDto, EmailAddressForRoutes.class);
  }

  public EmailAddressForRoutesDto getEmailByType(String type) {
    EmailAddressForRoutes byType = emailAddressForRouteRepository.findByType(type);
    if(byType == null){
      throw new RecordNotFoundException("No Record found");
    }
    return toDto(byType);
  }
}
