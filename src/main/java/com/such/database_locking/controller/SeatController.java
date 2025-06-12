package com.such.database_locking.controller;

import com.such.database_locking.dto.SeatDTO;
import com.such.database_locking.model.Seat;
import com.such.database_locking.services.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class SeatController {


  private final SeatService seatService;

  public SeatController(SeatService seatService) {
      this.seatService = seatService;
  }

  @PostMapping("seat")
    public ResponseEntity<Seat> create(@RequestBody SeatDTO seatDTO) {
      return new ResponseEntity<>(seatService.create(seatDTO), HttpStatus.CREATED);
  }

}

