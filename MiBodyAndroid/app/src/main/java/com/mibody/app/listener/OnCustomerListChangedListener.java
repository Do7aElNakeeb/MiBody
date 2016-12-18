package com.mibody.app.listener;

import com.mibody.app.app.ExerciseItem;

import java.util.List;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public interface OnCustomerListChangedListener {
    void onNoteListChanged(List<ExerciseItem> exerciseItemList);
}
