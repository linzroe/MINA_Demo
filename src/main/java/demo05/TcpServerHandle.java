package demo05;


import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TcpServerHandle extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {

    	// 读取客户端传过来的Student对象
    	StudentMsg.Student student = (StudentMsg.Student) message;
        System.out.println("ID:" + student.getId());
        System.out.println("Name:" + student.getName());
        System.out.println("Email:" + student.getEmail());
        System.out.println("Friends:");
        List<String> friends = student.getFriendsList();
        for(String friend : friends) {
        	System.out.println(friend);
        }

        
        // 新建一个Student对象传到客户端
        StudentMsg.Student.Builder builder = StudentMsg.Student.newBuilder();
        builder.setId(9);
        builder.setName("服务器");
        builder.setEmail("123@abc.com");
        builder.addFriends("X");
        builder.addFriends("Y");
        StudentMsg.Student student2 = builder.build();
        session.write(student2);
    }
}