package com.rkdevblog.karate.employee;

import com.intuit.karate.junit5.Karate;

public class EmployeeTest {

    @Karate.Test
    Karate testAddEmployee() {
        return Karate.run("create-employee").relativeTo(getClass());
    }

    @Karate.Test
    Karate testGetEmployee() {
        return Karate.run("get-employee").relativeTo(getClass());
    }

    @Karate.Test
    Karate testGelEmployeeList() {
        return Karate.run("get-employee-list").relativeTo(getClass());
    }

}
