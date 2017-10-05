/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.watsonconversation.watson;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author odzhara-ongom
 * Service that allows communication with several IBM Watson Conversation Services
 * It uses an instance of WatsonConfig (autowired from configuration files)
 * to configure a bunch of services for communication
 * It routes all user request to corresponding service, that can provide "dialog" with IBMs AI
 */
@Service
public class ChatBotService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ChatBotService.class);
    public static final String TEST_QUESTION ="Can my e class park autonomously?";

    @Autowired
    private WatsonConfig config;

    private Map<String, ConversationServiceIntf> services;

    /**
     * we loading the autowired configuration of IBMs Services and creating a map of interfaces/services
     * that allows the routing of user requests to needed service     *
     */
    @PostConstruct
    public void onPostConstruct() {
        services = new HashMap<>();
        if (config == null || CollectionUtils.isEmpty(config.getServices())) {
            return;
        }
        Map<String, WatsonConfig.ConversationConfig> servicesConfig = config.getServices();
        for (String name : servicesConfig.keySet()) {
            WatsonConfig.ConversationConfig configuration = servicesConfig.get(name);
            if (configuration != null) {
                services.put(name, createService(configuration));
            }
        }
    }

    private ConversationServiceIntf createService(WatsonConfig.ConversationConfig configuration) {
        ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
        service.setEndPoint(configuration.getEndpoint());
        service.setUsernameAndPassword(configuration.getUsername(), configuration.getPassword());
        return new ConversationServiceImpl(new WatsonConversationImpl(service), configuration.getDefaultWorkspaceId());
    }

    public List<String> serviceNames(){
        if(CollectionUtils.isEmpty(services)) return new ArrayList<>();
        return services.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Routes user request to the IBMs WatsonConversation Service
     * This function can be used for information domain routing
     * @param serviceName
     * @param question
     * @return
     */
    public ChatBotConversationDTO getAnswer(String serviceName, ChatBotConversationDTO question) {
        ConversationServiceIntf service = services.get(serviceName);
        if (service == null) {
            throw new IllegalArgumentException("The conversation service '"+serviceName+"' was not found." +
                    " Please check configuration files for possible errors");
        }
        return service.talk(question);
    }


}
