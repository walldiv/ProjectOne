import com.ex.dao.GameDAO;
import com.ex.model.Schedule;
import com.ex.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameTests {
    List<Schedule> schedules = new ArrayList<>();
    Schedule tmp = new Schedule(1, LocalDateTime.now(), "Bad News Bears", "ThunderCats", 7, 12, null);
//2020-05-01 17:32:28

    @Mock
    GameDAO dao;

    @InjectMocks
    GameService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        service = new GameService(dao);
//        service = new GameService();
        schedules.add(tmp);
    }

    @Test
    public void getScheduleOnDay() {
        //LocalDate day = LocalDate.now();   //will fail if anything other than 2020-05-01
//        LocalDate day = LocalDate.of(2020, 05, 01);
//        Mockito.when(dao.getScheduleOnDay(day)).thenReturn(schedules);
//        List<Schedule> tmpSched = new ArrayList<>();
//        tmpSched = service.getScheduleOnDay(day);
//        boolean result = tmpSched.size() > 0 ? true : false;
//        System.out.printf("FOUND %d GAMES ON DAY", tmpSched.size());
//        Assert.assertTrue("NOTHIGN RETURNED", result);
    }
}
