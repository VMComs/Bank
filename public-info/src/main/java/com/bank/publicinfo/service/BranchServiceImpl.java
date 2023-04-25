package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.BranchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public Branch findById(Long id) {
        log.debug("Вызов метода findById() |id = " + id + "| в сервисе " + this.getClass());
        return branchRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found: " + this.getClass().getSimpleName() + ", findById(), id = " + id));
    }

    @Override
    public List<Branch> findAll() {
        log.debug("Вызов метода findAll() в сервисе " + this.getClass());
        return branchRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Вызов метода deleteById() |id = " + id + "| в сервисе " + this.getClass());
        branchRepository.delete(findById(id));
    }

    @Override
    @Transactional
    public void save(Branch branch) throws NotExecutedException {
        log.debug("Вызов метода save() |Entity = " + branch + "| в сервисе " + this.getClass());
        if (branchRepository.existsById(branch.getId())) {
            throw new NotExecutedException("Not executed: " + this.getClass().getSimpleName() +
                    ", object with id = " + branch.getId() + " already exists");
        } else {
            branchRepository.save(branch);
        }
    }

}
