package controller;

import Controller.DBController;
import model.Member;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class MemberController {

    private ArrayList<Member> members;
    private DBController dbController;

    public MemberController(DBController dbController) throws ParseException {
        this.dbController = dbController;
        this.members = new ArrayList<>();
        getMembersFromDatabase();
    }

    public void addMember(Member member) {
        if (!members.contains(member)) {
            members.add(member);
            try {
                dbController.addMember(member);
            } catch (SQLException e) {
                System.err.println("Error adding member to database: " + e.getMessage());
            }
        } else {
            System.out.println("Member already exists.");
        }
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    private void getMembersFromDatabase() throws ParseException {
        try {
            members = dbController.getAllMembers();
        } catch (SQLException e) {
            System.err.println("Error fetching members from database: " + e.getMessage());
        }
    }

    public Member findMemberByID(String memberID) throws ParseException {
        // You'll need to implement this using your MemberController or DBController
        // This is a placeholder - replace with actual implementation
        MemberController memberController = new MemberController(DBController.getInstance());
        for (Member member : memberController.getMembers()) {
            if (member.getMembershipCard().getCardNumber().equals(memberID)) {
                return member;
            }
        }
        return null;
    }

    public Member findMemberByName(String memberName) throws ParseException {
        MemberController memberController = new MemberController(DBController.getInstance());
        for (Member member : memberController.getMembers()) {
            if (member.getName().equalsIgnoreCase(memberName)) {
                return member;
            }
        }
        return null;
    }
}
