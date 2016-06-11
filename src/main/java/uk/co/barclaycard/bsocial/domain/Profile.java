package uk.co.barclaycard.bsocial.domain;

import lombok.*;

/**
 * Created by ashishgodani on 11/06/2016.
 */
public class Profile {

    private String name;
    private int age;
    private int friendsCount;
    private int eventList;
    private String feeds;
    private int travelPlaces;
    private int socialScore = 0;

    public int getSocialScore() {
        return socialScore;
    }

    public void setSocialScore(int socialScore) {
        this.socialScore = socialScore;
    }

    public int getEventList() {
        return eventList;
    }

    public void setEventList(int eventList) {
        this.eventList = eventList;
    }

    public String getFeeds() {
        return feeds;
    }

    public void setFeeds(String feeds) {
        this.feeds = feeds;
    }

    public int getTravelPlaces() {
        return travelPlaces;
    }

    public void setTravelPlaces(int travelPlaces) {
        this.travelPlaces = travelPlaces;
    }

    public int getFriendsCount() {

        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
