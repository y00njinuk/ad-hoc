import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Getter
@Setter
@ToString
class Book {
    String title;
    Optional<String> subTitle = Optional.empty();
}

public class JsonOptionalSerde {
    @Test
    @DisplayName("Optional 필드에 값을 할당한 Book 객체를 JSON 문자열로 직렬화하는 것은 성공한다..")
    public void givenFieldWithValue_whenSerializing_thenReturnJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Book book = new Book();
        book.setTitle("OLiver Twist");
        book.setSubTitle(Optional.of("The Parish Boy's Progress"));

        String result = mapper.writeValueAsString(book);

        assertEquals(result, "{\"title\":\"OLiver Twist\",\"subTitle\":{\"empty\":false,\"present\":true}}");
    }

    @Test
    @DisplayName("설정 값이 없는 Object Mapper는 JSON 문자열 키/값을 Optional 필드로 역직렬화 시에 실패한다.")
    public void givenJsonString_whenDeserializing_thenThrowException() {
        ObjectMapper mapper = new ObjectMapper();

        String bookJson = "{\"title\":\"Oliver Twist\", \"subTitle\":\"foo\"}";

        assertThrows(JsonProcessingException.class, () -> mapper.readValue(bookJson, Book.class));
        /**
         * com.fasterxml.jackson.databind.exc.MismatchedInputException
         *      : Cannot construct instance of `java.util.Optional` (although at least one Creator exists)
         *      : no String-argument constructor/factory method to deserialize from String value ('foo')
         *          at [Source: (String)"{"title":"Oliver Twist", "subTitle":"foo"}"; line: 1, column: 37]
         *          (through reference chain: Book["subTitle"])
         */
    }

    @Test
    @DisplayName("Jdk8Module 객체를 설정값으로 한 Object Mapper는 JSON 문자열 키/값을 Optional 필드로 역직렬화 시 성공한다.")
    public void givenJsonString_whenDeserializing_thenReturnString() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

        String bookJson = "{\"title\":\"Oliver Twist\", \"subTitle\":\"foo\"}";

        assertDoesNotThrow(() -> {
            Book result = mapper.readValue(bookJson, Book.class);
            assertTrue(result.getSubTitle().isPresent());
            assertEquals(result.getSubTitle(), Optional.of("foo"));
        });
    }

    @Test
    @DisplayName("JSON 문자열 키/값이 존재하지 않으면 Optional.empty를 반환한다.")
    public void givenJsonString_whenDeserializing_thenReturnOptionEmpty() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

        String bookJson = "{\"title\":\"Oliver Twist\"}";

        assertDoesNotThrow(() -> {
            Book result = mapper.readValue(bookJson, Book.class);
            assertTrue(result.getSubTitle().isEmpty());
            assertEquals(result.getSubTitle(), Optional.empty());
        });

    }
}
