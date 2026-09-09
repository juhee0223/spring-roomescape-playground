package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createReservationTime(LocalTime time) {
        ReservationTime reservationTime = ReservationTime.createNewReservationTime(time);
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTime(Long id) {
        int deletedRows = reservationTimeRepository.deleteById(id);
        if (deletedRows == 0) {
            throw new ReservationNotFoundException("id " + id + "에 해당하는 시간을 찾을 수 없습니다.");
        }
    }
}
