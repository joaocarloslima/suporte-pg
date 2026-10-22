package br.com.fiap.suporte.views;

import br.com.fiap.suporte.service.ChatService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.Instant;

@Route("")
public class HomeView extends VerticalLayout {

    private final ChatService chatService;
    private MessageInput messageInput;
    private MessageList messageList;

    public HomeView(ChatService chatService) {

        add(new H1("Suporte FIAP"));
        add(new Paragraph("Tire suas dúvidas"));

        messageList = new MessageList();
        messageList.setWidthFull();
        messageList.setHeightFull();
        add(messageList);

        messageInput = new MessageInput();
        messageInput.setWidthFull();
        messageInput.addSubmitListener(e -> handleSend(e.getValue(), "Você") );
        add(messageInput);

        setHeightFull();
        this.chatService = chatService;
    }

    private void handleSend(String message, String author) {
        messageList.addItem(new MessageListItem(
            message,
            Instant.now(),
            author
        ));

        var response = chatService.sendMessageToAI(message);
        messageList.addItem(new MessageListItem(
            response,
            Instant.now(),
            "Suporte FIAP"
        ));
    }
}
