package cz.educanet;

public class Publisher {
    private int ID;
    private String name;
    private int count;

    public Publisher(int ID, String name, int count) {
        this.ID = ID;
        this.name = name;
        this.count = count;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
