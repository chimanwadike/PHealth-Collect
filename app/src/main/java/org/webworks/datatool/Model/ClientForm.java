package org.webworks.datatool.Model;

import java.util.Date;

public class ClientForm {
    private int Id;
    private String FormDate,DateClientConfirmed;
    private String ClientName, ClientHospitalNumber;
    private String ClientLastname;
    private String ClientCode, ClientIdentifier;
    private String ClientAddress, ClientVillage,  ClientGeoCode, ClientAddress2, ClientAddress3, CareGiverName;
    private String ClientPhone, ClientLga, ClientState;
    private String Age;
    private String Dob;
    private String EstimatedDob;
    private int Sex;
    private int MaritalStatus, EmploymentStatus, Religion, EducationLevel,RiskLevel, FinalRecencyTestResult;
    private int RefferedTo;
    private String DateReferred;
    private int RefferedFrom;
    private String ServiceNeeded;
    private String Comment;
    private String DateOfHivTest;
    private String TestingPoint;
    private int HivResult;
    private String DateOfPrevHivTest;
    private int SessionType;
    private int IndexClient; //0=> no, 1=>yes
    private int IndexClientType, HivRecencyTestType;
    private String IndexDomClientType;
    private String IndexClientId;
    private int Referred; //0=> referred, 1=> commencedART
    private int Uploaded; //0=> not uploaded/editable, 1=> uploaded/editable, 2=> uploaded/not editable, 3=>downloaded
    private String ApiId;
    private int Reported; //0=> not reported, 1=>reported data not uploaded 2 => reported data uploaded
    private int PreviouslyTested; //0=> not tested, 1=>tested
    private String DateClientReported, HivRecencyTestDate;
    private Date CreateDate;
    private int Progress;
    private String Pretest;
    private int CurrentHivResult;
    private String PostTest;
    private int TestedBefore;
    private int StoppedAtPreTest;
    private int Traced, ClientConfirmed;
    private String User, Facility;
    private String rstAgeGroup,  rstInformation, rstTestDate, rstTestResult, referralLga, referralState, referralTestingArea;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getApiId() {
        return ApiId;
    }

    public void setApiId(String apiId) {
        ApiId = apiId;
    }

    public String getFormDate() {
        return FormDate;
    }

