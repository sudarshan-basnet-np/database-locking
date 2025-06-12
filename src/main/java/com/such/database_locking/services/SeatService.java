package com.such.database_locking.services;

import com.such.database_locking.dto.SeatDTO;
import com.such.database_locking.model.Seat;

public interface SeatService {
    Seat create(SeatDTO seatDTO);

    Seat bookedSeat(Long id);

    void pessimisticLock(Long id);
}
