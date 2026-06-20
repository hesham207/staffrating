package com.example.staffrating.design;

import com.example.staffrating.model.RoleType;

public class StaffProfileFactory {

    public static StaffMemberProfile createProfile(RoleType roleType) {
        if (roleType == RoleType.TA) {
            return new TaProfile();
        }

        if (roleType == RoleType.PROF) {
            return new ProfProfile();
        }

        return new DefaultStaffProfile(roleType.name());
    }
}