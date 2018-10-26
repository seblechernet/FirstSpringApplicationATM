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

    @RequestMapping("/withdraw/{id}")
    public String withdrawal(@PathVariable("id") long id,Model model){
        model.addAttribute("account", accountRepository.findById(id));

        return "withdrawalform";
    }
    @GetMapping("/withdrawprocess/{id}")
    public String processwithdraw(@PathVariable("id") long id,Model model){
        Account account=accountRepository.findById(id).get();
        double currentBalance=account.getBalance()-account.getAmount();
        account.setBalance(currentBalance);
                 return "redirect:/";
    }

    @RequestMapping("/deposite/{id}")
    public String deposite(@PathVariable("id") long id,Model model){
    model.addAttribute("account", accountRepository.findById(id));
        return "depositeform";
    }
    @GetMapping("/depositeprocess/{id}")
    public String processdeposite(@PathVariable("id") long id,Model model){
        Account account=accountRepository.findById(id).get();
        double currentBalance=account.getBalance()+ account.getAmount();
        account.setBalance(currentBalance);
        return "redirect:/";
    }


}
