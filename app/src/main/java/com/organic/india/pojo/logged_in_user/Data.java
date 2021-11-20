package com.organic.india.pojo.logged_in_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("employee_code")
    @Expose
    private String employeeCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("father_dob")
    @Expose
    private String fatherDob;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("mother_dob")
    @Expose
    private String motherDob;
    @SerializedName("spouse_name")
    @Expose
    private String spouseName;
    @SerializedName("spouse_dob")
    @Expose
    private String spouseDob;
    @SerializedName("childs_details")
    @Expose
    private String childsDetails;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("personal_email")
    @Expose
    private String personalEmail;
    @SerializedName("present_address")
    @Expose
    private String presentAddress;
    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("emergency_name")
    @Expose
    private String emergencyName;
    @SerializedName("emergency_mobile")
    @Expose
    private Long emergencyMobile;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("joining_date")
    @Expose
    private String joiningDate;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("resignation_date")
    @Expose
    private String resignationDate;
    @SerializedName("separation_date")
    @Expose
    private String separationDate;
    @SerializedName("last_working_day")
    @Expose
    private String lastWorkingDay;
    @SerializedName("irm")
    @Expose
    private String irm;
    @SerializedName("reporting_manager_id")
    @Expose
    private String reportingManagerId;
    @SerializedName("reporting_manager")
    @Expose
    private String reportingManager;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("band")
    @Expose
    private String band;
    @SerializedName("store_codes")
    @Expose
    private String storeCodes;
    @SerializedName("cadre")
    @Expose
    private String cadre;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("uan_no")
    @Expose
    private String uanNo;
    @SerializedName("esic_no")
    @Expose
    private String esicNo;
    @SerializedName("bank_account_no")
    @Expose
    private String bankAccountNo;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("aadhar_no")
    @Expose
    private String aadharNo;
    @SerializedName("pf_number")
    @Expose
    private String pfNumber;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("highest_qualification")
    @Expose
    private String highestQualification;
    @SerializedName("passing_year")
    @Expose
    private String passingYear;
    @SerializedName("institute_name")
    @Expose
    private String instituteName;
    @SerializedName("biomatric_attendance")
    @Expose
    private String biomatricAttendance;
    @SerializedName("online_app_attendance")
    @Expose
    private String onlineAppAttendance;
    @SerializedName("role_sales_non_sales")
    @Expose
    private String roleSalesNonSales;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("aadhar_photo")
    @Expose
    private String aadharPhoto;
    @SerializedName("pan_photo")
    @Expose
    private String panPhoto;
    @SerializedName("weekly_off")
    @Expose
    private String weeklyOff;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherDob() {
        return fatherDob;
    }

    public void setFatherDob(String fatherDob) {
        this.fatherDob = fatherDob;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherDob() {
        return motherDob;
    }

    public void setMotherDob(String motherDob) {
        this.motherDob = motherDob;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSpouseDob() {
        return spouseDob;
    }

    public void setSpouseDob(String spouseDob) {
        this.spouseDob = spouseDob;
    }

    public String getChildsDetails() {
        return childsDetails;
    }

    public void setChildsDetails(String childsDetails) {
        this.childsDetails = childsDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public Long getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(Long emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResignationDate() {
        return resignationDate;
    }

    public void setResignationDate(String resignationDate) {
        this.resignationDate = resignationDate;
    }

    public String getSeparationDate() {
        return separationDate;
    }

    public void setSeparationDate(String separationDate) {
        this.separationDate = separationDate;
    }

    public String getLastWorkingDay() {
        return lastWorkingDay;
    }

    public void setLastWorkingDay(String lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
    }

    public String getIrm() {
        return irm;
    }

    public void setIrm(String irm) {
        this.irm = irm;
    }

    public String getReportingManagerId() {
        return reportingManagerId;
    }

    public void setReportingManagerId(String reportingManagerId) {
        this.reportingManagerId = reportingManagerId;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getStoreCodes() {
        return storeCodes;
    }

    public void setStoreCodes(String storeCodes) {
        this.storeCodes = storeCodes;
    }

    public String getCadre() {
        return cadre;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUanNo() {
        return uanNo;
    }

    public void setUanNo(String uanNo) {
        this.uanNo = uanNo;
    }

    public String getEsicNo() {
        return esicNo;
    }

    public void setEsicNo(String esicNo) {
        this.esicNo = esicNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear = passingYear;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getBiomatricAttendance() {
        return biomatricAttendance;
    }

    public void setBiomatricAttendance(String biomatricAttendance) {
        this.biomatricAttendance = biomatricAttendance;
    }

    public String getOnlineAppAttendance() {
        return onlineAppAttendance;
    }

    public void setOnlineAppAttendance(String onlineAppAttendance) {
        this.onlineAppAttendance = onlineAppAttendance;
    }

    public String getRoleSalesNonSales() {
        return roleSalesNonSales;
    }

    public void setRoleSalesNonSales(String roleSalesNonSales) {
        this.roleSalesNonSales = roleSalesNonSales;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getAadharPhoto() {
        return aadharPhoto;
    }

    public void setAadharPhoto(String aadharPhoto) {
        this.aadharPhoto = aadharPhoto;
    }

    public String getPanPhoto() {
        return panPhoto;
    }

    public void setPanPhoto(String panPhoto) {
        this.panPhoto = panPhoto;
    }

    public String getWeeklyOff() {
        return weeklyOff;
    }

    public void setWeeklyOff(String weeklyOff) {
        this.weeklyOff = weeklyOff;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
