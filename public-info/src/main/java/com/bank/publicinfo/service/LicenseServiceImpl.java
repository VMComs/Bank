package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.LicenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;

    @Override
    public License findById(Long id) {
        log.debug("Вызов метода findById() |id = " + id + "| в сервисе " + this.getClass());
        return licenseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found: " + this.getClass().getSimpleName() + ", findById(), id = " + id));
    }

    @Override
    public License findByBankDetails(BankDetails bankDetails) {
        log.debug("Вызов метода findByBankDetails() |Bank details = " + bankDetails + "| в сервисе " + this.getClass());
        return licenseRepository.findByBankDetails(bankDetails).orElseThrow(
                () -> new NotFoundException("Not found: " + this.getClass().getSimpleName() +
                        ", findByBankDetails(), bank details = " + bankDetails));
    }

    @Override
    public List<License> findAll() {
        log.debug("Вызов метода findAll() в сервисе " + this.getClass());
        return licenseRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Вызов метода deleteById() |id = " + id + "| в сервисе " + this.getClass());
        licenseRepository.delete(findById(id));
    }

    @Override
    @Transactional
    public void save(License license) throws NotExecutedException {
        log.debug("Вызов метода save() |Entity = " + license + "| в сервисе " + this.getClass());
        if (licenseRepository.existsById(license.getId())) {
            throw new NotExecutedException("Not executed: " + this.getClass().getSimpleName() +
                    ", object with id = " + license.getId() + " already exists");
        } else {
            licenseRepository.save(license);
        }
    }
}
