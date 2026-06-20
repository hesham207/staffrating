package com.example.staffrating.design;

public class DefaultStaffProfile implements StaffMemberProfile {

    private final String roleName;

    public DefaultStaffProfile(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String displayTitle() {
        return roleName;
    }
}