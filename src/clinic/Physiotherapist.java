package clinic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Physiotherapist extends Person {

    private List<String> expertiseAreas;
    private Map<String, List<Treatment>> treatmentsByExpertise;

    public Physiotherapist(int id, String fullName, String address, String telephone) {
        super(id, fullName, address, telephone);
        this.expertiseAreas = new ArrayList<>();
        this.treatmentsByExpertise = new HashMap<>();
    }

    public void addExpertise(String expertise) {
        if (!expertiseAreas.contains(expertise)) {
            expertiseAreas.add(expertise);
            treatmentsByExpertise.put(expertise, new ArrayList<>());
        }
    }

    public void removeExpertise(String expertise) {
        expertiseAreas.remove(expertise);
        treatmentsByExpertise.remove(expertise);
    }

    public List<String> getExpertiseAreas() {
        return new ArrayList<>(expertiseAreas);
    }

    public void addTreatment(Treatment treatment) {
        String expertise = treatment.getExpertiseArea();
        if (expertiseAreas.contains(expertise)) {
            if (!treatmentsByExpertise.containsKey(expertise)) {
                treatmentsByExpertise.put(expertise, new ArrayList<>());
            }
            treatmentsByExpertise.get(expertise).add(treatment);
        }
    }

    public List<Treatment> getTreatmentsByExpertise(String expertise) {
        if (treatmentsByExpertise.containsKey(expertise)) {
            return new ArrayList<>(treatmentsByExpertise.get(expertise));
        }
        return new ArrayList<>();
    }

    public List<Treatment> getAllTreatments() {
        List<Treatment> allTreatments = new ArrayList<>();
        for (List<Treatment> treatments : treatmentsByExpertise.values()) {
            allTreatments.addAll(treatments);
        }
        return allTreatments;
    }

    public boolean hasExpertise(String expertise) {
        return expertiseAreas.contains(expertise);
    }

    @Override
    public String toString() {
        return super.toString() + ", Expertise: " + String.join(", ", expertiseAreas);
    }
}
