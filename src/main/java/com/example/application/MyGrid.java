package com.example.application;

import com.vaadin.flow.component.UI;

public class MyGrid<T> extends com.vaadin.flow.component.grid.Grid<T> {

    public MyGrid(Class<T> beanType) {
        super(beanType);
    }

    /**
     * Contains a workaround for https://github.com/vaadin/flow-components/issues/4497
     */
    @Override
    public void scrollToEnd() {
        getUI().orElseGet(UI::getCurrent)
                .beforeClientResponse(this, ctx -> getElement()
                        .executeJs("const el = this; setTimeout(()=>el.scrollToIndex(el._effectiveSize),0);"));
    }
}
