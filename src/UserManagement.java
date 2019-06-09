
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * User management
 * @author KhoiLQCE130023
 */
public class UserManagement {

    private String US_FILE; // The URL of data file that stores all User
    private int numberOfUser; // The number of users that stored at data file
    private ArrayList<User> users; // All instances of users

    public UserManagement(String US_FILE) throws Exception {
        if (US_FILE.equals("")) {
            throw new Exception("The URL of User data file can't be empty!");
        } else {
            // Inits the URL of data file that store user bank
            this.US_FILE = US_FILE;
            // Create empty user bank
            this.users = new ArrayList<User>();
            // So, the number of user is equal 0
            this.numberOfUser = 0;
        }
    }

    /**
     * Load data of users from data file and store it into ArrayList
     *
     * @throws IOException
     * @throws Exception
     */
    public void loadUsers() throws IOException, Exception {
        File usFile = new File(US_FILE);

        // Checks the status file create or not
        if (!usFile.exists()) {
            usFile.createNewFile(); // If not, create a new file
            System.out.println("Creating the new file... Done!");
            this.numberOfUser = 0; // New data file with the number of user is equal 0
        } else {
            // If file is exists, loading this data file
            System.out.print("Data of users is loading...");
            // Load text file into buffer
            try (BufferedReader br = new BufferedReader(new FileReader(US_FILE))) {
                String line, userId;
                int score;
                // Read the number of users
                line = br.readLine();
                if (line != null) {
                    this.numberOfUser = Integer.parseInt(line);
                }

                for (int i = 0; i < this.numberOfUser; i++) {
                    // Read the user infomation
                    userId = br.readLine();
                    score = Integer.parseInt(br.readLine());

                    // Create new instance of User and adds to user bank
                    this.users.add(new User(userId, score));
                }
            }
            System.out.println("Done!");
        }
    }

    /**
     * add new user
     *
     * @param user
     */
    public void addUser(User user) throws IOException {
        this.users.add(user);
        Collections.sort(users);
        saveUsers();
    }

    public void changeScore(String userId, int score) throws IOException, Exception {
        boolean isExisted = false;
        int size = users.size();
        for (int i = 0; i < size; i++) {
            if (users.get(i).getUserId().equalsIgnoreCase(userId)) {
                isExisted = true;
                users.get(i).setScore(score);
                break;
            }
        }
        if (!isExisted) {
            System.out.println("userId: " + userId + " not existed.\n");
        } else {
            saveUsers();
        }
        Collections.sort(users);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Save ArrayList<User> into data file
     *
     * @throws IOException
     */
    public void saveUsers() throws IOException {
        //Overwrite data file
        FileWriter fw = new FileWriter(new File(US_FILE), false);
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            //Writes number of User
            bw.write(String.valueOf(this.users.size()));

            for (int i = 0; i < this.users.size(); i++) {
                //Inits User's information
                String userId = this.users.get(i).getUserId();
                String score = String.valueOf(this.users.get(i).getScore());

                //Writes User's information into data file
                bw.newLine();
                bw.write(userId);
                bw.newLine();
                bw.write(score);
            }
        } finally {
            //Saves data file (from RAM into Storage)
            bw.close();
            System.out.println("Save successfully!");
        }
    }

    /**
     * Find user by userId and return the index of this userId
     *
     * @param userInput
     * @return
     */
    public int searchUser(String userInput) {
        for (int i = 0; i < this.users.size(); i++) {
            User acc = this.users.get(i);
            if (acc.getUserId().equals(userInput)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets number of users
     *
     * @return
     */
    public int getSize() {
        return this.numberOfUser;
    }

    /**
     * check the user is valid or not
     *
     * @param user
     * @return
     */
    public User checkUser(User user) {
        for (User u : users) {
            if (u.getUserId().equals(user.getUserId())) {
                return u;
            }
        }
        return null;
    }

    /**
     * Show all users
     *
     * @return
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.numberOfUser; i++) {
            str += String.format("%-30s%-3d\n", users.get(i).getUserId(), users.get(i).getScore());
        }
        return str;
    }
}
