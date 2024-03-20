package dev.mrb.fullstack_3.service;

import dev.mrb.fullstack_3.model.BookedRoom;
import dev.mrb.fullstack_3.response.BookingResponse;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookingsByRoomId(Long id);

    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);
}
