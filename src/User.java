
/**
 * Class User object
 * @author KhoiLQCE130023
 */
public class User implements Comparable<User>{

    // Declare variables
    private String userId;
    private int score;
    


     // The Get User ID Method
    public String getUserId() {
        return userId;
    }

    
     // The Set User ID Method
     public void setUserId(String userId) throws Exception {
        if (userId.trim().equals("")) {
            throw new Exception("The User ID can't be empty!");
        } else {
            this.userId = userId;
        }
    }

    // The Get score Method
    public int getScore() {
        return score;
    }

   
     // The Set score Method
    public void setScore(int score) throws Exception {
            this.score = score;
    }

    // create a new user
    public User(String userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    // compare and sort
    @Override
    public int compareTo(User t) {
        if (this.getScore() > t.getScore()) return -1;
        if (this.getScore() < t.getScore()) return 1;
        return 0;
    }
}
