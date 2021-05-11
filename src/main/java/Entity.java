public class Entity {
    private String name;
    private int id;
    private double creationTime;
    private int priority;
    private PetriNet petriNet;

    public Entity(String name) {
        this.name = name;
    }

    public Entity(String name, PetriNet petriNet) {
        this.name = name;
        this.petriNet = petriNet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(double creationTime) {
        this.creationTime = creationTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public PetriNet getPetriNet() {
        return petriNet;
    }

    public void setPetriNet(PetriNet petriNet) {
        this.petriNet = petriNet;
    }
}
