package com.gmail.willramanand.RamLocks.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

public class EasyComponent {

    private TextComponent component;

    public EasyComponent(String message) {
        component = Component.text(Txt.parse(message));
    }

    public TextComponent get() {
        return component;
    }

    public EasyComponent clickEvent(String command) {
        component = component.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    public EasyComponent hoverEvent(String hoverText) {
        component = component.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(Txt.parse(hoverText))));
        return this;
    }
}
