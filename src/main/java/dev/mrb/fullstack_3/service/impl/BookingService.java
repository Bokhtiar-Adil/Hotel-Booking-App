package dev.mrb.fullstack_3.service.impl;

import dev.mrb.fullstack_3.model.BookedRoom;
import dev.mrb.fullstack_3.service.IBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {
    @Override
    public List<BookedRoom> getAllBookinsByRoomId(Long id) {
        return null;
    }
}
