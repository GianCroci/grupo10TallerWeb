package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VistaNavBar extends VistaWeb {
    public VistaNavBar(Page page) {
        super(page);
    }

    public void darClickEnHerreria() {
        page.waitForSelector("button[type='submit']", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        this.darClickEnElElemento("#herreria");
    }
}
