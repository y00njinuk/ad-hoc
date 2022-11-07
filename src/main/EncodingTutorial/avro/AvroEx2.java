package EncodingTutorial.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import EncodingTutorial.avro.schema.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class AvroEx2 {
    public static void main(String[] args) throws IOException {
        Schema writer = new Schema.Parser().parse(new File("src/java/com/avro/schema/person.avsc"));
        Schema reader = new Schema.Parser().parse(new File("src/java/com/avro/schema/newPerson.avsc"));

        Person person = Person.newBuilder()
                .setUserName("Martin")
                .setFavoriteNumber(1337L)
                .setInterests(Arrays.asList("daydreaming", "hacking"))
                .build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);

        GenericDatumWriter<Person> datumWriter = new GenericDatumWriter<>(writer);
        datumWriter.write(person, encoder);

        byte[] res = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(res);
        Decoder decoder = DecoderFactory.get().directBinaryDecoder(inputStream, null);

        GenericDatumReader<GenericData.Record> datumReader = new GenericDatumReader<>(writer, reader);
        GenericData.Record record = datumReader.read(null, decoder);

        System.out.println(GenericData.get().toString(person));
        System.out.println(GenericData.get().toString(record));
    }
}