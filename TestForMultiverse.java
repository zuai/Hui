package com.multiverse.test;


import static org.multiverse.api.StmUtils.newTxnLong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnLong;

class Account{
    final TxnLong amount;

    public Account(long amount){
       this.amount = newTxnLong(amount);
    }

    public static void transfer(final Account from, final Account to, final long amount){
        StmUtils.atomic(new Runnable(){
            public void run(){
                from.amount.decrement(amount);
                to.amount.increment(amount);
            }
        });
    }
}

public class TestForMultiverse {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub		
		final Account account1 = new Account(100);
		final Account account2 = new Account(100);
		ExecutorService executor = Executors.newCachedThreadPool();
		for( int i = 0; i <2; ++i)
			executor.execute(new Runnable(){
				public void run(){
					Account.transfer(account1, account2, 1);
				}
				
			});
		executor.shutdown();
		executor.awaitTermination(1L, TimeUnit.HOURS);
		System.out.println();
		StmUtils.atomic(new Runnable(){
			public void run(){
				System.out.println(account1.amount.get());
				System.out.println(account2.amount.get());
			}
		});
	}

}

