package com.example.demo;

import com.example.demo.model.Todo;
import com.example.demo.services.TodoServiceImp;
import com.itextpdf.text.DocumentException;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

@Route
public class MainView extends VerticalLayout {

    @Autowired
    TodoServiceImp todoServiceImp;
    final TextField filter;
    Label label;
    String string;

    public MainView() throws UnknownHostException {
        this.filter = new TextField();
        this.filter.setWidth(600, Unit.PIXELS);
        Button addNewBtn = new Button("Search", VaadinIcon.SEARCH_PLUS.create());
        Button printBtn = new Button("Print", VaadinIcon.PRINT.create());
        this.label = new Label("Status of Device");

        Button button = new Button("Print", VaadinIcon.PRINT.create()); //need a button
        Anchor url = new Anchor("/find/download/"); // and anchor with a href link
        url.setTarget("download"); //this is to open in a new tab
        url.add(button); //add your button here

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, url);
        add(actions, label);

        filter.setPlaceholder("Please input IP Address to look up");

        addNewBtn.addClickListener(e -> {
            findHostNameByIP(filter.getValue());
        });
    }

    public void findHostNameByIP(String IPAddress){
        Todo todo = todoServiceImp.getTodoByIPAddress(IPAddress);
        this.add(new Label(todo.getIpAddress()));
        this.add(new Label(todo.getHostName()));
        string += (todo.getIpAddress() + todo.getHostName());
    }
}
