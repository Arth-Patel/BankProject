package com.capgemini.service;

import com.capgemini.exceptions.InsufficientAccountNumberException;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.beans.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialAmountException;

	Account depositAmount(int accountNumber,int amount) throws InsufficientAccountNumberException;
	
	Account withdrawAmount(int accountNumber, int amount) throws InsufficientAccountNumberException,InsufficientBalanceException;
	
}