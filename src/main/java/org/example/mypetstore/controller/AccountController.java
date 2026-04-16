package org.example.mypetstore.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.example.mypetstore.mapper.AccountMapper;
import org.example.mypetstore.model.Account;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountMapper accountMapper;

    public AccountController(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public List<Account> list() {
        return accountMapper.findAll();
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id) {
        return accountMapper.findById(id);
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody Account account) {
        accountMapper.insert(account);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "用户创建成功");
        result.put("data", account);
        return result;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        accountMapper.update(account);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "用户更新成功");
        return result;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        accountMapper.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "用户删除成功");
        return result;
    }

    @PostMapping("/{id}/reset-password")
    public Map<String, Object> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newPassword = request.getOrDefault("newPassword", "123456");
        accountMapper.resetPassword(id, newPassword);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "密码重置成功");
        result.put("newPassword", newPassword);
        return result;
    }

    @GetMapping("/search")
    public List<Account> searchByUsername(@RequestParam String username) {
        return accountMapper.findByUsername(username);
    }
}