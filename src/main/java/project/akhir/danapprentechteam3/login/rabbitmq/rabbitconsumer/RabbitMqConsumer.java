package project.akhir.danapprentechteam3.login.rabbitmq.rabbitconsumer;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.login.payload.request.LoginRequest;
import project.akhir.danapprentechteam3.login.payload.request.SignupRequest;

@Service
public class RabbitMqConsumer
{
        static final String Signupqueue = "signupQueue";
        @RabbitListener(queues = Signupqueue)
        public void recievedMessage(SignupRequest signupRequest)
        {
            System.out.println(signupRequest.getConfirmPassword());
        }

        static final String Loginqueue = "loginKey";
//        @RabbitListener(queues = Loginqueue)
        public LoginRequest loginRequest(LoginRequest loginRequest)
        {
            System.out.println("");
            return loginRequest;
        }

        static final String Logoutqueue = "logoutKey";
//        @RabbitListener(queues = Logoutqueue)
        public LoginRequest logoutRequest(LoginRequest loginRequest)
        {
            System.out.println("");
            return loginRequest;
        }

        static final String updatequeue = "updateKey";
//        @RabbitListener(queues = updatequeue)
        public LoginRequest updateRequest(LoginRequest loginRequest)
        {
            System.out.println("");
            return loginRequest;
        }
}
