package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.AtmRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;

    @Override
    public Atm findById(Long id) {
        log.debug("Вызов метода findById() |id = " + id + "| в сервисе " + this.getClass());
        return atmRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found: " + this.getClass().getSimpleName() + ", findById(), id = " + id));
    }

    @Override
    public Atm findByBranch(Branch branch) {
        log.debug("Вызов метода findByBranch() |branch = " + branch + "| в сервисе " + this.getClass());
        return atmRepository.findByBranch(branch).orElseThrow(
                () -> new NotFoundException("Not found: " + this.getClass().getSimpleName() + ", findByBranch(), branch = " + branch));
    }

    @Override
    public List<Atm> findAll() {
        log.debug("Вызов метода findAll() в сервисе " + this.getClass());
        return atmRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Вызов метода deleteById() |id = " + id + "| в сервисе " + this.getClass());
        atmRepository.delete(findById(id));
    }

    @Override
    @Transactional
    public void save(Atm atm) throws NotExecutedException {
        log.debug("Вызов метода save() |Entity = " + atm + "| в сервисе " + this.getClass());
        if (atmRepository.existsById(atm.getId())) {
            throw new NotExecutedException("Not executed: " + this.getClass().getSimpleName() +
                    ", object with id = " + atm.getId() + " already exists");
        } else {
            atmRepository.save(atm);
        }
    }
}
