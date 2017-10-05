/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.watsonconversation.watson;

import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Transfer Object for communication between Integration Layer and one single IBM Watson Conversation Service
 *
 * @author odzhara-ongom
 */
public class ChatBotConversationDTO {

    /**
     * placeholder for a users question
     */
    private String question;
    /**
     * placeholder for the answer of user question. The long answers can be slitted in paragraphs
     */
    private List<String> answer;
    /**
     * placeholder for Context Object from IBM Watson Conversation Service.
     * contains information about state of conversation
     * might also have information about nickname, car model, telephone, etc, if such is being set by Watson
     */
    private Map<String, Object> context;
    /**
     * a list of intents from IBM Watson Conversation Service response.
     * Can be used to get/check the confidence level of response
     */
    private List<Intent> intents;
    /**
     * Mirrors corresponding IBM Watson Conversation Response field.
     * currently not being used
     */
    private Map<String, Object> output;
    /**
     * id of conversation. unique key that identifies certain conversation
     */
    private String id;
    /**
     * id of workspace that contains AI, that should "talk" with user:
     * Every IBM Watson Service might have several "AIs", that can communicate with user
     * so in his request the user shoud specify with which "AI" he wants to "speak"
     */
    private String workspaceId;

    private String nickname;

    private String model;

    /**
     * Converts standard Watson MessageResponse Object into Entity Class used within Integration Layer
     *
     * @param response
     * @param workspaceId
     * @return
     */
    public static ChatBotConversationDTO fromMessageResponse(MessageResponse response, String workspaceId) {
        ChatBotConversationDTO dto = new ChatBotConversationDTO();
        dto.setIntents(response.getIntents());
        dto.setOutput(response.getOutput());
        dto.setContext(response.getContext());
        dto.setAnswer(response.getText());
        dto.setQuestion(response.getInputText());
        dto.setId((String) response.getContext().get("conversation_id"));
        dto.setNickname((String) response.getContext().get("nickname"));
        dto.setModel((String) response.getContext().get("vehicleModel"));
        dto.setWorkspaceId(workspaceId);
        return dto;
    }

    public ChatBotConversationDTO() {
        super();
    }

    public ChatBotConversationDTO(String workspaceId, String question) {
        this.workspaceId = workspaceId;
        this.question = question;
    }


    public ChatBotConversationDTO workspaceId(String workspaceId) {
        setWorkspaceId(workspaceId);
        return this;
    }

    public ChatBotConversationDTO question(String q) {
        if (q == null) {
            setQuestion("");
        } else {
            setQuestion(q);
        }
        return this;
    }

    private static String combineStrings(List<String> strings) {
        if (CollectionUtils.isEmpty(strings)) {
            return null;
        }
        if (strings.size() > 1)
            throw new RuntimeException("Watson Response contains more as one answer. Such responses are not supported jet.");
        return strings.get(0);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getNickname() {
        return nickname;
    }

    public ChatBotConversationDTO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ChatBotConversationDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public ChatBotConversationDTO context(Map<String, Object> context) {
        if (this.context == null)
            this.context = context;
        else
            this.context.putAll(context);
        return this;
    }

    public List<Intent> getIntents() {
        return intents;
    }

    public void setIntents(List<Intent> intents) {
        this.intents = intents;
    }

    public Map<String, Object> getOutput() {
        return output;
    }

    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }
}
