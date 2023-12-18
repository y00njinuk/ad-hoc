package EncodingTutorial.protobuf;

import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import org.apache.commons.codec.binary.Hex;
import EncodingTutorial.protobuf.schema.Person;

import java.io.IOException;

public class ProtobufEx {
    public static Message fromJson(String json) throws IOException {
        Message.Builder structBuilder = Struct.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(json, structBuilder);
        return structBuilder.build();
    }

    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        return JsonFormat.printer().print(messageOrBuilder);
    }

    public static void main(String[] args) throws IOException {
        Person person = Person.newBuilder()
                .setUserName("Martin")
                .setFavoriteNumber(1337)
                .addInterests("daydreaming")
                .addInterests("hacking")
                .build();

        byte[] res = person.toByteArray();

        System.out.println(res.length);
        System.out.println(Hex.encodeHex(res));

        Person.Builder builder = person.toBuilder();
        String jsonString = toJson(builder);
        System.out.println(jsonString);
    }
}
