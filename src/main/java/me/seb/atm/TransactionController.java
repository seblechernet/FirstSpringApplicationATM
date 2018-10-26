package me.seb.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
public class TransactionController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    Accountservice accountservice;

    @RequestMapping("/")
    public String homePage(Model model){
        model.addAttribute("accounts", accountRepository.findAll());

        return "list";
    }
    @GetMapping("/add")
    public String addAccount(Model model){

        model.addAttribute("account",new Account());
        return "addform";
     }
     @PostMapping("/process")
     public String process(@Valid Account account, BindingResult result){
        if(result.hasErrors()){
            return "addform";
        }
        accountRepository.save(account);
        return "redirect:/";
     }
    @RequestMapping("/displaytransaction/{id}")
    public String showTransaction(@PathVariable("id") long id, Model model){

        model.addAttribute("account", accountRepository.findById(id).get());

        return "historyform";
    }

    @GetMapping("/withdraw/{id}")
    public String withdrawal(@PathVariable("id") long id,Model model){
        model.addAttribute("account", accountRepository.findById(id));
        return "withdrawalform";
    }
    @PostMapping("/withdrawprocess")
    public String processwithdraw(Account account, double amount, Model model){

        model.addAttribute("amount",amount);
        account.setAmount(amount);
        double balance=(account.getBalance()-account.getAmount());
        account.setBalance(balance);
           return "redirect:/";
    }

    @GetMapping("/deposite/{id}")
    public String deposite(@PathVariable("id") long id,Model model){
    model.addAttribute("account", accountRepository.findById(id));
        return "depositeform";
    }
    @PostMapping("/depositeprocess")
    public String processdeposite(Account account,double amount,Model model){

        model.addAttribute("amount",amount);
        account.setAmount(amount);
        double balance=(account.getBalance()+account.getAmount());
        account.setBalance(balance);
        return "redirect:/";
    }
    }



