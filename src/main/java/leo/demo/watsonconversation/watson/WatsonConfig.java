package leo.demo.watsonconversation.watson;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by odzhara-ongom on 13.09.2017.
 * POJO that configures a set of IBM Watson Conversation Service classes to allow the communication
 * with a bunch of IBMs Artificial Intelligences
 */
@Configuration
@ConfigurationProperties(prefix = "watsonconfiguration")
public class WatsonConfig {

    private Map<String, ConversationConfig> services;

    public Map<String, ConversationConfig> getServices() {
        return services;
    }

    public void setServices(Map<String, ConversationConfig> services) {
        this.services = services;
    }

    public String toString(){
        String result="";
        if (services==null) return null;
        for(String name:services.keySet()){
            result+=services.get(name).toString();
        }
        return result;
    }

    public static class ConversationConfig {
        private String username;
        private String password;
        private String endpoint;
        /*
         normaly one should define service and workspace to get answer from IBM Watson.
        The default implementation of communication service in Integration Layer (ConversationServiceImpl)
        uses this field as failback, if workspaceId is not specified in request
         */
        private String defaultWorkspaceId;

        @Override
        public String toString() {
            return "ConversationConfig{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", endpoint='" + endpoint + '\'' +
                    ", defaultWorkspaceId='" + defaultWorkspaceId + '\'' +
                    '}';
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getDefaultWorkspaceId() {
            return defaultWorkspaceId;
        }

        public void setDefaultWorkspaceId(String defaultWorkspaceId) {
            this.defaultWorkspaceId = defaultWorkspaceId;
        }
    }
}
