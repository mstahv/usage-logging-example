package com.example.application.views;

import com.example.application.MyGrid;
import com.example.application.data.entity.UsageLogEntry;
import com.example.application.data.entity.User;
import com.example.application.data.service.GeoIpService;
import com.example.application.data.service.UsageLogService;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;

@Route(layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Usage log")
public class UsageLogView extends VerticalLayout {

    private final UsageLogService service;
    Grid<UsageLogEntry> grid = new MyGrid<>(UsageLogEntry.class);
    DateTimePicker start = new DateTimePicker("Start");
    DateTimePicker end = new DateTimePicker("End");
    MultiSelectComboBox<User> users = new MultiSelectComboBox<User>("Users");

    public UsageLogView(UsageLogService service, UserService userService, GeoIpService geoIpService) {
        this.service = service;

        users.setItems(userService.list());
        users.setItemLabelGenerator(u -> u.getName());

        Button filter = new Button("Filter", event -> filter());
        HorizontalLayout filterBar = new HorizontalLayout(start, end, users, filter);
        filterBar.setAlignItems(Alignment.END);
        add(filterBar);

        grid.setColumns("pageVisit", "view", "ip");
        grid.addColumn(e -> geoIpService.getCity(e.getIp())).setHeader("City");
        grid.addColumn(e -> e.getUser() == null ? "-" : e.getUser().getName()).setHeader("Username");

        addAndExpand(grid);

        end.setValue(LocalDateTime.now());
        start.setValue(end.getValue().minusHours(1));
        // Only allow one hour of results
        start.addValueChangeListener(event -> {
            if(event.isFromClient()) {
                end.setValue(event.getValue().plusHours(1));
            }
        });
        end.addValueChangeListener(event -> {
            if(event.isFromClient()) {
                start.setValue(event.getValue().minusHours(1));
            }
        });

        filter.addClickShortcut(Key.ENTER);
        filter();
    }

    private void filter() {
        // Note, if you expect a "megaton of" hits
        // might make sense (to save resources) to add also limit clause
        // and show notification that only top results shown
        // if the size results is bigger than the limit
        grid.setItems(service.listEntries(start.getValue(),end.getValue(), users.getValue()));
        grid.scrollToEnd();
    }

}
