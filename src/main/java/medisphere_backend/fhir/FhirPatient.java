package medisphere_backend.fhir;

public class FhirPatient {

    private String resourceType;
    private String id;
    private String name;
    private int age;
    private String gender;

    public FhirPatient() {
        this.resourceType = "Patient";
    }

    public FhirPatient(String id, String name, int age, String gender) {
        this.resourceType = "Patient";
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}