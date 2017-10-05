package leo.demo.watsonconversation;

import leo.demo.watsonconversation.watson.ChatBotConversationDTO;
import leo.demo.watsonconversation.watson.ChatBotService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by odzhara-ongom on 29.09.2017.
 */
@RestController
public class RestApi {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private ChatBotService chatBotService;

    @RequestMapping(value = {"/api/test", "/info"}, method = RequestMethod.GET)
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("application", "iOS App Microservice");
        result.put("version", WatsonconversationApplication.DEVELOPMENT_VERSION);
        result.put("applicationStart", new Date(WatsonconversationApplication.APPLICATION_START_TIME).toString());
        return result;
    }

    @RequestMapping(value = {"/api/talk/continue"}, method = RequestMethod.POST)
    public ResponseEntity<ChatBotConversationDTO> talk(@RequestBody ChatBotConversationDTO request,
                                                       @RequestParam Map<String, String> params) {
        String serviceName = extractServiceName(params);
        ChatBotConversationDTO chatResponse = chatBotService.getAnswer(serviceName, request);
        String username = (String) chatResponse.getContext().get("username");
        return ResponseEntity.ok(chatResponse);
    }

    private String extractServiceName(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params)) {
            return chatBotService.serviceNames().get(0);
        }
        String serviceName = params.get("service");
        if (StringUtils.isEmpty(serviceName)) {
            return chatBotService.serviceNames().get(0);
        } else {
            return serviceName;
        }
    }

}
