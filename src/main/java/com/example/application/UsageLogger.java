package com.example.application;

import com.example.application.data.service.UsageLogService;
import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class UsageLogger implements VaadinServiceInitListener {

    UsageLogService service;
    AuthenticatedUser authenticatedUser;

    public UsageLogger(UsageLogService service, AuthenticatedUser authenticatedUser) {
        this.service = service;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            uiEvent.getUI().addAfterNavigationListener(nEvent -> {
                HasElement view = nEvent.getActiveChain().get(0);
                service.logEntry(
                        (com.vaadin.flow.component.Component) view,
                        VaadinRequest.getCurrent().getRemoteAddr(),
                        authenticatedUser.get().orElse(null)
                );
            });
        });
    }
}
