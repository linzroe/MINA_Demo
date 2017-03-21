package demo05;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 编码器
 * @author DELL
 * 
 */
public class MinaProtobufEncoder extends ProtocolEncoderAdapter {

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

    	StudentMsg.Student student = (StudentMsg.Student) message;
        byte[] bytes = student.toByteArray(); // Student对象转为protobuf字节码
        int length = bytes.length;

        IoBuffer buffer = IoBuffer.allocate(length + 4);
        buffer.putInt(length); // write header
        buffer.put(bytes); // write body
        buffer.flip();
        out.write(buffer);
    }
}
