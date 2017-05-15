package ch.philopateer.mibody.listener;

import ch.philopateer.mibody.object.ExerciseItem;

import java.util.List;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public interface OnCustomerListChangedListener {
    void onNoteListChanged(List<ExerciseItem> exerciseItemList);
}
