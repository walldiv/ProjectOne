import com.ex.dao.CoachDAO;
import com.ex.model.*;
import com.ex.service.CoachService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoachTests {
    Sponsor sponsor = new Sponsor("Meijers", "616-867-5309", "fred@meijer.com");
    List<Player> players = new ArrayList<>();
    List<Schedule> schedules = new ArrayList<>();
    Person coach = new Person(1, "BillyBob Thornton", "616-458-1234", "616-458-1234", PhoneCarrier.TMobile,
            true, new Team(), 7);
    Team team = new Team("Bad News Bears", coach, players, schedules, null);
    Player player = new Player(1, "Chucky Barnes", "6161234567", "6167654312", PhoneCarrier.Cingular,
            false, team, 6, "Knoble Barnes",14, Position.none);

    @Mock
    CoachDAO dao;

    @InjectMocks
    CoachService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        service = new CoachService(dao);
//        service = new CoachService();
        coach.setTeam(team);
    }

    @Test
    public void addSponsor() throws Exception {
        Mockito.doNothing().when(dao).addSponsor(sponsor, team);
        boolean success = service.addSponsor(sponsor, team);
        Assert.assertTrue("ADD SPONSOR FAILURE", success);
    }

    @Test
    public void changeTeamName() throws Exception {
        boolean success = service.renameTeam(team.getName(), "Luckys");
        Assert.assertTrue("UNABLE TO CHANGE TEAM NAME", success);
    }

    @Test
    public void setTeamPracticeDay() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        Mockito.doNothing().when(dao).setPracticeDay(date, team.getName());
        boolean success = service.setPracticeDay(date, team.getName());
        Assert.assertTrue("ADD PRACTICEDAY FAILURE", success);
    }

    @Test
    public void addGameScore() throws Exception {
        Mockito.doNothing().when(dao).addGameScore(1, 7, true);
        boolean success = service.addGameScore(1, 7, true);
        Assert.assertTrue("SCORE WAS NOT ADDED", success);
    }

    @Test
    public void forfeitGame() throws Exception {
        //int scheduleID, Team team
        Mockito.doNothing().when(dao).forfeitGame(1, team);
        boolean success = service.forfeitGame(1, team);
        Assert.assertTrue("FORFEIT WAS NOT ADDED", success);
    }

    @Test
    public void changePlayerPosition() throws Exception {
        //Player player, Position position
        Mockito.doNothing().when(dao).changePlayerPosition(player, Position.Catcher);
        boolean success = service.changePlayerPosition(player, Position.Catcher);
        Assert.assertTrue("NO PLAYER POSITION CHANGE", success);
    }

    @Test
    public void addPlayerToTeam() throws Exception {
        //Player player, Team team
        Mockito.doNothing().when(dao).addPlayerToTeam(player, team);
        boolean success = service.addPlayerToTeam(player, team);
        Assert.assertTrue("NO PLAYER ADDED TO TEAM", success);
    }

    @Test
    public void removePlayerFromTeam() throws Exception {
        //Player player, Team team
        Mockito.doNothing().when(dao).removePlayerFromTeam(player, team);
        boolean success = service.removePlayerFromTeam(player, team);
        Assert.assertTrue("NO PLAYER REMOVED FROM TEAM", success);
    }
        @Test
    public void createDate() {
        LocalDateTime date = LocalDateTime.now();
        Timestamp time = Timestamp.valueOf(date);
        System.out.println(time.toString());
    }
}
