package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MembershipCard {
    private String cardNumber;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private String expiryDate;
    public MembershipCard(String cardNumber, Date expiryDate) {
        this.cardNumber = cardNumber;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.expiryDate = dateFormat.format(expiryDate);
    }
}
