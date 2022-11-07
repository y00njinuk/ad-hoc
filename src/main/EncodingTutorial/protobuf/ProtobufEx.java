package EncodingTutorial.protobuf;

import org.apache.commons.codec.binary.Hex;
import EncodingTutorial.protobuf.schema.Person;

public class ProtobufEx {
    public static void main(String[] args) {
        Person person = Person.newBuilder()
                .setUserName("Martin")
                .setFavoriteNumber(1337)
                .addInterests("daydreaming")
                .addInterests("hacking")
                .build();

        byte[] res = person.toByteArray();

        System.out.println(res.length);
        System.out.print(Hex.encodeHex(res));
    }
}
