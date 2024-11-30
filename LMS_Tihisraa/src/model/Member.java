package model;

public class Member extends User{
    private MembershipCard membershipCard;

    public Member(String name, String contact, String address, MembershipCard membershipCard) {
        super(name,contact,address);
        this.membershipCard = membershipCard;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    @Override
    public void signIn() {
        System.out.println("Member Sign In");
    }

    @Override
    public String toString() {
        return "Member{" + getName() + getContact() +
                "membershipCard=" + membershipCard.getCardNumber() + membershipCard.getExpiryDate() +
                '}';
    }
}
