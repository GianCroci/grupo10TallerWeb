package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VistaHome extends VistaWeb {
    public VistaHome(Page page) {
        super(page);
        page.navigate("http://localhost:8080/spring/home");
    }

    public void darClickEnHerreria() {
        page.waitForSelector("a#herreria", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        this.darClickEnElElemento("#herreria");
    }
}
