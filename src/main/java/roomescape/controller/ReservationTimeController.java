package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationTimeRepository;

@Controller
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
            @Valid @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime temporaryReservationTime = ReservationTime.createNewReservationTime(
                reservationTimeRequest.time());
        ReservationTime savedReservationTime = reservationTimeRepository.save(temporaryReservationTime);
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(savedReservationTime);

        return ResponseEntity.created(URI.create("/times/" + reservationTimeResponse.id())).body(reservationTimeResponse);
    }

    @GetMapping("/time")
    public String timePage() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeRepository.findAll().stream().map(ReservationTimeResponse::from).toList();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        int deletedRows = reservationTimeRepository.deleteById(id);

        if (deletedRows == 0) {
            throw new ReservationNotFoundException("id " + id + "에 해당하는 시간을 찾을 수 없습니다.");
        }

        return ResponseEntity.noContent().build();
    }
}
