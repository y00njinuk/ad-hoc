package EncodingTutorial.thrift;

import org.apache.commons.codec.binary.Hex;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import EncodingTutorial.thrift.schema.Person;

import java.util.Arrays;

public class ThriftEx {
    public static void main(String[] args) throws TException {
        Person person = new Person();

        person.setUserName("Martin");
        person.setFavoriteNumber(1337);
        person.setInterests(Arrays.asList("daydreaming", "hacking"));

        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());

        byte[] res = serializer.serialize(person);

        System.out.println(res.length);
        System.out.print(Hex.encodeHex(res));
    }
}
