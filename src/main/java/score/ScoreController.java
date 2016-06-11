package score;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.barclaycard.bsocial.domain.Profile;
import uk.co.barclaycard.bsocial.domain.Suggestions;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ScoreController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    private KieContainer kieContainer;

    @Inject
    public ScoreController(Facebook facebook, ConnectionRepository connectionRepository, KieContainer kieContainer) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.kieContainer = kieContainer;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        Profile profile = new Profile();
        profile.setName("ashish");


        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            //return "redirect:/linkedin/connections";
        }

        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());

        // Profile
        AgeRange ageRange = facebook.userOperations().getUserProfile().getAgeRange();
        facebook.userOperations().getUserProfile().getBirthday();

        // Operations
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        List<PlaceTag> travelledPlaces = facebook.userOperations().getTaggedPlaces();

        int groupSubscribedTo = facebook.groupOperations().getMemberships().size();

        PagedList<Invitation> attendingEvents = facebook.eventOperations().getAttending();


        for (Invitation invite : attendingEvents) {
            for (String str : invite.getExtraData().keySet()) {

            }
        }

        PagedList<Reference> friendsList = facebook.friendOperations().getFriends();


        StatelessKieSession socialScoreSession = kieContainer.newStatelessKieSession();
        Suggestions suggestedScore = new Suggestions();
        socialScoreSession.setGlobal("suggestions", suggestedScore);
        socialScoreSession.execute(profile);

        model.addAttribute("feed", feed);

        return "hello";
    }

}