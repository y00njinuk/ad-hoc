package EncodingTutorial.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.commons.codec.binary.Hex;
import EncodingTutorial.avro.schema.Person;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class AvroEx {
    public static void main(String[]args)throws IOException {
        Schema writer = new Schema.Parser().parse(new File("src/java/com/avro/schema/person.avsc"));

        Person person = Person.newBuilder()
                .setUserName("Martin")
                .setFavoriteNumber(1337L)
                .setInterests(Arrays.asList("daydreaming","hacking"))
                .build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().directBinaryEncoder(outputStream,null);

        GenericDatumWriter<Person>datumWriter =new GenericDatumWriter<>(writer);
        datumWriter.write(person,encoder);

        byte[] res = outputStream.toByteArray();

        System.out.println(res.length);
        System.out.print(Hex.encodeHex(res));
    }
}