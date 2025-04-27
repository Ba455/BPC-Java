package clinic;

public class Treatment {

    private int id;
    private String name;
    private String expertiseArea;

    public Treatment(int id, String name, String expertiseArea) {
        this.id = id;
        this.name = name;
        this.expertiseArea = expertiseArea;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExpertiseArea() {
        return expertiseArea;
    }

    @Override
    public String toString() {
        return name + " (" + expertiseArea + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Treatment treatment = (Treatment) obj;
        return id == treatment.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
