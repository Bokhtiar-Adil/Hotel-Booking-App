package dev.mrb.fullstack_3.repository;

import dev.mrb.fullstack_3.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

//    @Query("SELECT FROM bookedRoom WHERE ")

    @Query("SELECT a FROM booked_room a WHERE a.confirmationCode = :confirmationCode")
    BookedRoom findByBookingConfirmationCode(String confirmationCode);
    List<BookedRoom> getAllBookingsByRoomId(Long id);
}
