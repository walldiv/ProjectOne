import com.ex.dao.SponsorDAO;
import com.ex.model.Sponsor;
import com.ex.service.SponsorService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class SponsorTests {
    Sponsor sponsor = new Sponsor("Meijers", "616-867-5309", "fred@meijer.com");

    @Mock
    SponsorDAO dao;

    @InjectMocks
    SponsorService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        service = new SponsorService(dao);
//        service = new SponsorService();
    }

    @Test
    public void registerSponsor() throws Exception {
        service.registerSponsor(sponsor);
    }

    @Test
    public void blah() {
        String x = sponsor.getPhone();
        x = x.replace("-", "");
        sponsor.setPhone(x);
        System.out.println(sponsor.toString());

    }
}
