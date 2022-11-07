package EncodingTutorial.msgpack;

import org.apache.commons.codec.binary.Hex;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;

public class MsgPackEx {
    public static void main(String[] args) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();;

        // pack map (key -> value) elements
        packer.packMapHeader(3); // the number of (key, value) pairs
        packer.packString("userName");
        packer.packString("Martin");
        packer.packString("favoriteNumber");
        packer.packInt(1337);
        packer.packString("interests");
        packer.packArrayHeader(2);
        packer.packString("daydreaming");
        packer.packString("hacking");

        byte[] res = packer.toByteArray();

        System.out.println(res.length);
        System.out.print(Hex.encodeHex(res));
    }
}