    public void setFormDate(String date) {
        FormDate = date;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getClientAddress() {
        return ClientAddress;
    }

    public void setClientAddress(String clientAddress) {
        ClientAddress = clientAddress;
    }

    public String getClientPhone() {
        return ClientPhone;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public int getSex() {
        return Sex;
    }

    public  int getMaritalStatus(){return MaritalStatus;}

    public int getEmploymentStatus(){return EmploymentStatus;}
    public void setEmploymentStaus(int employmentStatus){EmploymentStatus = employmentStatus;}

    public int getReligion(){return Religion;}
    public void setReligion(int religion){Religion = religion;}

    public int getEducationLevel(){return EducationLevel;}
    public void setEducationLevel(int educationLevel){EducationLevel = educationLevel;}

    public int getFinalRecencyTestResult(){return FinalRecencyTestResult;}
    public void setFinalRecencyTestResult(int finalRecencyTestResult){FinalRecencyTestResult = finalRecencyTestResult;}

    public String getClientHospitalNumber() { return ClientHospitalNumber; }
    public void setClientHospitalNumber(String clientHospitalNumber) { ClientHospitalNumber = clientHospitalNumber;}

    public String getClientVillage() { return ClientVillage; }
    public void setClientVillage(String clientVillage) { ClientVillage = clientVillage;}

    public String getClientLga() { return ClientLga;}
    public void setClientLga(String clientLga) { ClientLga = clientLga;}

    public String getClientState() { return ClientState; }
    public void setClientState(String clientState) { ClientState = clientState; }

    public String getClientIdentifier() { return ClientIdentifier;}
    public void setClientIdentifier(String clientIdentifier) { ClientIdentifier = clientIdentifier;}

    public String getClientGeoCode() { return ClientGeoCode; }
    public void setClientGeoCode(String clientGeoCode) { ClientGeoCode = clientGeoCode;}

    public int getHivRecencyTestType() { return HivRecencyTestType;}
    public void setHivRecencyTestType(int hivRecencyTestType) { HivRecencyTestType = hivRecencyTestType; }

    public String getHivRecencyTestDate() { return HivRecencyTestDate;}

    public void setHivRecencyTestDate(String hivRecencyTestDate) { HivRecencyTestDate = hivRecencyTestDate;}

    public void setMaritalStatus(int maritalStatus) { MaritalStatus = maritalStatus; }

    public void setSex(int sex) {
        Sex = sex;
    }

    public int getRefferedTo() {
        return RefferedTo;
    }

    public void setRefferedTo(int refferedTo) {
        RefferedTo = refferedTo;
    }

    public int getRefferedFrom() {
        return RefferedFrom;
    }

    public void setRefferedFrom(int refferedFrom) {
        RefferedFrom = refferedFrom;
    }

    public String getServiceNeeded() {
        return ServiceNeeded;
    }

    public void setServiceNeeded(String serviceNeeded) {
        ServiceNeeded = serviceNeeded;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getUploaded() {
        return Uploaded;
    }

    public void setUploaded(int uploaded) {
        Uploaded = uploaded;
    }

    public int getReported() {
        return Reported;
    }

    public void setReported(int reported) {
        Reported = reported;
    }

    public String getClientLastname() {
        return ClientLastname;
    }

    public void setClientLastname(String clientLastname) {
        ClientLastname = clientLastname;
    }

    public String getDateOfHivTest() {
        return DateOfHivTest;
    }

    public void setDateOfHivTest(String dateOfHivTest) {
        DateOfHivTest = dateOfHivTest;
    }

    public String getTestingPoint() {
        return TestingPoint;
    }

    public void setTestingPoint(String testingPoint) {
        TestingPoint = testingPoint;
    }

    public int getHivResult() {
        return HivResult;
    }

    public void setHivResult(int hivResult) {
        HivResult = hivResult;
    }

    public String getDateOfPrevHivTest() {
        return DateOfPrevHivTest;
    }

    public void setDateOfPrevHivTest(String dateOfPrevHivTest) {
        DateOfPrevHivTest = dateOfPrevHivTest;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getEstimatedDob() {
        return EstimatedDob;
    }

    public void setEstimatedDob(String estimatedDob) {
        EstimatedDob = estimatedDob;
    }

    public int getPreviouslyTested() {
        return PreviouslyTested;
    }

    public void setPreviouslyTested(int previouslyTested) {
        PreviouslyTested = previouslyTested;
    }

    public int getSessionType() {
        return SessionType;
    }

    public void setSessionType(int sessionType) {
        SessionType = sessionType;
    }

    public int getIndexClient() {
        return IndexClient;
    }

    public void setIndexClient(int indexClient) {
        IndexClient = indexClient;
    }

    public int getIndexClientType() {
        return IndexClientType;
    }

    public void setIndexClientType(int indexClientType) {
        IndexClientType = indexClientType;
    }

    public String getIndexClientId() {
        return IndexClientId;
    }

    public void setIndexClientId(String indexClientId) {
        IndexClientId = indexClientId;
    }

    public String getDateClientReported() {
        return DateClientReported;
    }

    public void setDateClientReported(String dateClientReported) {
        DateClientReported = dateClientReported;
    }


    public int getReferred() {
        return Referred;
    }

    public void setReferred(int referred) {
        Referred = referred;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }

    public String getPretest() {
        return Pretest;
    }

    public void setPretest(String pretest) {
        Pretest = pretest;
    }

    public int getCurrentHivResult() {
        return CurrentHivResult;
    }

    public void setCurrentHivResult(int currentHivResult) {
        CurrentHivResult = currentHivResult;
    }

    public String getDateReferred() {
        return DateReferred;
    }

    public void setDateReferred(String dateReferred) {
        DateReferred = dateReferred;
    }

    public String getPostTest() {
        return PostTest;
    }

    public void setPostTest(String postTest) {
        PostTest = postTest;
    }

    public int getTestedBefore() {
        return TestedBefore;
    }

    public void setTestedBefore(int testedBefore) {
        TestedBefore = testedBefore;
    }

    public String getIndexDomClientType() {
        return IndexDomClientType;
    }

    public void setIndexDomClientType(String indexDomClientType) {
        IndexDomClientType = indexDomClientType;
    }

    public int getStoppedAtPreTest() {
        return StoppedAtPreTest;
    }

    public void setStoppedAtPreTest(int stoppedAtPreTest) {
        StoppedAtPreTest = stoppedAtPreTest;
    }

    public int getTraced() {
        return Traced;
    }

    public void setTraced(int traced) {
        Traced = traced;
    }

    public int getClientConfirmed() { return ClientConfirmed; }

    public void setClientConfirmed(int clientConfirmed) { ClientConfirmed = clientConfirmed; }

    public String getDateClientConfirmed() { return DateClientConfirmed; }

    public void setDateClientConfirmed(String dateClientConfirmed) { DateClientConfirmed = dateClientConfirmed; }

    public void setUser(String user) { User = user; }

    public String getUser() { return User; }

    public void setFacility(String facility) { Facility = facility; }

    public String getFacility() { return Facility; }

    public String getRstAgeGroup() { return rstAgeGroup; }

    public void setRstAgeGroup(String rstAgeGroup) { this.rstAgeGroup = rstAgeGroup; }

    public String getRstInformation() { return rstInformation; }

    public void setRstInformation(String rstInformation) { this.rstInformation = rstInformation; }

    public String getRstTestDate() { return rstTestDate; }

    public void setRstTestDate(String rstTestDate) { this.rstTestDate = rstTestDate; }

    public String getRstTestResult() { return rstTestResult; }

    public void setRstTestResult(String rstTestResult) { this.rstTestResult = rstTestResult; }

    public String getReferralLga() { return referralLga; }

    public void setReferralLga(String referralLga) { this.referralLga = referralLga; }

    public String getReferralState() { return referralState; }

    public void setReferralState(String referralState) { this.referralState = referralState; }

    public int getRiskLevel() { return RiskLevel; }

    public void setRiskLevel(int riskLevel) { RiskLevel = riskLevel; }

    public void setReferralTestingArea(String referralTestingArea) { this.referralTestingArea = referralTestingArea; }

    public String getReferralTestingArea() { return referralTestingArea; }

    public String getCareGiverName() { return CareGiverName; }

    public void setCareGiverName(String careGiverName) { CareGiverName = careGiverName; }

    public String getClientAddress2() { return ClientAddress2; }

    public void setClientAddress2(String clientAddress2) { ClientAddress2 = clientAddress2; }

    public String getClientAddress3() { return ClientAddress3; }

    public void setClientAddress3(String clientAddress3) { ClientAddress3 = clientAddress3; }
}
