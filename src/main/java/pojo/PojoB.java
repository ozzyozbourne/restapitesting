package pojo;

public class PojoB {
    private String reviewScore;

    private String releaseDate;

    private String name;

    @Override
    public String toString() {
        return "ClassPojo [reviewScore = "+reviewScore+", releaseDate = "+releaseDate+", name = "+name+"]";
    }

    public PojoB() {
    }

    public String getReviewScore() {
        return reviewScore;
    }

    public PojoB(String reviewScore, String releaseDate, String name) {
        this.reviewScore = reviewScore;
        this.releaseDate = releaseDate;
        this.name = name;
    }

    public void setReviewScore(String reviewScore) {
        this.reviewScore = reviewScore;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
