package dev.mrb.fullstack_3.controller;

import dev.mrb.fullstack_3.exception.InvalidBookingRequestException;
import dev.mrb.fullstack_3.exception.ResourceNotFoundException;
import dev.mrb.fullstack_3.model.BookedRoom;
import dev.mrb.fullstack_3.model.Room;
import dev.mrb.fullstack_3.response.BookingResponse;
import dev.mrb.fullstack_3.response.RoomResponse;
import dev.mrb.fullstack_3.service.IBookingService;
import dev.mrb.fullstack_3.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping(path = "/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponses(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping(path = "/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponses(booking);
            return ResponseEntity.ok(bookingResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest) {
        try {
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room booked successfully. Confirmation code: " + confirmationCode);
        } catch (InvalidBookingRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }
    private BookingResponse getBookingResponses(BookedRoom booking) {
        Optional<Room> room = roomService.getRoomById(booking.getRoom().getId());
        RoomResponse roomResponse = new RoomResponse(room.get().getId(), room.get().getRoomType(), room.get().getRoomPrice());
        return new BookingResponse(booking.getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getBookingConfirmationCode(),
                roomResponse);
    }

}
