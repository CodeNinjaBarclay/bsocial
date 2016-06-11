package score;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ScoreController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public ScoreController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
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


        for(Invitation invite : attendingEvents){
            for(String str : invite.getExtraData().keySet()){
                
            }
        }

        PagedList<Reference> friendsList = facebook.friendOperations().getFriends();



        model.addAttribute("feed", feed);

        return "hello";
    }

}