package com.vanderis.talismans.gui.edit;

import com.vanderis.talismans.items.ItemData;
import lombok.*;

@Getter
@Setter
public class EditData<N extends Number> {

    private final ItemData itemData;
    private final String editType;
    private String editValue_String;
    private final N editValue_Number; // Use for value need for next stage of edit, see EditLoreManager for example

    public EditData(ItemData itemData, String editType) {
        this.itemData = itemData;
        this.editType = editType;
        this.editValue_String = null;
        this.editValue_Number = null;
    }

    public EditData(ItemData itemData, String editType, String editValue_String) {
        this.itemData = itemData;
        this.editType = editType;
        this.editValue_String = editValue_String;
        this.editValue_Number = null;
    }

    public EditData(ItemData itemData, String editType, N editValue_Number) {
        this.itemData = itemData;
        this.editType = editType;
        this.editValue_String = null;
        this.editValue_Number = editValue_Number;
    }

    public EditData(ItemData itemData, String editType, String editValue_String, N editValue_Number) {
        this.itemData = itemData;
        this.editType = editType;
        this.editValue_String = editValue_String;
        this.editValue_Number = editValue_Number;
    }

}
