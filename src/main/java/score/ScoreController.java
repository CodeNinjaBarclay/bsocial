package score;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.barclaycard.bsocial.domain.Profile;
import uk.co.barclaycard.bsocial.domain.Suggestions;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/")
public class ScoreController {

    private Facebook facebook;
    private Twitter twitter;
    private ConnectionRepository connectionRepository;
    private KieContainer kieContainer;

    @Inject
    public ScoreController(Facebook facebook, Twitter twitter, ConnectionRepository connectionRepository, KieContainer kieContainer) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
        this.kieContainer = kieContainer;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        Profile profile = new Profile();

        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return "redirect:/connect/linkedin";
        }

        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());


        // Operations
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        List<PlaceTag> travelledPlaces = facebook.userOperations().getTaggedPlaces();

        int groupSubscribedTo = facebook.groupOperations().getMemberships().size();

        PagedList<Invitation> attendingEvents = facebook.eventOperations().getAttending();


//        for (Invitation invite : attendingEvents) {
//            for (String str : invite.getExtraData().keySet()) {
//
//            }
//        }

//        PagedList<Reference> friendsList = facebook.friendOperations().getFriends();

        profile.setVerified(facebook.userOperations().getUserProfile().isVerified());
        profile.setName(facebook.userOperations().getUserProfile().getName());
        profile.setFriendsCount(facebook.friendOperations().getFriends().getTotalCount());

        profile.setEventList(2);
        profile.setTravelPlaces(2);

        StatelessKieSession socialScoreSession = kieContainer.newStatelessKieSession();
        Suggestions suggestedScore = new Suggestions();
        socialScoreSession.setGlobal("suggestions", suggestedScore);
        socialScoreSession.execute(profile);

        model.addAttribute("feed", feed);
        model.addAttribute("profile", twitter.userOperations().getUserProfile());
/*
        ListOperations listOperations = twitter.listOperations();

        int fritwitter.friendOperations().getFriendIds().size();
*/


        return "hello";
    }

}