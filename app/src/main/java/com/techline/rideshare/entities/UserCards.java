package com.techline.rideshare.entities;

public class UserCards {
    private String nameOnCard;
    private String fullNumberOnCard;
    private String tokenisedCard;
    private String pinOnCard;
    private String otpOnCard;
    private String tokenisationVerifiedOnCard;
    private String authorizationCodeOnCard;
    private String bankOnCard;
    private String typeOnCard;
    private String last4DigitsOnCard;
    private String emilOnCard;
    private String authObjOnCard;
    private String accountNumber;


    public UserCards() {
    }

    public UserCards(String nameOnCard, String fullNumberOnCard, String tokenisedCard,
                     String pinOnCard, String otpOnCard, String tokenisationVerifiedOnCard,
                     String authorizationCodeOnCard, String bankOnCard, String typeOnCard,
                     String last4DigitsOnCard, String emilOnCard, String authObjOnCard,
                     String accountNumber) {
        this.nameOnCard = nameOnCard;
        this.fullNumberOnCard = fullNumberOnCard;
        this.tokenisedCard = tokenisedCard;
        this.pinOnCard = pinOnCard;
        this.otpOnCard = otpOnCard;
        this.tokenisationVerifiedOnCard = tokenisationVerifiedOnCard;
        this.authorizationCodeOnCard = authorizationCodeOnCard;
        this.bankOnCard = bankOnCard;
        this.typeOnCard = typeOnCard;
        this.last4DigitsOnCard = last4DigitsOnCard;
        this.emilOnCard = emilOnCard;
        this.authObjOnCard = authObjOnCard;
        this.accountNumber = accountNumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getFullNumberOnCard() {
        return fullNumberOnCard;
    }

    public void setFullNumberOnCard(String fullNumberOnCard) {
        this.fullNumberOnCard = fullNumberOnCard;
    }

    public String getTokenisedCard() {
        return tokenisedCard;
    }

    public void setTokenisedCard(String tokenisedCard) {
        this.tokenisedCard = tokenisedCard;
    }

    public String getPinOnCard() {
        return pinOnCard;
    }

    public void setPinOnCard(String pinOnCard) {
        this.pinOnCard = pinOnCard;
    }

    public String getOtpOnCard() {
        return otpOnCard;
    }

    public void setOtpOnCard(String otpOnCard) {
        this.otpOnCard = otpOnCard;
    }

    public String getTokenisationVerifiedOnCard() {
        return tokenisationVerifiedOnCard;
    }

    public void setTokenisationVerifiedOnCard(String tokenisationVerifiedOnCard) {
        this.tokenisationVerifiedOnCard = tokenisationVerifiedOnCard;
    }

    public String getAuthorizationCodeOnCard() {
        return authorizationCodeOnCard;
    }

    public void setAuthorizationCodeOnCard(String authorizationCodeOnCard) {
        this.authorizationCodeOnCard = authorizationCodeOnCard;
    }

    public String getBankOnCard() {
        return bankOnCard;
    }

    public void setBankOnCard(String bankOnCard) {
        this.bankOnCard = bankOnCard;
    }

    public String getTypeOnCard() {
        return typeOnCard;
    }

    public void setTypeOnCard(String typeOnCard) {
        this.typeOnCard = typeOnCard;
    }

    public String getLast4DigitsOnCard() {
        return last4DigitsOnCard;
    }

    public void setLast4DigitsOnCard(String last4DigitsOnCard) {
        this.last4DigitsOnCard = last4DigitsOnCard;
    }

    public String getEmilOnCard() {
        return emilOnCard;
    }

    public void setEmilOnCard(String emilOnCard) {
        this.emilOnCard = emilOnCard;
    }

    public String getAuthObjOnCard() {
        return authObjOnCard;
    }

    public void setAuthObjOnCard(String authObjOnCard) {
        this.authObjOnCard = authObjOnCard;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
