package com.such.database_locking.services.impl;

import com.such.database_locking.dto.SeatDTO;
import com.such.database_locking.model.Seat;
import com.such.database_locking.repository.SeatRepository;
import com.such.database_locking.services.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public Seat create(SeatDTO seatDTO) {
        return seatRepository.save(toEntity.apply(seatDTO));
    }

    @Override
    @Transactional
    public Seat bookedSeat(Long id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));
        if (seat.isBooked()) {
            throw new RuntimeException("Seat is already booked");
        }
        seat.setBooked(true);
        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public void pessimisticLock(Long id) {
        System.out.println(Thread.currentThread().getName() + " Started pessimisticLock.....................");
        Seat seat = seatRepository.findByIdWithLock(id).orElseThrow(() -> new RuntimeException("Seat not found"));

        System.out.println(Thread.currentThread().getName() + " acquired the lock for seat id " + id);
        if (seat.isBooked()) {
            System.out.println(Thread.currentThread().getName() + " failed Seat Id " + id + " is already booked ");
            throw new RuntimeException("Seat is already booked");
        }
        seat.setBooked(true);
        seatRepository.save(seat);
        System.out.println("pessimisticLock finished" + seat.getId());

        System.out.println(Thread.currentThread().getName() + " successfully book the seat with ID " + id);
    }


    Function<SeatDTO, Seat> toEntity = (payload) -> {
        Seat seat = new Seat();
        seat.setBooked(payload.booked());
        seat.setMovieName(payload.movieName());
        return seat;
    };
}
