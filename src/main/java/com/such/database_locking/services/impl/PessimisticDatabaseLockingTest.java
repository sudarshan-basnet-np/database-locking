package com.such.database_locking.services.impl;

import com.such.database_locking.model.Seat;
import com.such.database_locking.services.SeatService;
import org.springframework.stereotype.Service;

@Service
public class PessimisticDatabaseLockingTest {

    private final SeatService seatService;

    public PessimisticDatabaseLockingTest(SeatService seatService) {
        this.seatService = seatService;
    }

    public void testPessimisticLocking(Long id) {

        Thread thread = new Thread(() -> {
            try {
                seatService.pessimisticLock(id);

            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " fail to book a seat......................" + e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                seatService.pessimisticLock(id);
            }catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " fail to book a seat...................." + e.getMessage());
            }
        });

        try{
            thread.start();
//            Thread.sleep(2000);
            thread2.start();


            thread.join();
            thread2.join();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }


}
