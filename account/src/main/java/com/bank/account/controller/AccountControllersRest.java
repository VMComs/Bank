package com.bank.account.controller;

import com.bank.account.feign.ProfileFeignClient;
import com.bank.account.feign.PublicInfoFeignClient;
import com.bank.account.model.dto.AccountDTO;
import com.bank.account.model.entity.Account;
import com.bank.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс Контроллеров, взаимодейтсвующих с пользователем по API.
 */

@RestController("/account")
public class AccountControllersRest {
    private final AccountService accountService;

    ProfileFeignClient profileFeignClient;
    PublicInfoFeignClient publicInfoFeignClient;


    public AccountControllersRest(AccountService accountService) {
        this.accountService = accountService;

    }

    @GetMapping("/accounts")
    public ResponseEntity <List<Account>> getAll() {
        List <Account> accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Account> getById(@PathVariable Long id){
        return new ResponseEntity <> (accountService.getByID(id), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity <Profile> getProfileById (@PathVariable Long id) {
//        Profile profile = profileFeignClient.getById(id);
//        return new ResponseEntity<>(profile, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity <BankDetailsDto> getBankById (@PathVariable Long id) {
//        BankDetailsDto bankDetailsDto = publicInfoFeignClient.getById(id);
//        return new ResponseEntity<>(bankDetailsDto, HttpStatus.OK);
//    }

    @PostMapping("/new")
    public ResponseEntity <Account> create (@RequestBody AccountDTO accountDTO) {
        // добавить проверку, что profileId и passportId существуют

        accountService.save(accountDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/editAccount/{id}")
    public ResponseEntity <Account> edit (@RequestBody AccountDTO accountDTO, @PathVariable Long id) {
        accountService.update(accountDTO, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("deleteAccount/{id}")
    public ResponseEntity <Account> delete (@PathVariable Long id){
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
