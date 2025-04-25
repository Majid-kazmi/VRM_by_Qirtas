package com.example.vrmbyqirtas;

public class Voter {
    String name, fatherName,CNIC,address,mobile,uc,ilaqa,category;
    boolean specialVoter;
    public Voter() {}
    public Voter(String name, String fatherName, String CNIC, String address, String mobile, String uc,
                 String ilaqa, String category, boolean specialVoter)
    {
        this.name = name;
        this.fatherName = fatherName;
        this.CNIC = CNIC;
        this.address = address;
        this.mobile = mobile;
        this.uc = uc;
        this.ilaqa = ilaqa;
        this.category = category;
        this.specialVoter = specialVoter;
    }
}

