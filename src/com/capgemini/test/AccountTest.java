package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientAccountNumberException;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.beans.Account;
import com.capgemini.repos.AccountRepo;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;



public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepo accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	
	
	@Test
	public void whenTheValidInfoIsPassedAccountMoneyShouldBeDepositedSuccessfully() throws InsufficientInitialAmountException, InsufficientAccountNumberException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(1000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.depositAmount(101, 1000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 2000));
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientAccountNumberException.class)
	public void whenTheAccountNumberIsIncorrectonDeposit() throws InsufficientInitialAmountException, InsufficientAccountNumberException
	{
		accountService.createAccount(101, 1000);
		accountService.depositAmount(102, 1000);
		
		
	}
	
	
	
	@Test(expected=com.capgemini.exceptions.InsufficientAccountNumberException.class)
	public void whenTheAccountNumberIsIncorrectonWithdraw() throws InsufficientInitialAmountException, InsufficientAccountNumberException, InsufficientBalanceException
	{
		accountService.createAccount(101, 1000);
		accountService.withdrawAmount(102, 1000);
		
		
	}
	
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenThereIsNotEnoughMoneyToWithdraw() throws InsufficientInitialAmountException, InsufficientAccountNumberException, InsufficientBalanceException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setAccountNumber(101);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.withdrawAmount(101, 1500);
		
		
	}
	
	@Test
	public void whenTheAccountNumberIsCorrectonWithdraw() throws InsufficientInitialAmountException, InsufficientAccountNumberException, InsufficientBalanceException
	{
		Account account = new Account();
		account.setAmount(11000);
		account.setAccountNumber(101);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.withdrawAmount(101, 1000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 10000));
	}
	
	
	
	
	

}