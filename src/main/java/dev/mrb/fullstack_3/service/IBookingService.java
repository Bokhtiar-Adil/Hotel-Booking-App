package dev.mrb.fullstack_3.service;

import dev.mrb.fullstack_3.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookinsByRoomId(Long id);
}
