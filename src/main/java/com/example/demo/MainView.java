package com.example.demo;

import com.example.demo.model.Todo;
import com.example.demo.services.TodoServiceImp;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

@Route
public class MainView extends VerticalLayout {

    @Autowired
    TodoServiceImp todoServiceImp;
    final TextField filter;
    Label label;
    String string;
    HorizontalLayout horizontalLayout;
    VerticalLayout verticalLayout1;
    VerticalLayout verticalLayout2;

    public MainView() throws UnknownHostException {
        this.horizontalLayout = new HorizontalLayout();
        this.verticalLayout1 = new VerticalLayout();
        this.verticalLayout2 = new VerticalLayout();
        this.filter = new TextField();
        this.filter.setWidth(600, Unit.PIXELS);
        Button addNewBtn = new Button("Search", VaadinIcon.SEARCH_PLUS.create());
        Button printBtn = new Button("Show all Devices in Subnet.");
        this.label = new Label("Status of Device");
        this.horizontalLayout.add(verticalLayout1, verticalLayout2);

//        Button button = new Button("Print", VaadinIcon.PRINT.create()); //need a button
//        Anchor url = new Anchor("/find/download/"); // and anchor with a href link
//        url.setTarget("download"); //this is to open in a new tab
//        url.add(button); //add your button here

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, printBtn);
        add(actions, label, horizontalLayout);

        filter.setPlaceholder("Please input IP Address to look up");

        addNewBtn.addClickListener(e -> {
            findHostNameByIP(filter.getValue());
        });
        printBtn.addClickListener(e -> {
            printAllSubnetIP();
        });
    }

    public void findHostNameByIP(String IPAddress){
        Todo todo = todoServiceImp.getTodoByIPAddress(IPAddress);
        verticalLayout1.add(new Label(todo.getIpAddress()));
        verticalLayout1.add(new Label(todo.getHostName()));
        string += (todo.getIpAddress() + todo.getHostName());
    }

    public void printAllSubnetIP() {
        List<Todo> todos = todoServiceImp.getTodos();
        for (Todo todo: todos) {
            verticalLayout2.add(new Label(todo.getIpAddress() + " : " + todo.getHostName()));
        }
    }
}
