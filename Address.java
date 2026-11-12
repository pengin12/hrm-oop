public class Address {
    private String number;
    private String street;
    private String ward;
    private String district;
    private String city;

    public Address() {
        this.number = null;
        this.street = null;
        this.ward = null;
        this.district = null;
        this.city = null;
    }

    public Address(String number, String street, String ward, String district, String city) {
        this.number = number;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return this.ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void printFullAddress() {
        System.out
                .println(this.number + " " + this.street + ", " + this.ward + ", " + this.district + ", " + this.city);
    }
}
