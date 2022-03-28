package User;

public class Movies {

    private int id;
    private String name;
    private String summary;
    private double imdb;
    private String category;
    private int user_id;

    public Movies(String nameField2, String summaryField2, String imdbField2, String categoryField2) {
    }

    public Movies(int id, String name, String summary, double imdb, String category, int user_id) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.imdb = imdb;
        this.category = category;
        this.user_id = user_id;
    }

    public Movies(int id, String name, String summary, double imdb, String category) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.imdb = imdb;
        this.category = category;
    }

    public Movies(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Movies(String name, String summary, double imdb, String category, int user_id) {
        this.name = name;
        this.summary = summary;
        this.imdb = imdb;
        this.category = category;
        this.user_id = user_id;
    }

    public Movies(String name, String summary, double imdb, String category) {
        this.name = name;
        this.summary = summary;
        this.imdb = imdb;
        this.category = category;
    }

    public Movies() {

    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getImdb() {
        return imdb;
    }

    public void setImdb(double imdb) {
        this.imdb = imdb;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", imdb=" + imdb +
                ", category='" + category + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}