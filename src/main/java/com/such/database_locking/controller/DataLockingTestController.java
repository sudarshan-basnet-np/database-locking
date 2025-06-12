package com.such.database_locking.controller;

import com.such.database_locking.services.impl.OptimisticDatabaseLockingTest;
import com.such.database_locking.services.impl.PessimisticDatabaseLockingTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class DataLockingTestController {

    private final OptimisticDatabaseLockingTest optimisticDatabaseLockingTest;
    private final PessimisticDatabaseLockingTest pessimisticDatabaseLockingTest;

    public DataLockingTestController(OptimisticDatabaseLockingTest optimisticDatabaseLockingTest, PessimisticDatabaseLockingTest pessimisticDatabaseLockingTest) {
        this.optimisticDatabaseLockingTest = optimisticDatabaseLockingTest;
        this.pessimisticDatabaseLockingTest = pessimisticDatabaseLockingTest;
    }

    @GetMapping("/optimistic/{id}")
    //Transaction is required...........
    public void getOptimisticLocking(@PathVariable Long id) {
        System.out.println(Thread.currentThread().getName() + " starting............");
        optimisticDatabaseLockingTest.testOptimisticDatabase(id);
    }

    @GetMapping("/pessimistic/{id}")
    //Transaction is required..............................
    public void getPessimisticLocking(@PathVariable Long id) {
        System.out.println(Thread.currentThread().getName() + " starting............");
        pessimisticDatabaseLockingTest.testPessimisticLocking(id);
        System.out.println(Thread.currentThread().getName() + " end............");
    }


}
