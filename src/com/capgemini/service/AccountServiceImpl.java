package com.capgemini.service;

import com.capgemini.exceptions.InsufficientAccountNumberException;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.beans.Account;
import com.capgemini.repos.AccountRepo;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepo accountRepository;
	
	
	public AccountServiceImpl(AccountRepo accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}


	@Override
	public Account createAccount(int accountNumber,int amount) throws InsufficientInitialAmountException
	{
		int a,b,c;
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		
		account.setAmount(amount);
		 
		if(accountRepository.save(account))
		{
			return account;
		}
	     
		return null;
		
	}
	
	@Override
	public Account depositAmount(int accountNumber,int amount) throws InsufficientAccountNumberException{
		
		
		Account account = accountRepository.searchAccount(accountNumber);
		if(account==null) {
			throw new InsufficientAccountNumberException();
		}
		account.setAmount(account.getAmount()+amount);
		return account;
		
	}


	@Override
	public Account withdrawAmount(int accountNumber, int amount)
			throws InsufficientAccountNumberException, InsufficientBalanceException {
		// TODO Auto-generated method stub
		
		Account account = accountRepository.searchAccount(accountNumber);
		if(account==null) {
			throw new InsufficientAccountNumberException();
		}else if(account.getAmount()<amount) {
			throw new InsufficientBalanceException();
		}
		account.setAmount(account.getAmount()-amount);
		return account;
	}
	
	
	

}