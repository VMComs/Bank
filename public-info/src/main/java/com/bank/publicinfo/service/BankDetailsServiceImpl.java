package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.BankDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;

    @Override
    public BankDetails findById(Long id) {
        log.debug("Вызов метода findById() |id = " + id + "| в сервисе " + this.getClass());
        return bankDetailsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found: " + this.getClass().getSimpleName() + ", findById(), id = " + id));
    }

    @Override
    public List<BankDetails> findAll() {
        log.debug("Вызов метода findAll() в сервисе " + this.getClass());
        return bankDetailsRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Вызов метода deleteById() |id = " + id + "| в сервисе " + this.getClass());
        bankDetailsRepository.delete(findById(id));
    }

    @Override
    @Transactional
    public void save(BankDetails bankDetails) throws NotExecutedException {
        log.debug("Вызов метода save() |Entity = " + bankDetails + "| в сервисе " + this.getClass());
        if (bankDetailsRepository.existsById(bankDetails.getId())) {
            throw new NotExecutedException("Not executed: " + this.getClass().getSimpleName() +
                    ", object with id = " + bankDetails.getId() + " already exists");
        } else {
            bankDetailsRepository.save(bankDetails);
        }
    }
}
