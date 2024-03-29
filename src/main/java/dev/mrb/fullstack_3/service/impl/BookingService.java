package dev.mrb.fullstack_3.service.impl;

import dev.mrb.fullstack_3.exception.InvalidBookingRequestException;
import dev.mrb.fullstack_3.model.BookedRoom;
import dev.mrb.fullstack_3.model.Room;
import dev.mrb.fullstack_3.repository.BookingRepository;
import dev.mrb.fullstack_3.repository.RoomRepository;
import dev.mrb.fullstack_3.service.IBookingService;
import dev.mrb.fullstack_3.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;

    private final IRoomService roomService;

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long id) {
        Room room = roomRepository.findById(id).get();
        return room.getBookings();
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Room not available");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        // insert logic -- too lazy to add now -- so everything is true now :D
        return true;
    }
}
