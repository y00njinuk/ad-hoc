import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StringHashCodeCompareTest {
    public class Player {
        private String firstName;
        private String lastName;
        private String position;

        public Player(String firstName, String lastName, String position) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.position = position;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + firstName != null ? firstName.hashCode() : 0;
            result = 31 * result + lastName != null ? lastName.hashCode() : 0;
            result = 31 * result + position != null ? position.hashCode() : 0;
            return result;
        }
    }

    @Test
    public void testHashCode() {
        Player player = new Player("Eduardo", "Rodriguez", "Pitcher");
        Player indenticalPlayer = new Player("Eduardo", "Rodriguez", "Pitcher");

        int hashCode1 = player.hashCode();
        int hashCode2 = player.hashCode();
        int hashCode3 = indenticalPlayer.hashCode();

        assertEquals(hashCode1, hashCode2);
        assertEquals(hashCode1, hashCode3);
    }

    @Test
    public void testPlayer() {
        Player player = new Player("Bobby", "Dalbec", "First Base");
        int hashcode1 = player.hashCode();
        String[] playerInfo = {"Bobby", "Dalbec", "First Base"};
        int hashcode2 = Arrays.hashCode(playerInfo);

        assertEquals(hashcode1, hashcode2);
    }
}