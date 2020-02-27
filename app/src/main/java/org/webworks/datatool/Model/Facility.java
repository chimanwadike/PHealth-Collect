package org.webworks.datatool.Model;

public class Facility {
    private int id;
    private String name;
    private String code;
    private String guid;
    private String contactPerson;
    private String lgaCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setLgaCode(String lgaCode) { this.lgaCode = lgaCode; }

    public String getLgaCode() { return lgaCode; }
}
