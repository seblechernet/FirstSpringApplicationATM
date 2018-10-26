package me.seb.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Accountservice {
    @Autowired
    AccountRepository accountRepository;

public void withdraw(Account  account){

    double balance=(account.getBalance()-account.getAmount());
    account.setBalance(balance);

}

}
